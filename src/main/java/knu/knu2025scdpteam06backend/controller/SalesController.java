package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import knu.knu2025scdpteam06backend.dto.sales.SalesRequestDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto;
import knu.knu2025scdpteam06backend.dto.sales.TotalSalesResponseDto;
import knu.knu2025scdpteam06backend.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    @Operation(
            summary = "특정 매장의 기간별 매출 조회(일별로 판매 개수, 금액의 총합 반환)",
            description = "ID와 날짜 기간을 이용하여 매출 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public List<SalesResponseDto> getSalesData(

            @Parameter(
                    description = "사업자 ID",
                    example = "1234567890",
                    required = true
            )
            @PathVariable String id,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-01T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime startDate,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-02T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime endDate,
            @RequestParam(required = false) Integer startHour,
            @RequestParam(required = false) Integer endHour
    ) {
        SalesRequestDto requestDto = SalesRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        return salesService.getSalesByStore(id, requestDto);
    }

    @Operation(
            summary = "특정 매장의 기간별 매출 조회(기간 내 전체 판매 개수, 금액의 총합 반환)",
            description = "ID와 날짜 기간을 이용하여 매출 상세 정보를 조회합니다."
    )
    @GetMapping("/total/{id}")
    public TotalSalesResponseDto getTotalSales(
            @Parameter(
                    description = "사업자 ID",
                    example = "1234567890",
                    required = true
            )
            @PathVariable String id,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-01T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime startDate,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-02T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime endDate,
            @RequestParam(required = false) Integer startHour,
            @RequestParam(required = false) Integer endHour
    ){
        SalesRequestDto requestDto = SalesRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        return salesService.getTotalSalesByStore(id, requestDto);
    }

}