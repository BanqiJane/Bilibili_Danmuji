package xyz.acproject.danmuji.entity;

import java.io.Serializable;
import java.util.Date;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480650792452371498L;
	private String roomid;
	private String uid;
	private String content;
	private String time;
	private String statue;
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
	
}
