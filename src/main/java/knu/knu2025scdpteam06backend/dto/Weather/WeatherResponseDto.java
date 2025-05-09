package knu.knu2025scdpteam06backend.dto.Weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WeatherResponseDto {
    private double precipitation;
    private double feeling;
    private String weather;
}
