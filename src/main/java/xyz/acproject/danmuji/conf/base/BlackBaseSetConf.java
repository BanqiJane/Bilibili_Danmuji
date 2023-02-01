package xyz.acproject.danmuji.conf.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Admin
 * @ClassName BlackBaseSetConf
 * @Description TODO
 * @date 2023/1/29 17:00
 * @Copyright:2023
 */
@Data
public class BlackBaseSetConf {
    @JSONField(name = "is_black")
    private boolean is_black = false;
}
