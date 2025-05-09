package knu.knu2025scdpteam06backend.controller;

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

    @GetMapping("/{id}")
    public WeatherResponseDto getWeatherData(
            @PathVariable String id,
            @RequestParam LocalDateTime dateTime
    ){
        return weatherService.getWeatherByStoreIdAndDateTime(id, dateTime);
    }
}
