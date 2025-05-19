package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import knu.knu2025scdpteam06backend.dto.Weather.WeatherResponseDto;
import knu.knu2025scdpteam06backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(
            summary = "특정 매장의 하루 날씨 조회",
            description = "ID와 날짜를 이용하여 날씨 정보(강수량, 체감 기온, 날씨)를 조회합니다."
    )
    @GetMapping
    public WeatherResponseDto getWeatherData(
            HttpServletRequest request,
            @Parameter(
                    description = "날짜",
                    example = "2025-01-01T00:00:00",
                    required = true
            )
            @RequestParam LocalDateTime dateTime
    ){
        Long storeId = (Long) request.getAttribute("store_id");
        return weatherService.getWeatherByStoreIdAndDateTime(storeId, dateTime);
    }
}