package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastCreateRequestDto {

    private Long storeId;
    private Integer prophetForecast;
    private Double xgboostForecast;
    private LocalDateTime dateTime;

}
