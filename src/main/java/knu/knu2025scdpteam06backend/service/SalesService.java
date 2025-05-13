package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.dailydata.DailyData;
import knu.knu2025scdpteam06backend.domain.dailydata.DailyDataRepository;
import knu.knu2025scdpteam06backend.domain.menu.Menu;
import knu.knu2025scdpteam06backend.domain.menu.MenuRepository;
import knu.knu2025scdpteam06backend.domain.sales.Sales;
import knu.knu2025scdpteam06backend.domain.sales.SalesRepository;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.sales.RealTimeSalesDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesRequestDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto.SalesDataDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto.SalesDataDto.MenuDto;
import knu.knu2025scdpteam06backend.dto.sales.TotalSalesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final DailyDataRepository dailyDataRepository;
    private final WebClientService webClientService;

    public List<SalesResponseDto> getSalesByStore(String mbId, SalesRequestDto requestDto) {

        List<DailyData> dailyDataList = getDailyDataListByStoreId(mbId, requestDto);
        List<SalesResponseDto> result = new ArrayList<>();

        for (DailyData dailyData : dailyDataList) {
            if (dailyData.getTotalRevenue() == 0){
                result.add(SalesResponseDto.builder()
                        .date(dailyData.getDateTime())
                        .totalRevenue(0)
                        .totalCount(0)
                        .salesData(new ArrayList<>())
                        .build());
            }
            else{
                List<Sales>salesList = salesRepository.findByDailyDataId(dailyData.getId());
                List<SalesDataDto> dataList = new ArrayList<>();
                for (Sales sale : salesList) {
                    dataList.add(
                            SalesDataDto.builder()
                                    .count(sale.getCount())
                                    .dateTime(sale.getDateTime())
                                    .menu(MenuDto.builder()
                                            .name(sale.getMenu().getName())
                                            .image(sale.getMenu().getImage())
                                            .price(sale.getMenu().getPrice())
                                            .build())
                                    .build()
                    );
                }
                result.add(SalesResponseDto.builder()
                        .date(dailyData.getDateTime())
                        .totalRevenue(dailyData.getTotalRevenue())
                        .totalCount(dailyData.getTotalCount())
                        .salesData(dataList)
                        .build());
            }

        }

        // 실시간 데이터 받아오기
        LocalDate today = LocalDate.now();
        if (requestDto.getEndDate().toLocalDate().equals(today) || requestDto.getEndDate().toLocalDate().isAfter(today)){

            RealTimeSalesDto realTimeSalesDto = webClientService.getRealTimeSalesData(mbId, today.toString());
            if (realTimeSalesDto.getTotalRevenue() == 0){
                result.add(SalesResponseDto.builder()
                        .date(today.atStartOfDay())
                        .totalRevenue(0)
                        .totalCount(0)
                        .salesData(new ArrayList<>())
                        .build());
            }
            else{
                int totalRevenue = 0;
                int totalCount = 0;
                List<SalesDataDto> dataList = new ArrayList<>();
                int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
                int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;

                for (RealTimeSalesDto.SalesDataDto salesDataDto : realTimeSalesDto.getData()) {

                    Optional<Menu> menu = menuRepository.findById(salesDataDto.getMenuId());
                    if (menu.isEmpty()) {
                        Long id = salesDataDto.getMenuId();
                        String name = salesDataDto.getMenuName();
                        Integer revenue = salesDataDto.getRevenue();
                        Integer count = salesDataDto.getCount();
                        Integer price = revenue / count;

                        menuRepository.save(Menu.builder().
                                id(id).
                                name(name).
                                price(price).
                                build());
                        menu = menuRepository.findById(salesDataDto.getMenuId());
                    }

                    if (requestDto.getStartDate().equals(requestDto.getEndDate())){
                        if (endHour >= Integer.parseInt(salesDataDto.getHour()) && startHour <= Integer.parseInt(salesDataDto.getHour()) ){
                            totalRevenue += salesDataDto.getCount() * menu.get().getPrice();
                            totalCount += salesDataDto.getCount();
                            dataList.add(
                                    SalesDataDto.builder()
                                            .count(salesDataDto.getCount())
                                            .dateTime(LocalDate.parse(salesDataDto.getDate()).atStartOfDay().plusHours(Integer.parseInt(salesDataDto.getHour())))
                                            .menu(MenuDto.builder()
                                                    .name(menu.get().getName())
                                                    .image(menu.get().getImage())
                                                    .price(menu.get().getPrice())
                                                    .build())
                                            .build()
                            );
                        }
                    }
                    else {
                        if (endHour >= Integer.parseInt(salesDataDto.getHour())){
                            totalRevenue += salesDataDto.getCount() * menu.get().getPrice();
                            totalCount += salesDataDto.getCount();
                            dataList.add(
                                    SalesDataDto.builder()
                                            .count(salesDataDto.getCount())
                                            .dateTime(LocalDate.parse(salesDataDto.getDate()).atStartOfDay().plusHours(Integer.parseInt(salesDataDto.getHour())))
                                            .menu(MenuDto.builder()
                                                    .name(menu.get().getName())
                                                    .image(menu.get().getImage())
                                                    .price(menu.get().getPrice())
                                                    .build())
                                            .build()
                            );
                        }
                    }
                }

                result.add(SalesResponseDto.builder()
                        .date(today.atStartOfDay())
                        .totalRevenue(totalRevenue)
                        .totalCount(totalCount)
                        .salesData(dataList)
                        .build());
            }
        }
        // 날짜 순 정렬
        result.sort(Comparator.comparing(SalesResponseDto::getDate));
        return result;
    }

    public TotalSalesResponseDto getTotalSalesByStore(String mbId, SalesRequestDto requestDto) {
        List<DailyData> dailyDataList = getDailyDataListByStoreId(mbId, requestDto);

        int totalRevenue = 0;
        int totalCount = 0;
        List<TotalSalesResponseDto.SalesDataDto> salesData = new ArrayList<>();

        for (DailyData dailyData : dailyDataList) {

            totalRevenue += dailyData.getTotalRevenue();
            totalCount += dailyData.getTotalCount();

            List<Sales>salesList = salesRepository.findByDailyDataId(dailyData.getId());
            List<SalesDataDto> dataList = new ArrayList<>();
            for (Sales sale : salesList) {
                salesData.add(
                    TotalSalesResponseDto.SalesDataDto.builder()
                        .count(sale.getCount())
                        .dateTime(sale.getDateTime())
                        .menu(
                            TotalSalesResponseDto.SalesDataDto.MenuDto.builder()
                                    .name(sale.getMenu().getName())
                                    .image(sale.getMenu().getImage())
                                    .price(sale.getMenu().getPrice())
                                    .build()
                        )
                        .build()
                );
            }

        }

        return TotalSalesResponseDto.builder()
                .totalRevenue(totalRevenue)
                .totalCount(totalCount)
                .salesData(salesData)
                .build();
    }

    private List<DailyData> getDailyDataListByStoreId(String mbId, SalesRequestDto requestDto) {
        Store store = storeRepository.findByMbId(mbId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + mbId));

        int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
        int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;

        LocalDateTime startDateTime = requestDto.getStartDate().plusHours(startHour);
        LocalDateTime endDateTime = requestDto.getEndDate().plusHours(endHour);

        List<DailyData> dailyDataList = dailyDataRepository.findByStoreIdAndDateTimeBetween(
                store.getId(), startDateTime, endDateTime
        );

        return dailyDataList;
    }
}