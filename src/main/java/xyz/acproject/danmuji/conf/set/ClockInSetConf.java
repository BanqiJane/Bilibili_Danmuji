package xyz.acproject.danmuji.conf.set;

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
public class ClockInSetConf implements Serializable {

    private static final long serialVersionUID = 3354141280551660852L;
    /**
    * 是否开启自动打卡
    */
    private boolean is_open = false;

    /**
    * 自定义打卡时间
    */
    private String time = "00:35:00";
    /**
    * 自动打卡发送的弹幕
    */
    private String barrage = "签到";

    public ClockInSetConf() {
        super();
    }

    public ClockInSetConf(boolean is_open, String barrage) {
        super();
        this.is_open = is_open;
        this.barrage = barrage;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public String getBarrage() {
        return barrage;
    }

    public void setBarrage(String barrage) {
        this.barrage = barrage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ClockInSetConf time(Date date){
        this.time = JodaTimeUtils.format(date,"HH:mm:ss");
        return this;
    }


}
