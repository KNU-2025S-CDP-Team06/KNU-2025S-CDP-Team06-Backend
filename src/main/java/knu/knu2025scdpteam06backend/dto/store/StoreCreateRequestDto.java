package knu.knu2025scdpteam06backend.dto.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateRequestDto {
    private String mbId;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
}
