package knu.knu2025scdpteam06backend.domain.forecast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    Forecast getForecastsByStoreIdAndDateTime(Long storeId, LocalDateTime date);
}
