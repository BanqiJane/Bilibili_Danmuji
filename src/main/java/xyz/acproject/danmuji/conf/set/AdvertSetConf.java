package xyz.acproject.danmuji.conf.set;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.base.StartThreadInterface;
import xyz.acproject.danmuji.conf.base.TimingLiveSetConf;

import java.io.Serializable;

/**
 * @ClassName AdvertSetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:20:57
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertSetConf extends TimingLiveSetConf implements Serializable, StartThreadInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -643702235901579872L;
	//如何发送 0 1
	private short status=0;
	//发送语
	private String adverts;

	private double time2=0;


	//方法区
	@Override
	public void start(ThreadComponent threadComponent){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return;
		}
		if (is_live_open()) {
			if (PublicDataConf.lIVE_STATUS != 1) {
				threadComponent.closeAdvertThread();
			} else {
				if (is_open()) {
					threadComponent.startAdvertThread(this);
				} else {
					threadComponent.setAdvertThread(this);
					threadComponent.closeAdvertThread();
				}
			}
		} else {
			if (is_open()) {
				threadComponent.startAdvertThread(this);
			} else {
				threadComponent.setAdvertThread(this);
				threadComponent.closeAdvertThread();
			}
		}
	}
	
	
}
