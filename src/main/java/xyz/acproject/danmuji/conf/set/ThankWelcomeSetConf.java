package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.base.ThankLiveSetConf;

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
public class ThankWelcomeSetConf extends ThankLiveSetConf implements Serializable {

    private static final long serialVersionUID = 3606170913548896208L;
    //是否开启本人
    @JSONField(name = "is_open_self")
    private boolean is_open_self = false;
    //人员感谢过滤 0全部 1仅勋章 2仅舰长
    @JSONField(name = "list_people_shield_status")
    private short list_people_shield_status = 0;

    private String welcomes="欢迎%uNames%的进入直播间~";



    //方法区
    public boolean is_welcomeThank(){
        if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
            return false;
        }
        if(is_live_open()) {
            //没在直播
            if(PublicDataConf.lIVE_STATUS !=1){
                return false;
            }else{
                if(is_open()) {
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            if(is_open()) {
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
        if(is_live_open()) {
            //没在直播
            if(live_status!=1){
                return false;
            }else{
                if(is_open()) {
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            if(is_open()) {
                return true;
            }else{
                return false;
            }
        }
    }
}
