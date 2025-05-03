package knu.knu2025scdpteam06backend.domain.sales;

import jakarta.persistence.*;
import knu.knu2025scdpteam06backend.domain.dailydata.DailyData;
import knu.knu2025scdpteam06backend.domain.menu.Menu;
import knu.knu2025scdpteam06backend.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_data_id", nullable = false)
    private DailyData dailyData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private Integer count;

    private LocalDateTime datetime;
}
