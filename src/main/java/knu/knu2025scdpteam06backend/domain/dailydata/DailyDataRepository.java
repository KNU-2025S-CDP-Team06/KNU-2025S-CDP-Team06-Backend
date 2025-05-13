package knu.knu2025scdpteam06backend.domain.dailydata;

import knu.knu2025scdpteam06backend.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DailyDataRepository extends JpaRepository<DailyData, Long> {
    List<DailyData> findByStoreIdAndDateTimeBetween(Long storeId, LocalDateTime start, LocalDateTime end);
}
