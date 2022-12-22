package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName AutoSendGiftConf
 * @Description TODO
 * @date 2021/7/24 23:40
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoSendGiftConf implements Serializable {
    private static final long serialVersionUID = -264415209929286293L;
    //是否开启
    @JSONField(name = "is_open")
    private boolean is_open = false;

    private String room_id;

    private String time = "23:45:00";

}
