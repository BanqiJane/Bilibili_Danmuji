package xyz.acproject.danmuji.conf.set;

import java.io.Serializable;

public class ThankFollowSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7461261092620918037L;
	private boolean is_open = false;
	private boolean is_live_open =false;
	//分段回复
	private short num = 1;
	private String follows="谢谢%uNames%的关注~";
	public ThankFollowSetConf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public ThankFollowSetConf(boolean is_open, boolean is_live_open, short num,
			String follows) {
		super();
		this.is_open = is_open;
		this.is_live_open = is_live_open;
		this.num = num;
		this.follows = follows;
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
	
	
	
	
	
}
