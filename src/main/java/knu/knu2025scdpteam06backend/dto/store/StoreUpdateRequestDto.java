package knu.knu2025scdpteam06backend.dto.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreUpdateRequestDto {
    private String mbId;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private Integer cluster;
}

