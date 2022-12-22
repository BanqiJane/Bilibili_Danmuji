package xyz.acproject.danmuji.entity.login_data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Qrcode
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:58
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qrcode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8700211079867769292L;
	private String url;
	private String oauthKey;

	
}
