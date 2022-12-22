package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThankFollowSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7461261092620918037L;
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
	//发送弹幕
	private String follows="谢谢%uNames%的关注~";
	//发送感谢语延迟时间
	private double delaytime = 0;

	
	
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
