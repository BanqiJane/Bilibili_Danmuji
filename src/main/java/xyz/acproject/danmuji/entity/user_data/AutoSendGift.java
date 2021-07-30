package xyz.acproject.danmuji.entity.user_data;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName AutoSendGift
 * @Description TODO
 * @date 2021/7/26 12:09
 * @Copyright:2021
 */
public class AutoSendGift implements Serializable {
    private static final long serialVersionUID = 1247970966228534932L;
    private Integer id;
    private String name;
    private Integer feed;
    private Integer num;
    private Short coin_type;

    public AutoSendGift() {
    }

    public AutoSendGift(Integer id, String name,Integer feed, Short coin_type) {
        this.id = id;
        this.name = name;
        this.feed=feed;
        this.coin_type = coin_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFeed() {
        return feed;
    }

    public void setFeed(Integer feed) {
        this.feed = feed;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Short getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(Short coin_type) {
        this.coin_type = coin_type;
    }
}
