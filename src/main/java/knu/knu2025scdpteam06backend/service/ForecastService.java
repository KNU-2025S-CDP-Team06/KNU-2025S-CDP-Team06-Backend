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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final StoreRepository storeRepository;

    public List<ForecastResponseDto> getForecastByStoreId(Long storeId, LocalDateTime startDate, LocalDateTime endDate) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + storeId));

        List<Forecast> forecastList = forecastRepository.getForecastsByStoreIdAndDateTimeBetween(store.getId(), startDate, endDate);
        List<ForecastResponseDto> responseDtoList = new ArrayList<>();
        for (Forecast forecast : forecastList) {

            ForecastResponseDto result =  ForecastResponseDto.builder()
                    .dateTime(forecast.getDateTime())
                    .prophetForecast(forecast.getProphetForecast())
                    .xgboostForecast(forecast.getXgboostForecast())
                    .build();
            responseDtoList.add(result);
            responseDtoList.sort(Comparator.comparing(ForecastResponseDto::getDateTime));
        }
        return responseDtoList;

    }

    public void addForecast(ForecastCreateRequestDto forecastCreateRequestDto){
        Store store = storeRepository.findById(forecastCreateRequestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + forecastCreateRequestDto.getStoreId()));

        Forecast forecast = Forecast.builder().
                store(store).
                dateTime(forecastCreateRequestDto.getDateTime()).
                prophetForecast(forecastCreateRequestDto.getProphetForecast()).
                xgboostForecast(forecastCreateRequestDto.getXgboostForecast()).
                build();

        forecastRepository.save(forecast);
    }
}
