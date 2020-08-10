package xyz.acproject.danmuji.entity.superchat;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:14
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class UserInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4802153428241317283L;
	//用户名称
	private String uname;
	//用户头像
	private String face;
	//用户头像边框
	private String face_frame;
	//舰队类型0n 1总督 2提督 3舰长
	private Short guard_level;
	//用户等级
	private Short user_level;
	//等级背景颜色
	private String level_color;
	//老爷0n 1y
	private Short is_vip;
	//年费老爷0n 1y
	private Short is_svip;
	//是否为大会员
	private Short is_main_vip;
	//什么的标题啊  难道是称号？？？
	private String title;
	//房管0n 1y
	private Short manager;
	
	public UserInfo() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public UserInfo(String uname, String face, String face_frame, Short guard_level, Short user_level,
			String level_color, Short is_vip, Short is_svip, Short is_main_vip, String title, Short manager) {
		super();
		this.uname = uname;
		this.face = face;
		this.face_frame = face_frame;
		this.guard_level = guard_level;
		this.user_level = user_level;
		this.level_color = level_color;
		this.is_vip = is_vip;
		this.is_svip = is_svip;
		this.is_main_vip = is_main_vip;
		this.title = title;
		this.manager = manager;
	}

	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getFace_frame() {
		return face_frame;
	}
	public void setFace_frame(String face_frame) {
		this.face_frame = face_frame;
	}
	public Short getGuard_level() {
		return guard_level;
	}
	public void setGuard_level(Short guard_level) {
		this.guard_level = guard_level;
	}
	public Short getUser_level() {
		return user_level;
	}
	public void setUser_level(Short user_level) {
		this.user_level = user_level;
	}
	public String getLevel_color() {
		return level_color;
	}
	public void setLevel_color(String level_color) {
		this.level_color = level_color;
	}
	public Short getIs_vip() {
		return is_vip;
	}
	public void setIs_vip(Short is_vip) {
		this.is_vip = is_vip;
	}
	public Short getIs_svip() {
		return is_svip;
	}
	public void setIs_svip(Short is_svip) {
		this.is_svip = is_svip;
	}
	public Short getIs_main_vip() {
		return is_main_vip;
	}
	public void setIs_main_vip(Short is_main_vip) {
		this.is_main_vip = is_main_vip;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Short getManager() {
		return manager;
	}
	public void setManager(Short manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "UserInfo [uname=" + uname + ", face=" + face + ", face_frame=" + face_frame + ", guard_level="
				+ guard_level + ", user_level=" + user_level + ", level_color=" + level_color + ", is_vip=" + is_vip
				+ ", is_svip=" + is_svip + ", is_main_vip=" + is_main_vip + ", title=" + title + ", manager=" + manager
				+ "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new UserInfo(uname, face, face_frame, guard_level, user_level, level_color, is_vip, is_svip, is_main_vip, title, manager));
	}
}
