package knu.knu2025scdpteam06backend.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesResponseDto {

    private String date;  // yyyy-MM-dd
    private int totalRevenue;
    private int totalCount;
    private List<SalesDataDto> salesData;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SalesDataDto {
        private int count;
        private String datetime;  // yyyy-MM-dd-HH

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