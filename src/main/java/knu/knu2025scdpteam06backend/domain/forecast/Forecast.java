package knu.knu2025scdpteam06backend.domain.forecast;

import jakarta.persistence.*;
import knu.knu2025scdpteam06backend.domain.store.Store;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "forecast")
@Data
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "date")
    private LocalDateTime dateTime;

    private Integer prophetForecast;

    private Double xgboostForecast;


}
