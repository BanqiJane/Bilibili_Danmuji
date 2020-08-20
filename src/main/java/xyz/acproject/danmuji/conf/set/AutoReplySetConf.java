package xyz.acproject.danmuji.conf.set;

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
	private boolean is_open = false;
	private boolean is_live_open =false;
	private short time = 3;
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
	
}
