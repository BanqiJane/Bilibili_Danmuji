package xyz.acproject.danmuji.entity.Welcome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName WelcomeVip
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:48
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
