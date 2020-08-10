package xyz.acproject.danmuji.entity.Welcome;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName WelcomeVip
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:48
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class WelcomeVip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -823797545698723949L;
	//用户uid
	private Long uid;
	//用户名称
	private String uname;
	//是否为管理员
	private Boolean is_admin;
	//年费老爷
	private Short svip;
	//老爷
	private Short vip;
	//未知
	private Short mock_effect;
	public WelcomeVip() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public WelcomeVip(Long uid, String uname, Boolean is_admin, Short svip, Short vip, Short mock_effect) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.is_admin = is_admin;
		this.svip = svip;
		this.vip = vip;
		this.mock_effect = mock_effect;
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
	public Boolean getIs_admin() {
		return is_admin;
	}
	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
	}
	public Short getSvip() {
		return svip;
	}
	public void setSvip(Short svip) {
		this.svip = svip;
	}
	public Short getVip() {
		return vip;
	}
	public void setVip(Short vip) {
		this.vip = vip;
	}
	public Short getMock_effect() {
		return mock_effect;
	}
	public void setMock_effect(Short mock_effect) {
		this.mock_effect = mock_effect;
	}
	@Override
	public String toString() {
		return "WelcomVip [uid=" + uid + ", uname=" + uname + ", is_admin=" + is_admin + ", svip=" + svip + ", vip="
				+ vip + ", mock_effect=" + mock_effect + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new WelcomeVip(uid,uname,is_admin,svip,vip,mock_effect));
	}
}
