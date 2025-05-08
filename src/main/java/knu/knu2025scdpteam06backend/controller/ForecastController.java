package knu.knu2025scdpteam06backend.controller;

import knu.knu2025scdpteam06backend.dto.forecast.ForecastResponseDto;
import knu.knu2025scdpteam06backend.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping("/forecast/{id}"
    public ForecastResponseDto getForecast(
            @PathVariable String id,
            @RequestParam LocalDateTime dateTime
     {
        return forecastService.getForecastByStore(id, dateTime;
    }

}
