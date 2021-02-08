package xyz.acproject.danmuji.conf.set;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName ClockInSetConf
 * @Description TODO
 * @date 2021/2/6 13:26
 * @Copyright:2021
 */
public class ClockInSetConf implements Serializable {

    private static final long serialVersionUID = 3354141280551660852L;
    private boolean is_open = false;
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

}
