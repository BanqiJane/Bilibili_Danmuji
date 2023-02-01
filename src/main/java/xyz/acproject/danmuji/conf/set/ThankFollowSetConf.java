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
public class ThankFollowSetConf extends ThankLiveSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7461261092620918037L;
	//发送弹幕
	private String follows="谢谢%uNames%的关注~";

	
	
	// 方法区
	public boolean is_followThank(){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		//是否开启仅在直播中运行
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

	public boolean is_followThank(short live_status){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		//是否开启仅在直播中运行
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
