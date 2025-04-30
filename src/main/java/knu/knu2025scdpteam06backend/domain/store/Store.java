package knu.knu2025scdpteam06backend.domain.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    private Long id; // 매장 ID

    private String mbId; // 사업자번호

    private String name; // 매장명

    private String address; // 매장 도로명 주소

    private String latitude;

    private String longitude;

    @Column(nullable = true)
    private Integer cluster;
}
