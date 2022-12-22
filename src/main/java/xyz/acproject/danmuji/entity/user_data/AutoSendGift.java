package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName AutoSendGift
 * @Description TODO
 * @date 2021/7/26 12:09
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoSendGift implements Serializable {
    private static final long serialVersionUID = 1247970966228534932L;
    private Integer id;
    private String name;
    private Integer feed;
    private Integer num;
    private Short coin_type;

    public AutoSendGift(Integer id, String name,Integer feed, Short coin_type) {
        this.id = id;
        this.name = name;
        this.feed=feed;
        this.coin_type = coin_type;
    }

}
