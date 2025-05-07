package knu.knu2025scdpteam06backend.dto.sales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class RealTimeSalesDto {

    @JsonProperty("total_revenue")
    private int totalRevenue;

    @JsonProperty("total_count")
    private int totalCount;

    private List<SalesDataDto> data;

    @Data
    public static class SalesDataDto {
        @JsonProperty("menu_id")
        private Long menuId;
        @JsonProperty("menu_name")
        private String menuName;
        private String date;
        private String hour;
        private int count;
        private int revenue;
    }

}

