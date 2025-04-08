package knu.knu2025scdpteam06backend.domain.sales;

import knu.knu2025scdpteam06backend.domain.dailydata.DailyData;
import knu.knu2025scdpteam06backend.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findByDailyDataAndMenu(DailyData dailyData, Menu menu);
}
