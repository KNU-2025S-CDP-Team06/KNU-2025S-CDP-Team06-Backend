package knu.knu2025scdpteam06backend.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ForecastResponseDto {

    private Integer totalProphetForecast;
    private List<ForecastItem> forecastData;

    @Data
    @AllArgsConstructor
    public static class ForecastItem {
        private LocalDateTime dateTime;
        private Integer prophetForecast;
        private Double xgboostForecast;
    }

}