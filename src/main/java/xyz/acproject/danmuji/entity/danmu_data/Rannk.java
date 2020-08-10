package xyz.acproject.danmuji.entity.danmu_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Rannk
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:41
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Rannk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2809400486616994730L;
	private Long roomid;
	private String rank_desc;
	private String color;
	private String h5_url;
	private String web_url;
	private Long timestamp;
	public Rannk() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Rannk(Long roomid, String rank_desc, String color, String h5_url, String web_url, Long timestamp) {
		super();
		this.roomid = roomid;
		this.rank_desc = rank_desc;
		this.color = color;
		this.h5_url = h5_url;
		this.web_url = web_url;
		this.timestamp = timestamp;
	}
	public Long getRoomid() {
		return roomid;
	}
	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}
	public String getRank_desc() {
		return rank_desc;
	}
	public void setRank_desc(String rank_desc) {
		this.rank_desc = rank_desc;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getH5_url() {
		return h5_url;
	}
	public void setH5_url(String h5_url) {
		this.h5_url = h5_url;
	}
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Rannk [roomid=" + roomid + ", rank_desc=" + rank_desc + ", color=" + color + ", h5_url=" + h5_url
				+ ", web_url=" + web_url + ", timestamp=" + timestamp + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new Rannk(roomid,rank_desc,color,h5_url,web_url,timestamp));
	}
}
