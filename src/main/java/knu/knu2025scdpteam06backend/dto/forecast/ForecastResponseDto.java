package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastResponseDto {

    private LocalDateTime dateTime;

    private Integer prophetForecast;

    private Double xgboostForecast;
}
