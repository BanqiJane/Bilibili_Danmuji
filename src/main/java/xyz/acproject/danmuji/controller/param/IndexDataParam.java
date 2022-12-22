package xyz.acproject.danmuji.controller.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.entity.user_data.User;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName IndexDataParam
 * @Description TODO
 * @date 2021/4/21 23:17
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
