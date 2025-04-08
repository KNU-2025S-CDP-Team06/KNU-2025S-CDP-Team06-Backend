package knu.knu2025scdpteam06backend.domain.dailydata;

import jakarta.persistence.*;
import knu.knu2025scdpteam06backend.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_data", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"storeId", "date"}
}
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY
    @JoinColumn(name = "store_id", nullable = false
    private Store store;

    private LocalDateTime dateTime;

    private Integer totalRevenue;

    private Integer totalCount;
}
