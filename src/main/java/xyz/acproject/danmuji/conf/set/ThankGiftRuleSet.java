package xyz.acproject.danmuji.conf.set;

import java.io.Serializable;

/**
 * @ClassName ThankGiftRuleSet
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:19
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ThankGiftRuleSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4838306525238441431L;
	private boolean is_open = false;
	private String gift_name;
	//条件码 0数量  1银瓜子 2金瓜子
	private short status = 0;
	//对应的数量或者瓜子数
	private int num = 0;
	public ThankGiftRuleSet() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public ThankGiftRuleSet(boolean is_open, String gift_name, short status, int num) {
		super();
		this.is_open = is_open;
		this.gift_name = gift_name;
		this.status = status;
		this.num = num;
	}
	public boolean isIs_open() {
		return is_open;
	}
	public void setIs_open(boolean is_open) {
		this.is_open = is_open;
	}
	public String getGift_name() {
		return gift_name;
	}
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
