package xyz.acproject.danmuji.entity.danmu_data;

import lombok.Data;
import xyz.acproject.danmuji.entity.superchat.MedalInfo;

import java.io.Serializable;

/**
 * @author Admin
 * @ClassName RedPackage
 * @Description TODO
 * @date 2023/1/29 16:31
 * @Copyright:2023
 */
@Data
public class RedPackage implements Serializable {
    private Long lot_id;
    private Long start_time;
    private Long current_time;

    private Integer wait_num;

    private String uname;

    private Long uid;

    private String action;

    private Integer num;

    private String gift_name;

    private Long gift_id;

    private Integer price;

    private String name_color;

    private MedalInfo medal_info;
}
