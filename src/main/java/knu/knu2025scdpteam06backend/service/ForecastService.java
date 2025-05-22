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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final StoreRepository storeRepository;

    public ForecastResponseDto getForecastByStoreId(Long storeId, LocalDateTime startDate, LocalDateTime endDate) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + storeId));

        List<Forecast> forecastList = forecastRepository.getForecastsByStoreIdAndDateTimeBetween(
                store.getId(), startDate, endDate);

        List<ForecastResponseDto.ForecastItem> items = forecastList.stream()
                .map(forecast -> new ForecastResponseDto.ForecastItem(
                        forecast.getDateTime(),
                        forecast.getProphetForecast(),
                        forecast.getXgboostForecast()))
                .sorted(Comparator.comparing(ForecastResponseDto.ForecastItem::getDateTime))
                .toList();

        int total = forecastList.stream()
                .mapToInt(Forecast::getProphetForecast)
                .sum();

        return new ForecastResponseDto(total, items);
    }

    public void addForecast(ForecastCreateRequestDto dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("매장을 찾을 수 없습니다: " + dto.getStoreId()));

        Optional<Forecast> existingForecast = forecastRepository
                .findByStoreIdAndDateTime(dto.getStoreId(), dto.getDateTime());

        if (existingForecast.isPresent()) {
            Forecast forecast = existingForecast.get();
            forecast.setProphetForecast(dto.getProphetForecast());
            forecast.setXgboostForecast(dto.getXgboostForecast());
            forecastRepository.save(forecast); // 업데이트
        } else {
            Forecast newForecast = new Forecast();
            newForecast.setStore(store);
            newForecast.setDateTime(dto.getDateTime());
            newForecast.setProphetForecast(dto.getProphetForecast());
            newForecast.setXgboostForecast(dto.getXgboostForecast());
            forecastRepository.save(newForecast); // 새로 저장
        }
    }
}
