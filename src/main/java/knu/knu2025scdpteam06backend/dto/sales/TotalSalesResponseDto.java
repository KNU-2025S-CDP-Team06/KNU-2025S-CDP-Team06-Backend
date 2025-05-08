package knu.knu2025scdpteam06backend.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalSalesResponseDto {

    private int totalRevenue;
    private int totalCount;
    private List<SalesDataDto> salesData;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SalesDataDto {
        private int count;
        private LocalDateTime dateTime;

        private MenuDto menu;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class MenuDto {
            private String name;
            private String image;
            private int price;
        }
    }
}