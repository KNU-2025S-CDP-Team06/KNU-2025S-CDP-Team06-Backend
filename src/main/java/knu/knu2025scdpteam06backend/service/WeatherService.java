package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.domain.weather.Weather;
import knu.knu2025scdpteam06backend.domain.weather.WeatherRepository;
import knu.knu2025scdpteam06backend.dto.Weather.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final StoreRepository storeRepository;

    public WeatherResponseDto getWeatherByStoreIdAndDateTime(Long storeId, LocalDateTime dateTime){
        Optional<Store> store = storeRepository.findById(storeId);

        Weather weather =  weatherRepository.findByStoreIdAndDateTime(store.get().getId(), dateTime);

        return WeatherResponseDto.builder()
                .feeling(weather.getFeeling())
                .precipitation(weather.getPrecipitation())
                .weather(weather.getWeather())
                .build();
    }
}
