package xyz.acproject.danmuji.entity.Welcome;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName WelcomeGuard
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:44
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class WelcomeGuard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2312238019705486880L;
	//用户uid
	private Long uid;
	//用户名称
	private String username;
	//舰长等级 0 1总督 2提督 3舰长
	private Short guard_level;
	//未知
	private Short mock_effect;
	public WelcomeGuard() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public WelcomeGuard(Long uid, String username, Short guard_level, Short mock_effect) {
		super();
		this.uid = uid;
		this.username = username;
		this.guard_level = guard_level;
		this.mock_effect = mock_effect;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Short getGuard_level() {
		return guard_level;
	}
	public void setGuard_level(Short guard_level) {
		this.guard_level = guard_level;
	}
	public Short getMock_effect() {
		return mock_effect;
	}
	public void setMock_effect(Short mock_effect) {
		this.mock_effect = mock_effect;
	}
	@Override
	public String toString() {
		return "WelcomeGuard [uid=" + uid + ", username=" + username + ", guard_level=" + guard_level + ", mock_effect="
				+ mock_effect + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new WelcomeGuard(uid,username,guard_level,mock_effect));
	}
}
