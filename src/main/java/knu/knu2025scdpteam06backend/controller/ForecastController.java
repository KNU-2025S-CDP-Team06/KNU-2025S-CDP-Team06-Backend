package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import knu.knu2025scdpteam06backend.dto.forecast.ForecastCreateRequestDto;
import knu.knu2025scdpteam06backend.dto.forecast.ForecastResponseDto;
import knu.knu2025scdpteam06backend.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/forecast")
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @Operation(
            summary = "특정 매장의 매출 예측값 조회",
            description = "ID와 날짜를 이용하여 매출 예측값을 조회합니다."
    )
    @GetMapping
    public List<ForecastResponseDto> getForecast(
            HttpServletRequest request,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-01T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        Long storeId = (Long) request.getAttribute("store_id");
        return forecastService.getForecastByStoreId(storeId, startDate, endDate);
    }

    @Operation(
            summary = "특정 매장의 내일 매출 예측값 추가",
            description = "Store Id를 이용하여 매출 예측값을 추가합니다."
    )
    @PostMapping
    public ResponseEntity<Void> addForecast(
                            @RequestBody ForecastCreateRequestDto forecastCreateRequestDto) {
        forecastService.addForecast(forecastCreateRequestDto);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
