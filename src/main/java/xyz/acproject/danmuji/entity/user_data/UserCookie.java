package xyz.acproject.danmuji.entity.user_data;

import java.io.Serializable;

/**
 * @ClassName UserCookie
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:35
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class UserCookie  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8084610790722110108L;
	private String DedeUserID;
	private String bili_jct;
	private String DedeUserID__ckMd5;
	private String sid;
	private String SESSDATA;
	
	
	public UserCookie() {
		super();
		// TODO 自动生成的构造函数存根
	}


	public UserCookie(String dedeUserID, String bili_jct, String dedeUserID__ckMd5, String sid, String sESSDATA) {
		super();
		DedeUserID = dedeUserID;
		this.bili_jct = bili_jct;
		DedeUserID__ckMd5 = dedeUserID__ckMd5;
		this.sid = sid;
		SESSDATA = sESSDATA;
	}



	public String getDedeUserID() {
		return DedeUserID;
	}


	public void setDedeUserID(String dedeUserID) {
		DedeUserID = dedeUserID;
	}


	public String getBili_jct() {
		return bili_jct;
	}


	public void setBili_jct(String bili_jct) {
		this.bili_jct = bili_jct;
	}


	public String getDedeUserID__ckMd5() {
		return DedeUserID__ckMd5;
	}


	public void setDedeUserID__ckMd5(String dedeUserID__ckMd5) {
		DedeUserID__ckMd5 = dedeUserID__ckMd5;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getSESSDATA() {
		return SESSDATA;
	}


	public void setSESSDATA(String sESSDATA) {
		SESSDATA = sESSDATA;
	}


	@Override
	public String toString() {
		return "Cookie [DedeUserID=" + DedeUserID + ", bili_jct=" + bili_jct + ", DedeUserID__ckMd5="
				+ DedeUserID__ckMd5 + ", sid=" + sid + ", SESSDATA=" + SESSDATA + "]";
	}
	
	
	
}
