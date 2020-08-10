package xyz.acproject.danmuji.entity.login_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName LoginData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:53
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class LoginData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1326824790653747543L;
	private String oauthKey;
	private String gourl = "https://www.bilibili.com/";
	
	public LoginData() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	
	public LoginData(String oauthKey,String gourl) {
		super();
		this.oauthKey = oauthKey;
		this.gourl = gourl;
		
	}


	public String getGourl() {
		return gourl;
	}
	public void setGourl(String gourl) {
		this.gourl = gourl;
	}
	public String getOauthKey() {
		return oauthKey;
	}
	public void setOauthKey(String oauthKey) {
		this.oauthKey = oauthKey;
	}


	
	
	@Override
	public String toString() {
		return "LoginData [oauthKey=" + oauthKey + ", gourl=" + gourl + "]";
	}


	public String toJson() {
		return FastJsonUtils.toJson(new LoginData(oauthKey,gourl));
	}
}
