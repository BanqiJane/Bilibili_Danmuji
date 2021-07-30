package xyz.acproject.danmuji.entity.user_data;

import java.util.List;

/**
 * @author Jane
 * @ClassName UserBag
 * @Description TODO
 * @date 2021/7/24 23:09
 * @Copyright:2021
 */
public class UserBag {
    private Long bag_id;
    private Integer gift_id;
    private String gift_name;
    private Integer gift_num;
    private Integer gift_type;
    private Integer expire_at;
    private String corner_mark;
    private String corner_color;
    private List<UserBagCount> count_map;
    private Integer bind_roomid;
    private String bind_room_text;
    private Integer type;
    private String card_image;
    private String card_gif;
    private Integer card_id;
    private Integer card_record_id;
    private boolean is_show_send;
    private Integer feed;

    public Long getBag_id() {
        return bag_id;
    }

    public void setBag_id(Long bag_id) {
        this.bag_id = bag_id;
    }

    public Integer getGift_id() {
        return gift_id;
    }

    public void setGift_id(Integer gift_id) {
        this.gift_id = gift_id;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public Integer getGift_num() {
        return gift_num;
    }

    public void setGift_num(Integer gift_num) {
        this.gift_num = gift_num;
    }

    public Integer getGift_type() {
        return gift_type;
    }

    public void setGift_type(Integer gift_type) {
        this.gift_type = gift_type;
    }

    public Integer getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(Integer expire_at) {
        this.expire_at = expire_at;
    }

    public String getCorner_mark() {
        return corner_mark;
    }

    public void setCorner_mark(String corner_mark) {
        this.corner_mark = corner_mark;
    }

    public String getCorner_color() {
        return corner_color;
    }

    public void setCorner_color(String corner_color) {
        this.corner_color = corner_color;
    }

    public List<UserBagCount> getCount_map() {
        return count_map;
    }

    public void setCount_map(List<UserBagCount> count_map) {
        this.count_map = count_map;
    }

    public Integer getBind_roomid() {
        return bind_roomid;
    }

    public void setBind_roomid(Integer bind_roomid) {
        this.bind_roomid = bind_roomid;
    }

    public String getBind_room_text() {
        return bind_room_text;
    }

    public void setBind_room_text(String bind_room_text) {
        this.bind_room_text = bind_room_text;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCard_image() {
        return card_image;
    }

    public void setCard_image(String card_image) {
        this.card_image = card_image;
    }

    public String getCard_gif() {
        return card_gif;
    }

    public void setCard_gif(String card_gif) {
        this.card_gif = card_gif;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Integer getCard_record_id() {
        return card_record_id;
    }

    public void setCard_record_id(Integer card_record_id) {
        this.card_record_id = card_record_id;
    }

    public boolean isIs_show_send() {
        return is_show_send;
    }

    public void setIs_show_send(boolean is_show_send) {
        this.is_show_send = is_show_send;
    }

    public Integer getFeed() {
        return feed;
    }

    public void setFeed(Integer feed) {
        this.feed = feed;
    }

    @Override
    public String toString() {
        return "UserBag{" +
                "bag_id=" + bag_id +
                ", gift_id=" + gift_id +
                ", gift_name='" + gift_name + '\'' +
                ", gift_num=" + gift_num +
                ", gift_type=" + gift_type +
                ", expire_at=" + expire_at +
                ", corner_mark='" + corner_mark + '\'' +
                ", corner_color='" + corner_color + '\'' +
                ", count_map=" + count_map +
                ", bind_roomid=" + bind_roomid +
                ", bind_room_text='" + bind_room_text + '\'' +
                ", type=" + type +
                ", card_image='" + card_image + '\'' +
                ", card_gif='" + card_gif + '\'' +
                ", card_id=" + card_id +
                ", card_record_id=" + card_record_id +
                ", is_show_send=" + is_show_send +
                '}';
    }
}
