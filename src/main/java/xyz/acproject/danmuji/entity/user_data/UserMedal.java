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
    private String target_id;
    private String target_face;
    private Long roomid;
    private Long score;

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

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
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
                '}';
    }
}
