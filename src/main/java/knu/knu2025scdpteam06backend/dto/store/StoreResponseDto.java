package knu.knu2025scdpteam06backend.dto.store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String mbId;
    private String name;
    private String address;
}
