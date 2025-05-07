package knu.knu2025scdpteam06backend.service;

import jakarta.transaction.Transactional;
import knu.knu2025scdpteam06backend.domain.sales.Sales;
import knu.knu2025scdpteam06backend.domain.sales.SalesRepository;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.sales.SalesRequestDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto.SalesDataDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto.SalesDataDto.MenuDto;
import knu.knu2025scdpteam06backend.dto.sales.TotalSalesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final StoreRepository storeRepository;

    public List<SalesResponseDto> getSalesByStore(String mbId, SalesRequestDto requestDto) {

        List<Sales>salesList = getSalesListByMbId(mbId, requestDto);

        // 날짜별 그룹화
        Map<String, List<Sales>> grouped = salesList.stream()
                .collect(Collectors.groupingBy(s -> s.getDatetime().toLocalDate().toString()));

        // 응답 변환
        List<SalesResponseDto> result = new ArrayList<>();
        for (String date : grouped.keySet()) {
            List<Sales> salesOnDate = grouped.get(date);

            int totalRevenue = 0;
            int totalCount = 0;
            List<SalesDataDto> dataList = new ArrayList<>();

            for (Sales sale : salesOnDate) {
                int count = sale.getCount();
                int price = sale.getMenu().getPrice();

                totalRevenue += price * count;
                totalCount += count;

                dataList.add(
                        SalesDataDto.builder()
                                .count(count)
                                .datetime(sale.getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH")))
                                .menu(MenuDto.builder()
                                        .name(sale.getMenu().getName())
                                        .image(sale.getMenu().getImage())
                                        .price(price)
                                        .build())
                                .build()
                );
            }

            result.add(SalesResponseDto.builder()
                    .date(date)
                    .totalRevenue(totalRevenue)
                    .totalCount(totalCount)
                    .salesData(dataList)
                    .build());
        }

        // 날짜 순 정렬
        result.sort(Comparator.comparing(SalesResponseDto::getDate));
        return result;
    }

    public TotalSalesResponseDto getTotalSalesByStore(String mbId, SalesRequestDto requestDto) {
        List<Sales>salesList = getSalesListByMbId(mbId, requestDto);getSalesListByMbId(mbId, requestDto);

        int totalRevenue = 0;
        int totalCount = 0;
        List<TotalSalesResponseDto.SalesDataDto> salesData = new ArrayList<>();

        for (Sales sale : salesList) {
            int count = sale.getCount();
            int price = sale.getMenu().getPrice();

            totalRevenue += price * count;
            totalCount += count;

            salesData.add(
                    TotalSalesResponseDto.SalesDataDto.builder()
                            .count(count)
                            .datetime(sale.getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH")))
                            .menu(
                                    TotalSalesResponseDto.SalesDataDto.MenuDto.builder()
                                            .name(sale.getMenu().getName())
                                            .image(sale.getMenu().getImage())
                                            .price(price)
                                            .build()
                            )
                            .build()
            );
        }

        return TotalSalesResponseDto.builder()
                .totalRevenue(totalRevenue)
                .totalCount(totalCount)
                .salesData(salesData)
                .build();
    }

    private List<Sales> getSalesListByMbId(String mbId, SalesRequestDto requestDto) {
        Store store = storeRepository.findByMbId(mbId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + mbId));

        int startHour = requestDto.getStartHour() != null ? requestDto.getStartHour() : 0;
        int endHour = requestDto.getEndHour() != null ? requestDto.getEndHour() : 23;

        LocalDateTime startDateTime = LocalDateTime.parse(
                requestDto.getStartDate() + "T" + String.format("%02d:00:00", startHour)
        );
        LocalDateTime endDateTime = LocalDateTime.parse(
                requestDto.getEndDate() + "T" + String.format("%02d:00:00", endHour)
        );

        List<Sales> salesList = salesRepository.findByStoreIdAndDatetimeBetween(
                store.getId(), startDateTime, endDateTime
        );

        return  salesList;
    }
}