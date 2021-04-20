package xyz.acproject.danmuji.entity.user_data;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName UserManager
 * @Description TODO
 * @date 2021/4/19 22:42
 * @Copyright:2021
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = -6419840093508270008L;
    private Long roomid;
    private Long short_roomid;
    private boolean is_manager;

    public UserManager() {
    }

    public UserManager(Long roomid, Long short_roomid, boolean is_manager) {
        this.roomid = roomid;
        this.short_roomid = short_roomid;
        this.is_manager = is_manager;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Long getShort_roomid() {
        return short_roomid;
    }

    public void setShort_roomid(Long short_roomid) {
        this.short_roomid = short_roomid;
    }

    public boolean isIs_manager() {
        return is_manager;
    }

    public void setIs_manager(boolean is_manager) {
        this.is_manager = is_manager;
    }

    @Override
    public String toString() {
        return "UserManager{" +
                "roomid=" + roomid +
                ", short_roomid=" + short_roomid +
                ", is_manager=" + is_manager +
                '}';
    }
}
