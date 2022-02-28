package xyz.acproject.danmuji.conf.set;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;

/**
 * @ClassName AdvertSetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:20:57
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class AdvertSetConf implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -643702235901579872L;
	//是否开启
	private boolean is_open =false;
	//是否直播有效
	private boolean is_live_open = false;
	//如何发送 0 1
	private short status=0;
	//time 秒数
	private short time=0;
	//发送语
	private String adverts;
	
	
	public AdvertSetConf() {
		super();
		// TODO 自动生成的构造函数存根
	}


	public AdvertSetConf(boolean is_open, boolean is_live_open, short status, short time, String adverts) {
		super();
		this.is_open = is_open;
		this.is_live_open = is_live_open;
		this.status = status;
		this.time = time;
		this.adverts = adverts;
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


	public short getStatus() {
		return status;
	}


	public void setStatus(short status) {
		this.status = status;
	}


	public short getTime() {
		return time;
	}


	public void setTime(short time) {
		this.time = time;
	}


	public String getAdverts() {
		return adverts;
	}


	public void setAdverts(String adverts) {
		this.adverts = adverts;
	}


	//方法区

	public void start(ThreadComponent threadComponent){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return;
		}
		if (is_live_open) {
			if (PublicDataConf.lIVE_STATUS != 1) {
				threadComponent.closeAdvertThread();
			} else {
				if (is_open) {
					threadComponent.startAdvertThread(this);
				} else {
					threadComponent.setAdvertThread(this);
					threadComponent.closeAdvertThread();
				}
			}
		} else {
			if (is_open) {
				threadComponent.startAdvertThread(this);
			} else {
				threadComponent.setAdvertThread(this);
				threadComponent.closeAdvertThread();
			}
		}
	}
	
	
}
