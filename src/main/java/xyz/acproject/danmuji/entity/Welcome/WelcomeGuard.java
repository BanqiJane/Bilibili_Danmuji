package xyz.acproject.danmuji.entity.Welcome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.utils.FastJsonUtils;

import java.io.Serializable;

/**
 * @ClassName WelcomeGuard
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:44
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
