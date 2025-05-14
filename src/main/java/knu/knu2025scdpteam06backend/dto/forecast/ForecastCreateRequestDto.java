package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.Data;

@Data
public class ForecastCreateRequestDto {

    private Integer prophetForecast;
    private Double xgboostForecast;

}
