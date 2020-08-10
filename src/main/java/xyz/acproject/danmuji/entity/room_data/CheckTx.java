package xyz.acproject.danmuji.entity.room_data;

import java.io.Serializable;

/**
 * @ClassName CheckTx
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:03
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class CheckTx implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6849509148214688485L;
	private Long room_id;
	private String gift_name;
	private Short time;
	
	
	public CheckTx() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public CheckTx(Long room_id, String gift_name, Short time) {
		super();
		this.room_id = room_id;
		this.gift_name = gift_name;
		this.time = time;
	}

	public Long getRoom_id() {
		return room_id;
	}
	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}
	public String getGift_name() {
		return gift_name;
	}
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
	}
	public Short getTime() {
		return time;
	}
	public void setTime(Short time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "CheckTx [room_id=" + room_id + ", gift_name=" + gift_name + ", time=" + time + "]";
	}
	
}
