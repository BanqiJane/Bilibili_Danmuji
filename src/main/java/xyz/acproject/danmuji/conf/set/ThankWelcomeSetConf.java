package xyz.acproject.danmuji.conf.set;

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
public class ThankWelcomeSetConf implements Serializable {

    private static final long serialVersionUID = 3606170913548896208L;
    //是否开启
    private boolean is_open = false;
    //是否直播有效
    private boolean is_live_open =false;
    //是否开启屏蔽天选礼物
    private boolean is_tx_shield=false;
    //分段回复
    private short num = 1;
    //发送感谢语延迟时间
    private double delaytime = 0;

    private String welcomes="欢迎%uNames%的进入直播间~";


    public ThankWelcomeSetConf() {
    }


    public ThankWelcomeSetConf(boolean is_open, boolean is_live_open, boolean is_tx_shield, short num, double delaytime, String welcomes) {
        this.is_open = is_open;
        this.is_live_open = is_live_open;
        this.is_tx_shield = is_tx_shield;
        this.num = num;
        this.delaytime = delaytime;
        this.welcomes = welcomes;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public boolean isIs_live_open() {
        return is_live_open;
    }

    public void setIs_live_open(boolean is_live_open) {
        this.is_live_open = is_live_open;
    }

    public boolean isIs_tx_shield() {
        return is_tx_shield;
    }

    public void setIs_tx_shield(boolean is_tx_shield) {
        this.is_tx_shield = is_tx_shield;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    public double getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(double delaytime) {
        this.delaytime = delaytime;
    }

    public String getWelcomes() {
        return welcomes;
    }

    public void setWelcomes(String welcomes) {
        this.welcomes = welcomes;
    }


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
