package xyz.acproject.danmuji.entity.user_in_room_barrageMsg;

import java.io.Serializable;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName UserBarrage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:24
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class UserBarrage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 985041146105605410L;
	private Short mode;
	private Long color;
	private Short length = 20;
	private Long room_id = PublicDataConf.ROOMID;
	public UserBarrage() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public UserBarrage(Short mode, Long color, Short length, Long room_id) {
		super();
		this.mode = mode;
		this.color = color;
		this.length = length;
		this.room_id = room_id;
	}
	public Short getMode() {
		return mode;
	}
	public void setMode(Short mode) {
		this.mode = mode;
	}
	public Long getColor() {
		return color;
	}
	public void setColor(Long color) {
		this.color = color;
	}
	public Short getLength() {
		return length;
	}
	public void setLength(Short length) {
		this.length = length;
	}
	public Long getRoom_id() {
		return room_id;
	}
	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}
	@Override
	public String toString() {
		return "UserBarrage [mode=" + mode + ", color=" + color + ", length=" + length + ", room_id=" + room_id + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new UserBarrage(mode, color, length, room_id));
	}
}
