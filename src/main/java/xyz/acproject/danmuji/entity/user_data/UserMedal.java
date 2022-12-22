package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName UserMedal
 * @Description TODO
 * @date 2021/2/6 1:10
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMedal implements Serializable {
    private String medalName;
    private Integer level;
    private String target_name;
    private Long target_id;
    private String target_face;
    private Long roomid;
    private Long score;
    private Long dayLimit;
    private Long day_limit;
    private Long todayFeed;
    private Long today_feed;
    private Long today_intimacy;
    private Long uid;
    private String uname;

}
