package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.forecast.Forecast;
import knu.knu2025scdpteam06backend.domain.forecast.ForecastRepository;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.forecast.ForecastResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final StoreRepository storeRepository;

    public ForecastResponseDto getForecastByStore(String mbId, LocalDateTime dateTime) {
        Store store = storeRepository.findByMbId(mbId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + mbId));

        Forecast forecast = forecastRepository.getForecastsByStoreIdAndDateTime(store.getId(), dateTime);
        return ForecastResponseDto.builder()
                .prophetForecast(forecast.getProphetForecast())
                .xgboostForecast(forecast.getXgboostForecast())
                .build();

    }
}
