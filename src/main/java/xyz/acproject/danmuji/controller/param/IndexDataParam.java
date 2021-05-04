package xyz.acproject.danmuji.controller.param;

import xyz.acproject.danmuji.entity.user_data.User;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName IndexDataParam
 * @Description TODO
 * @date 2021/4/21 23:17
 * @Copyright:2021
 */
public class IndexDataParam implements Serializable {
    private static final long serialVersionUID = 2056926334699107131L;

    /**
    * 主播名称
    */
    private String anchorName;
    /**
    * 弹幕姬版本号
    */
    private String edition;

    /**
    * 房间号
    */
    private Long roomId;
    /**
    * 人气值
    */
    private Long popularity;

    /**
    * 是否是管理员
    */
    private Boolean manager;

    /**
    * 用户信息体
    */
    private User user;

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
