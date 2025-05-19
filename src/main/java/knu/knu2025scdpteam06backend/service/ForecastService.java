package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.forecast.Forecast;
import knu.knu2025scdpteam06backend.domain.forecast.ForecastRepository;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.forecast.ForecastCreateRequestDto;
import knu.knu2025scdpteam06backend.dto.forecast.ForecastResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final StoreRepository storeRepository;

    public ForecastResponseDto getForecastByStoreId(Long storeId, LocalDateTime dateTime) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + storeId));

        Forecast forecast = forecastRepository.getForecastsByStoreIdAndDateTime(store.getId(), dateTime);
        return ForecastResponseDto.builder()
                .prophetForecast(forecast.getProphetForecast())
                .xgboostForecast(forecast.getXgboostForecast())
                .build();

    }

    public void addForecast(ForecastCreateRequestDto forecastCreateRequestDto){
        Store store = storeRepository.findById(forecastCreateRequestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + forecastCreateRequestDto.getStoreId()));

        Forecast forecast = Forecast.builder().
                store(store).
                dateTime(LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT)).
                prophetForecast(forecastCreateRequestDto.getProphetForecast()).
                xgboostForecast(forecastCreateRequestDto.getXgboostForecast()).
                build();

        forecastRepository.save(forecast);
    }
}
