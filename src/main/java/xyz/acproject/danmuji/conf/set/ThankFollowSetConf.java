package xyz.acproject.danmuji.conf.set;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;

/**
 * @ClassName ThankFollowSetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:14
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ThankFollowSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7461261092620918037L;
	//是否开启
	private boolean is_open = false;
	//是否直播有效
	private boolean is_live_open =false;
	//是否开启屏蔽天选礼物
	private boolean is_tx_shield=false;
	//分段回复
	private short num = 1;
	//发送弹幕
	private String follows="谢谢%uNames%的关注~";
	//发送感谢语延迟时间
	private double delaytime = 0;
	
	public ThankFollowSetConf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	

	public ThankFollowSetConf(boolean is_open, boolean is_live_open, boolean is_tx_shield, short num, String follows,
			double delaytime) {
		super();
		this.is_open = is_open;
		this.is_live_open = is_live_open;
		this.is_tx_shield = is_tx_shield;
		this.num = num;
		this.follows = follows;
		this.delaytime = delaytime;
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
	public short getNum() {
		return num;
	}
	public void setNum(short num) {
		this.num = num;
	}
	public String getFollows() {
		return follows;
	}
	public void setFollows(String follows) {
		this.follows = follows;
	}
	public double getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(double delaytime) {
		this.delaytime = delaytime;
	}



	public boolean isIs_tx_shield() {
		return is_tx_shield;
	}



	public void setIs_tx_shield(boolean is_tx_shield) {
		this.is_tx_shield = is_tx_shield;
	}
	
	
	// 方法区
	public boolean is_followThank(){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		//是否开启仅在直播中运行
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

	public boolean is_followThank(short live_status){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		//是否开启仅在直播中运行
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
