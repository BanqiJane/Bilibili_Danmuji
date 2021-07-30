package xyz.acproject.danmuji.entity.user_data;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName UserMedal
 * @Description TODO
 * @date 2021/2/6 1:10
 * @Copyright:2021
 */
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

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public Long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(Long target_id) {
        this.target_id = target_id;
    }

    public String getTarget_face() {
        return target_face;
    }

    public void setTarget_face(String target_face) {
        this.target_face = target_face;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Long dayLimit) {
        this.dayLimit = dayLimit;
    }

    public Long getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(Long day_limit) {
        this.day_limit = day_limit;
    }

    public Long getTodayFeed() {
        return todayFeed;
    }

    public void setTodayFeed(Long todayFeed) {
        this.todayFeed = todayFeed;
    }

    public Long getToday_feed() {
        return today_feed;
    }

    public void setToday_feed(Long today_feed) {
        this.today_feed = today_feed;
    }

    public Long getToday_intimacy() {
        return today_intimacy;
    }

    public void setToday_intimacy(Long today_intimacy) {
        this.today_intimacy = today_intimacy;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "UserMedal{" +
                "medalName='" + medalName + '\'' +
                ", level=" + level +
                ", target_name='" + target_name + '\'' +
                ", target_id='" + target_id + '\'' +
                ", target_face='" + target_face + '\'' +
                ", roomid=" + roomid +
                ", score=" + score +
                ", dayLimit=" + dayLimit +
                ", day_limit=" + day_limit +
                ", todayFeed=" + todayFeed +
                ", today_feed=" + today_feed +
                ", today_intimacy=" + today_intimacy +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                '}';
    }
}
