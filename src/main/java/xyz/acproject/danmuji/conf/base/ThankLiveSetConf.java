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
    //是否开启屏蔽天选时期的动作
    @JSONField(name = "is_tx_shield")
    private boolean is_tx_shield=false;
    //是否开启屏蔽红包时期的动作
    @JSONField(name = "is_rd_shield")
    private boolean is_rd_shield=false;
    //分段回复
    private short num = 1;
    //发送感谢语延迟时间
    private double delaytime = 3;

    //方法区
    public boolean hasTxShield(){
        return is_tx_shield&&is_open();
    }

    public boolean hasRdShield(){
        return is_rd_shield&&is_open();
    }

    public boolean boolTxShield(Boolean existCache){
        return is_tx_shield&&is_open()&&existCache;
    }

    public boolean boolRdShield(Boolean existCache){
        return is_rd_shield&&is_open()&&existCache;
    }

    public boolean boolTxAndRdShield(Boolean existTxCache,boolean existRdCache){
        return boolRdShield(existRdCache)||boolTxShield(existTxCache);
    }
}
