package knu.knu2025scdpteam06backend.dto.sales;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalesRequestDto {
    String startDate;
    String endDate;
    Integer startHour;
    Integer endHour;
}
