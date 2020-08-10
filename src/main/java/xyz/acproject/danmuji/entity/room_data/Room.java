package xyz.acproject.danmuji.entity.room_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Room
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:08
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480650792452371498L;
	//真实房间号
	private String roomid;
	//主播uid
	private String uid;
	//主播公告
	private String content;
	//公告发布时间
	private String time;
	//不知道什么状态
	private String statue;
	//主播名称
	private String uname;
	
	public Room() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public Room(String rommid) {
		super();
		this.roomid = rommid;
	}

	public Room(String roomid, String uid, String content, String time, String statue, String uname) {
		super();
		this.roomid = roomid;
		this.uid = uid;
		this.content = content;
		this.time = time;
		this.statue = statue;
		this.uname = uname;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatue() {
		return statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Room [roomid=" + roomid + ", uid=" + uid + ", content=" + content + ", time=" + time + ", statue="
				+ statue + ", uname=" + uname + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new Room(roomid,uid,content,time,statue,uname));
	}
}
