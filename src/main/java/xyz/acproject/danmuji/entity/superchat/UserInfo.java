package xyz.acproject.danmuji.entity.superchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:14
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4802153428241317283L;
	//用户名称
	private String uname;
	//用户头像
	private String face;
	//用户头像边框
	private String face_frame;
	//舰队类型0n 1总督 2提督 3舰长
	private Short guard_level;
	//用户等级
	private Short user_level;
	//等级背景颜色
	private String level_color;
	//老爷0n 1y
	private Short is_vip;
	//年费老爷0n 1y
	private Short is_svip;
	//是否为大会员
	private Short is_main_vip;
	//什么的标题啊  难道是称号？？？
	private String title;
	//房管0n 1y
	private Short manager;
	

}
