package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping
    public List<SalesResponseDto> getSalesData(
            HttpServletRequest request,
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
        // JwtAuthenticationFilter에서 저장된 store_id
        Long storeId = (Long) request.getAttribute("store_id");
        SalesRequestDto requestDto = SalesRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        return salesService.getSalesByStore(storeId, requestDto);
    }

    @Operation(
            summary = "특정 매장의 기간별 매출 조회(기간 내 전체 판매 개수, 금액의 총합 반환)",
            description = "ID와 날짜 기간을 이용하여 매출 상세 정보를 조회합니다."
    )
    @GetMapping("/total")
    public TotalSalesResponseDto getTotalSales(
            HttpServletRequest request,
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
        Long storeId = (Long) request.getAttribute("store_id");
        SalesRequestDto requestDto = SalesRequestDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        return salesService.getTotalSalesByStore(storeId, requestDto);
    }

}