package knu.knu2025scdpteam06backend.controller;

import knu.knu2025scdpteam06backend.dto.sales.SalesRequestDto;
import knu.knu2025scdpteam06backend.dto.sales.SalesResponseDto;
import knu.knu2025scdpteam06backend.dto.sales.TotalSalesResponseDto;
import knu.knu2025scdpteam06backend.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/{id}")
    public List<SalesResponseDto> getSalesData(
            @PathVariable String id,
            @RequestParam String startDate,
            @RequestParam String endDate,
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

    @GetMapping("/total/{id}")
    public TotalSalesResponseDto getTotalSales(
            @PathVariable String id,
            @RequestParam String startDate,
            @RequestParam String endDate,
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