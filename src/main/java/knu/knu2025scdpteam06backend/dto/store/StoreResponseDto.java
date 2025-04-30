package knu.knu2025scdpteam06backend.dto.store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String mbId;
    private String name;
    private String address;

    public StoreResponseDto(String mbId, String name, String address {
        this.mbId = mbId;
        this.name = name;
        this.address = address;
    }
}
