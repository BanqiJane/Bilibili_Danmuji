package xyz.acproject.danmuji.entity.danmu_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Fans
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:21
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Fans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -418643615266356797L;
	//房间id
	private Long roomid;
	//粉丝数
	private Long fans;
	//以英文为红色警告？？？ 暂且不知道是干什么用的
	private Short red_notice;
	
	public Fans() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public Fans(Long roomid, Long fans, Short red_notice) {
		super();
		this.roomid = roomid;
		this.fans = fans;
		this.red_notice = red_notice;
	}
	public Long getRoomid() {
		return roomid;
	}
	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}
	public Long getFans() {
		return fans;
	}
	public void setFans(Long fans) {
		this.fans = fans;
	}
	public Short getRed_notice() {
		return red_notice;
	}
	public void setRed_notice(Short red_notice) {
		this.red_notice = red_notice;
	}
	@Override
	public String toString() {
		return "Fans [roomid=" + roomid + ", fans=" + fans + ", red_notice=" + red_notice + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new Fans(roomid,fans,red_notice));
	}
}
