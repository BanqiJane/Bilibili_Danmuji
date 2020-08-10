package xyz.acproject.danmuji.entity.superchat;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName GiftChat
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:26:57
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class GiftChat implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1794734764847323793L;
	private Integer num;
	private Integer gift_id;
	private String gift_name;
	public GiftChat() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public GiftChat(Integer num, Integer gift_id, String gift_name) {
		super();
		this.num = num;
		this.gift_id = gift_id;
		this.gift_name = gift_name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getGift_id() {
		return gift_id;
	}
	public void setGift_id(Integer gift_id) {
		this.gift_id = gift_id;
	}
	public String getGift_name() {
		return gift_name;
	}
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
	}
	@Override
	public String toString() {
		return "GiftChat [num=" + num + ", gift_id=" + gift_id + ", gift_name=" + gift_name + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new GiftChat(num, gift_id, gift_name));
	}
}
