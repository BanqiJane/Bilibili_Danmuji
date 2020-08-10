package xyz.acproject.danmuji.entity.superchat;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName MedalInfo
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:06
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class MedalInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8288034568124823988L;
	//勋章头图标id 如果没有就0 如果有就指定id
	private Integer icon_id;
	//不知道什么id
	private Integer target_id;
	//特殊？？？不知道什么
	private String special;
	//勋章所属主播名字
	private String anchor_uname;
	//勋章所属主播房间
	private String anchor_roomid;
	//勋章等级
	private Short medal_level;
	//勋章名字
	private String medal_name;
	//勋章颜色
	private String medal_color;
	public MedalInfo() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public MedalInfo(Integer icon_id, Integer target_id, String special, String anchor_uname, String anchor_roomid,
			Short medal_level, String medal_name, String medal_color) {
		super();
		this.icon_id = icon_id;
		this.target_id = target_id;
		this.special = special;
		this.anchor_uname = anchor_uname;
		this.anchor_roomid = anchor_roomid;
		this.medal_level = medal_level;
		this.medal_name = medal_name;
		this.medal_color = medal_color;
	}
	public Integer getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(Integer icon_id) {
		this.icon_id = icon_id;
	}
	public Integer getTarget_id() {
		return target_id;
	}
	public void setTarget_id(Integer target_id) {
		this.target_id = target_id;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getAnchor_uname() {
		return anchor_uname;
	}
	public void setAnchor_uname(String anchor_uname) {
		this.anchor_uname = anchor_uname;
	}
	public String getAnchor_roomid() {
		return anchor_roomid;
	}
	public void setAnchor_roomid(String anchor_roomid) {
		this.anchor_roomid = anchor_roomid;
	}
	public Short getMedal_level() {
		return medal_level;
	}
	public void setMedal_level(Short medal_level) {
		this.medal_level = medal_level;
	}
	public String getMedal_name() {
		return medal_name;
	}
	public void setMedal_name(String medal_name) {
		this.medal_name = medal_name;
	}
	public String getMedal_color() {
		return medal_color;
	}
	public void setMedal_color(String medal_color) {
		this.medal_color = medal_color;
	}
	@Override
	public String toString() {
		return "MedalInfo [icon_id=" + icon_id + ", target_id=" + target_id + ", special=" + special + ", anchor_uname="
				+ anchor_uname + ", anchor_roomid=" + anchor_roomid + ", medal_level=" + medal_level + ", medal_name="
				+ medal_name + ", medal_color=" + medal_color + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new MedalInfo(icon_id, target_id, special, anchor_uname, anchor_roomid, medal_level, medal_name, medal_color));
	}
}
