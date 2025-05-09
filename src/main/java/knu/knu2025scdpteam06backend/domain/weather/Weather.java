package knu.knu2025scdpteam06backend.domain.weather;

import jakarta.persistence.*;
import knu.knu2025scdpteam06backend.domain.store.Store;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "date")
    private LocalDateTime dateTime;

    private Integer weekday;

    private Double feeling;

    @Column(nullable = true)
    private Double precipitation;

    private String weather;
}
