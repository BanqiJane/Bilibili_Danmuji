package xyz.acproject.danmuji.entity.danmu_data;

import java.io.Serializable;

/**
 * @ClassName Interact
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:37
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Interact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8108196758728766627L;
	private Long uid;
	private String uname;
	private String uname_color;
	private Integer[] identities;
	private Short msg_type;
	private Long roomid;
	private Long timestamp;
	private Long score;
	public Interact() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Interact(Long uid, String uname, String uname_color, Integer[] identities, Short msg_type, Long roomid,
			Long timestamp, Long score) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.uname_color = uname_color;
		this.identities = identities;
		this.msg_type = msg_type;
		this.roomid = roomid;
		this.timestamp = timestamp;
		this.score = score;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUname_color() {
		return uname_color;
	}
	public void setUname_color(String uname_color) {
		this.uname_color = uname_color;
	}
	public Integer[] getIdentities() {
		return identities;
	}
	public void setIdentities(Integer[] identities) {
		this.identities = identities;
	}
	public Short getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(Short msg_type) {
		this.msg_type = msg_type;
	}
	public Long getRoomid() {
		return roomid;
	}
	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	
	}
