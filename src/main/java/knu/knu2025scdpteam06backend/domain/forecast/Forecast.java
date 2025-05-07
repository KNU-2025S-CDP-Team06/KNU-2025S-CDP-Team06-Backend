package knu.knu2025scdpteam06backend.domain.forecast;

import jakarta.persistence.*;
import knu.knu2025scdpteam06backend.domain.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "forecast"
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY
    @JoinColumn(name = "store_id", nullable = false
    private Store store;

    private LocalDateTime date;

    private Integer prophetForecast;

    private Double xgboostForecast;


}
