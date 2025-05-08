package knu.knu2025scdpteam06backend.domain.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByStoreIdAndDateTimeBetween(Long storeId, LocalDateTime start, LocalDateTime end;
}
