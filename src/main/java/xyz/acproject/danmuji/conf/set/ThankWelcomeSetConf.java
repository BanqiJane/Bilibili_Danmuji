package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName ThankWelcomeConf
 * @Description TODO
 * @date 2021/4/12 23:36
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThankWelcomeSetConf implements Serializable {

    private static final long serialVersionUID = 3606170913548896208L;
    //是否开启
    @JSONField(name = "is_open")
    private boolean is_open = false;
    //是否直播有效
    @JSONField(name = "is_live_open")
    private boolean is_live_open =false;
    //是否开启屏蔽天选礼物
    @JSONField(name = "is_tx_shield")
    private boolean is_tx_shield=false;
    //分段回复
    private short num = 1;
    //发送感谢语延迟时间
    private double delaytime = 0;

    private String welcomes="欢迎%uNames%的进入直播间~";



    //方法区
    public boolean is_welcomeThank(){
        if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
            return false;
        }
        if(is_live_open) {
            //没在直播
            if(PublicDataConf.lIVE_STATUS !=1){
                return false;
            }else{
                if(is_open) {
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            if(is_open) {
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean is_welcomeThank(short live_status){
        if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
            return false;
        }
        if(is_live_open) {
            //没在直播
            if(live_status!=1){
                return false;
            }else{
                if(is_open) {
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            if(is_open) {
                return true;
            }else{
                return false;
            }
        }
    }
}
