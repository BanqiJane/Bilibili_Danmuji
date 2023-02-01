package xyz.acproject.danmuji.conf.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Admin
 * @ClassName OpenSetConf
 * @Description TODO
 * @date 2023/1/13 9:00
 * @Copyright:2023
 */
@Data
public abstract class OpenSetConf {
    //是否开启
    @JSONField(name = "is_open")
    private boolean is_open = false;
}
