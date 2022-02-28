package xyz.acproject.danmuji.conf.set;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @ClassName AutoReplySetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:06
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class AutoReplySetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6387301110915854706L;
	//是否开启
	private boolean is_open = false;
	//是否直播有效
	private boolean is_live_open =false;
	//间隔时间
	private short time = 3;
	//自动回复子对象集合
	private HashSet<AutoReplySet> autoReplySets;
	
	
	public AutoReplySetConf() {
		super();
		// TODO 自动生成的构造函数存根
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
	public short getTime() {
		return time;
	}
	public void setTime(short time) {
		this.time = time;
	}
	
	public HashSet<AutoReplySet> getAutoReplySets() {
		return autoReplySets;
	}
	public void setAutoReplySets(HashSet<AutoReplySet> autoReplySets) {
		this.autoReplySets = autoReplySets;
	}



	//方法区

	public void start(ThreadComponent threadComponent){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return;
		}
		if (is_live_open) {
			if (PublicDataConf.lIVE_STATUS != 1) {
				threadComponent.closeAutoReplyThread();
			} else {
				if (is_open) {
					threadComponent.startAutoReplyThread(this);
				} else {
					threadComponent.setAutoReplyThread(this);
					threadComponent.closeAutoReplyThread();
				}
			}
		} else {
			if (is_open) {
				threadComponent.startAutoReplyThread(this);
			} else {
				threadComponent.setAutoReplyThread(this);
				threadComponent.closeAutoReplyThread();
			}
		}
	}
}
