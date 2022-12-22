package xyz.acproject.danmuji.entity.superchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName SuperChat
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:10
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuperChat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -726210364306930621L;
	//superchatid
	private Long id;
	//用户uid
	private Long uid;
	//价格
	private Integer price;
	//未知
	private Integer rate;
	//消息体
	private String message;
	//不知道什么
	private Short trans_mark;
	//是不是排行榜
	private Short is_ranked;
	//消息。。。什么玩意
	private String message_trans;
	//背景图片
	private String background_image;
	//背景颜色
	private String background_color;
	//背景图标
	private String background_icon;
	//价格颜色背景
	private String background_price_color;
	//中奖背景颜色
	private String background_bottom_color;
	//发送时间
	private Long ts;
	//不知道什么
	private String token;	
	//勋章体
	private MedalInfo medal_info;
	//用户信息
	private UserInfo user_info;
	//持续时间
	private Integer time;
	//开始时间
	private Long start_time;
	//结束时间
	private Long end_time;
	//相关礼物信息
	private GiftChat gift;

}
