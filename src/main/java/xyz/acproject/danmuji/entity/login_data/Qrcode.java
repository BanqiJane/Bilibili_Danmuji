package xyz.acproject.danmuji.entity.login_data;



import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Qrcode
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:58
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Qrcode implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8700211079867769292L;
	private String url;
	private String oauthKey;
	
	
	public Qrcode() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	
	public Qrcode(String url, String oauthKey) {
		super();
		this.url = url;
		this.oauthKey = oauthKey;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOauthKey() {
		return oauthKey;
	}
	public void setOauthKey(String oauthKey) {
		this.oauthKey = oauthKey;
	}


	@Override
	public String toString() {
		return "Qrcode [url=" + url + ", oauthKey=" + oauthKey + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new Qrcode(url, oauthKey));
	}
	
}
