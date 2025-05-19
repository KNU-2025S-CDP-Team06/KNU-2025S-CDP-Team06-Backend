package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.Data;

@Data
public class ForecastCreateRequestDto {

    private Long storeId;
    private Integer prophetForecast;
    private Double xgboostForecast;

}
