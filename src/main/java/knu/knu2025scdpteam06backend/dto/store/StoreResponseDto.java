package knu.knu2025scdpteam06backend.dto.store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private Long id;
    private String mbId;
    private String name;
    private String address;

    public StoreResponseDto(Long id, String mbId, String name, String address {
        this.id = id;
        this.mbId = mbId;
        this.name = name;
        this.address = address;
    }
}
