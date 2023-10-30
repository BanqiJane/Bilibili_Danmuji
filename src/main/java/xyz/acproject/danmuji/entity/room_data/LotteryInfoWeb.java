package xyz.acproject.danmuji.entity.room_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Admin
 * @ClassName LotteryInfoWeb
 * @Description TODO
 * @date 2023/10/23 16:33
 * @Copyright:2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryInfoWeb  implements Serializable{

    private Anchor anchor;

    private Object gift;

    private Object danmu;

    private Object guard;

    private Object pk;

    private List<PopularityRedPocket> popularity_red_pocket;

    private Object red_pocket;


    //tx
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Anchor{
        private String award_name;
        private Integer award_num;

        private Integer award_type;

        private Long current_time;

        private String danmu;

        private Integer time;

        private Integer status;

        private Long room_id;

        private long ruid;

        private Integer require_type;

        private Long id;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PopularityRedPocket{

        private Long current_time;

        private Long end_time;

        private String danmu;

        private Integer join_requirement;

        private Integer last_time;

        private Long lot_id;

        private Integer lot_status;

        private Long remove_time;

        private Long replace_time;

        private Long start_time;

        private BigDecimal total_price;

        private Integer user_status;

        private Integer wait_num;




    }
}
