package knu.knu2025scdpteam06backend.domain.store;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 매장 ID

    private String mbId; // 사업자번호

    private String name; // 매장명

    private String address; // 매장 도로명 주소

    private String latitude;

    private String longitude;

    @Column(nullable = true)
    private Integer cluster;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
