package xyz.acproject.danmuji.entity.login_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName LoginData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:53
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1326824790653747543L;
	private String oauthKey;
	private String gourl = "https://www.bilibili.com/";
}
