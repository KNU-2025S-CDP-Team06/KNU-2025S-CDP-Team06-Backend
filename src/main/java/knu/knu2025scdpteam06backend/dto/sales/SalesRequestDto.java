package knu.knu2025scdpteam06backend.dto.sales;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class SalesRequestDto {
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer startHour;
    Integer endHour;
}
