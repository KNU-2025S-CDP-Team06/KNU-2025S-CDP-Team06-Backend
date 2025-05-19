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

    public List<SalesResponseDto> getSalesByStore(Long storeId, SalesRequestDto requestDto) {

        List<DailyData> dailyDataList = getDailyDataListByStoreId(storeId, requestDto);
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
                int totalRevenue = 0;
                int totalCount = 0;
                LocalDateTime startDateTime = requestDto.getStartDate();
                LocalDateTime endDateTime = requestDto.getEndDate();

                if (dailyData.getDateTime().isEqual(requestDto.getStartDate()) && dailyData.getDateTime().isEqual(requestDto.getEndDate())){
                    int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
                    int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;
                    startDateTime = startDateTime.plusHours(startHour);
                    endDateTime = endDateTime.plusHours(endHour);
                }
                else if (dailyData.getDateTime().isEqual(requestDto.getStartDate())){
                    int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
                    startDateTime = startDateTime.plusHours(startHour);
                }
                else if (dailyData.getDateTime().isEqual(requestDto.getEndDate())) {
                    int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;
                    endDateTime = endDateTime.plusHours(endHour);
                }

                List<Sales>salesList = salesRepository.findByDailyDataIdAndDateTimeBetween(
                        dailyData.getId(), startDateTime, endDateTime);
                List<SalesDataDto> dataList = new ArrayList<>();
                for (Sales sale : salesList) {
                    totalRevenue += sale.getCount() * sale.getMenu().getPrice();
                    totalCount += sale.getCount();
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
                        .totalRevenue(totalRevenue)
                        .totalCount(totalCount)
                        .salesData(dataList)
                        .build());
            }

        }

        // 실시간 데이터 받아오기
        LocalDate today = LocalDate.now();
        if (requestDto.getEndDate().toLocalDate().equals(today) || requestDto.getEndDate().toLocalDate().isAfter(today)){

            RealTimeSalesDto realTimeSalesDto = webClientService.getRealTimeSalesData(storeId, today.toString());
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

    public TotalSalesResponseDto getTotalSalesByStore(Long storeId, SalesRequestDto requestDto) {
        List<DailyData> dailyDataList = getDailyDataListByStoreId(storeId, requestDto);

        int totalRevenue = 0;
        int totalCount = 0;
        List<TotalSalesResponseDto.SalesDataDto> salesData = new ArrayList<>();

        for (DailyData dailyData : dailyDataList) {

            LocalDateTime startDateTime = requestDto.getStartDate();
            LocalDateTime endDateTime = requestDto.getEndDate();

            if (dailyData.getDateTime().isEqual(requestDto.getStartDate()) && dailyData.getDateTime().isEqual(requestDto.getEndDate())){
                int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
                int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;
                startDateTime = startDateTime.plusHours(startHour);
                endDateTime = endDateTime.plusHours(endHour);
            }
            else if (dailyData.getDateTime().isEqual(requestDto.getStartDate())){
                int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
                startDateTime = startDateTime.plusHours(startHour);
            }
            else if (dailyData.getDateTime().isEqual(requestDto.getEndDate())) {
                int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;
                endDateTime = endDateTime.plusHours(endHour);
            }

            List<Sales>salesList = salesRepository.findByDailyDataIdAndDateTimeBetween(
                    dailyData.getId(), startDateTime, endDateTime);

            for (Sales sale : salesList) {
                totalRevenue += sale.getCount() * sale.getMenu().getPrice();
                totalCount += sale.getCount();
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

    private List<DailyData> getDailyDataListByStoreId(Long storeId, SalesRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + storeId));

        List<DailyData> dailyDataList = dailyDataRepository.findByStoreIdAndDateTimeBetween(store.getId(), requestDto.getStartDate(), requestDto.getEndDate());

        return dailyDataList;
    }
}