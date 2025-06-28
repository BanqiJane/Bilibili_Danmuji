package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @ClassName UserCookie
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:35
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
	private String buvid3;
	private String buvid4;
	private String bili_ticket;

	public UserCookie parse(String cookie){
		String[] cookies = cookie.split(";");
		for (String string : cookies) {
			String[] maps = string.split("=");
			switch (maps[0].trim()) {
			case "DedeUserID":
				this.DedeUserID = maps[1];
				break;
			case "bili_jct":
				this.bili_jct = maps[1];
				break;
			case "DedeUserID__ckMd5":
				this.DedeUserID__ckMd5 = maps[1];
				break;
			case "sid":
				this.sid = maps[1];
				break;
			case "SESSDATA":
				this.SESSDATA = maps[1];
				break;
			case "buvid3":
				this.buvid4 = maps[1];
				break;
			case "buvid4":
				this.buvid4 = maps[1];
				break;
			case "bili_ticket":
				this.bili_ticket = maps[1];
				break;
			}
		}
		return this;
	}

	public String getCookie() {
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(DedeUserID)) {
			sb.append("DedeUserID="+DedeUserID+"; ");
		}
		if(StringUtils.isNotBlank(bili_jct)) {
			sb.append("bili_jct="+bili_jct+"; ");
		}
		if(StringUtils.isNotBlank(DedeUserID__ckMd5)) {
			sb.append("DedeUserID__ckMd5="+DedeUserID__ckMd5+"; ");
		}
		if(StringUtils.isNotBlank(sid)) {
			sb.append("sid="+sid+"; ");
		}
		if(StringUtils.isNotBlank(SESSDATA)) {
			sb.append("SESSDATA="+SESSDATA+"; ");
		}
		if(StringUtils.isNotBlank(buvid3)) {
			sb.append("buvid3="+buvid3+"; ");
		}
		if(StringUtils.isNotBlank(buvid4)) {
			sb.append("buvid4="+buvid4+"; ");
		}
		if(StringUtils.isNotBlank(bili_ticket)) {
			sb.append("bili_ticket="+bili_ticket+"; ");
		}
		return sb.toString();
	}
	
}
