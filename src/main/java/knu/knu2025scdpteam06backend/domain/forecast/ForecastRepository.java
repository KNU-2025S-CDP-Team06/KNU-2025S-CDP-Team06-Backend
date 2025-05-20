package knu.knu2025scdpteam06backend.domain.forecast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> getForecastsByStoreIdAndDateTimeBetween(Long storeId, LocalDateTime startDate, LocalDateTime endDate);
}
