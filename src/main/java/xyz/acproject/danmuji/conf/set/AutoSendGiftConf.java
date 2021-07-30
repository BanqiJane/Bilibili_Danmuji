package xyz.acproject.danmuji.conf.set;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName AutoSendGiftConf
 * @Description TODO
 * @date 2021/7/24 23:40
 * @Copyright:2021
 */
public class AutoSendGiftConf implements Serializable {
    private static final long serialVersionUID = -264415209929286293L;
    //是否开启
    private boolean is_open = false;

    private String room_id;

    private String time = "23:45:00";

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
