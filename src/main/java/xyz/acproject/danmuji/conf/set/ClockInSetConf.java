package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.conf.base.OpenSetConf;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jane
 * @ClassName ClockInSetConf
 * @Description TODO
 * @date 2021/2/6 13:26
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClockInSetConf extends OpenSetConf implements Serializable {

    private static final long serialVersionUID = 3354141280551660852L;

    /**
    * 自定义打卡时间
    */
    private String time = "00:35:00";
    /**
    * 自动打卡发送的弹幕
    */
    private String barrage = "签到";


    public ClockInSetConf(boolean is_open, String barrage) {
        super();
        super.set_open(is_open);
        this.barrage = barrage;
    }


    public ClockInSetConf time(Date date){
        this.time = JodaTimeUtils.format(date,"HH:mm:ss");
        return this;
    }


}
