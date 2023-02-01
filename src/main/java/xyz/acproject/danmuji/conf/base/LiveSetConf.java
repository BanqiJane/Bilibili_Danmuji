package xyz.acproject.danmuji.conf.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Admin
 * @ClassName LiveSetConf
 * @Description TODO
 * @date 2023/1/13 9:11
 * @Copyright:2023
 */
@Data
public abstract class LiveSetConf extends OpenSetConf{
    //是否直播有效
    @JSONField(name = "is_live_open")
    private boolean is_live_open = false;
}
