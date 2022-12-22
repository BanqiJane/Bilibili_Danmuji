package xyz.acproject.danmuji.entity.apex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jane
 * @ClassName ApexMessage
 * @Description TODO
 * @date 2022/10/9 0:42
 * @Copyright:2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApexMessage {
    private Date pass_endDownTime;
    private Date shop_refreshTime;
    private String maker_day1;
    private String maker_day1_num;
    private String maker_day2;
    private String maker_day2_num;
    private String maker_week1;
    private String maker_week1_num;
    private String maker_week2;
    private String maker_week2_num;
    private MapLh pp_battle;
    private MapLh pp_arena;
    private MapLh pw_battle;
    private MapLh pw_arena;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MapLh{

        //类型 pp  pw
        private String type;
        private String now_name;
        private String remainder_time;
        private String times;
        private String next_name;

        private String pre_name;


    }


}
