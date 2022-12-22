package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	
	
}
