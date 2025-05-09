package knu.knu2025scdpteam06backend.domain.weather;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByStoreIdAndDateTime(Long storeId, LocalDateTime dateTime);
}
