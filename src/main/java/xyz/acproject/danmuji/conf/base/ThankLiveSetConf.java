package xyz.acproject.danmuji.conf.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author Admin
 * @ClassName ThankLiveSetConf
 * @Description TODO
 * @date 2023/1/13 9:52
 * @Copyright:2023
 */
@Data
public abstract class ThankLiveSetConf extends LiveSetConf{
    //是否开启屏蔽天选礼物
    @JSONField(name = "is_tx_shield")
    private boolean is_tx_shield=false;
    //分段回复
    private short num = 1;
    //发送感谢语延迟时间
    private double delaytime = 3;
}
