package xyz.acproject.danmuji.conf.set;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @ClassName AutoReplySet
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:02
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class AutoReplySet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 562887876061232840L;
	private boolean is_open = false;
	private boolean is_accurate = false;
	private HashSet<String> keywords;
	private HashSet<String> shields;
	private String reply;
	public AutoReplySet() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public AutoReplySet(boolean is_open, boolean is_accurate, HashSet<String> keywords, HashSet<String> shields,
			String reply) {
		super();
		this.is_open = is_open;
		this.is_accurate = is_accurate;
		this.keywords = keywords;
		this.shields = shields;
		this.reply = reply;
	}

	public boolean isIs_open() {
		return is_open;
	}
	public void setIs_open(boolean is_open) {
		this.is_open = is_open;
	}
	public boolean isIs_accurate() {
		return is_accurate;
	}

	public void setIs_accurate(boolean is_accurate) {
		this.is_accurate = is_accurate;
	}

	public HashSet<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(HashSet<String> keywords) {
		this.keywords = keywords;
	}
	public HashSet<String> getShields() {
		return shields;
	}
	public void setShields(HashSet<String> shields) {
		this.shields = shields;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
}
