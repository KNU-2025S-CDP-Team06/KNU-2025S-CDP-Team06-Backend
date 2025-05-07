package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastResponseDto {
    private Integer prophetForecast;

    private Double xgboostForecast;
}
