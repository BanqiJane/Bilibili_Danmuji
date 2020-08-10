package xyz.acproject.danmuji.entity.superchat;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName SuperChat
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:10
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
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
	public SuperChat() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public SuperChat(Long id, Long uid, Integer price, Integer rate, String message, Short trans_mark, Short is_ranked,
			String message_trans, String background_image, String background_color, String background_icon,
			String background_price_color, String background_bottom_color, Long ts, String token, MedalInfo medal_info,
			UserInfo user_info, Integer time, Long start_time, Long end_time, GiftChat gift) {
		super();
		this.id = id;
		this.uid = uid;
		this.price = price;
		this.rate = rate;
		this.message = message;
		this.trans_mark = trans_mark;
		this.is_ranked = is_ranked;
		this.message_trans = message_trans;
		this.background_image = background_image;
		this.background_color = background_color;
		this.background_icon = background_icon;
		this.background_price_color = background_price_color;
		this.background_bottom_color = background_bottom_color;
		this.ts = ts;
		this.token = token;
		this.medal_info = medal_info;
		this.user_info = user_info;
		this.time = time;
		this.start_time = start_time;
		this.end_time = end_time;
		this.gift = gift;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Short getTrans_mark() {
		return trans_mark;
	}
	public void setTrans_mark(Short trans_mark) {
		this.trans_mark = trans_mark;
	}
	public Short getIs_ranked() {
		return is_ranked;
	}
	public void setIs_ranked(Short is_ranked) {
		this.is_ranked = is_ranked;
	}
	public String getMessage_trans() {
		return message_trans;
	}
	public void setMessage_trans(String message_trans) {
		this.message_trans = message_trans;
	}
	public String getBackground_image() {
		return background_image;
	}
	public void setBackground_image(String background_image) {
		this.background_image = background_image;
	}
	public String getBackground_color() {
		return background_color;
	}
	public void setBackground_color(String background_color) {
		this.background_color = background_color;
	}
	public String getBackground_icon() {
		return background_icon;
	}
	public void setBackground_icon(String background_icon) {
		this.background_icon = background_icon;
	}
	public String getBackground_price_color() {
		return background_price_color;
	}
	public void setBackground_price_color(String background_price_color) {
		this.background_price_color = background_price_color;
	}
	public String getBackground_bottom_color() {
		return background_bottom_color;
	}
	public void setBackground_bottom_color(String background_bottom_color) {
		this.background_bottom_color = background_bottom_color;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public MedalInfo getMedal_info() {
		return medal_info;
	}
	public void setMedal_info(MedalInfo medal_info) {
		this.medal_info = medal_info;
	}
	public UserInfo getUser_info() {
		return user_info;
	}
	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Long getStart_time() {
		return start_time;
	}
	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}
	public Long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}
	public GiftChat getGift() {
		return gift;
	}
	public void setGift(GiftChat gift) {
		this.gift = gift;
	}
	@Override
	public String toString() {
		return "SuperChat [id=" + id + ", uid=" + uid + ", price=" + price + ", rate=" + rate + ", message=" + message
				+ ", trans_mark=" + trans_mark + ", is_ranked=" + is_ranked + ", message_trans=" + message_trans
				+ ", background_image=" + background_image + ", background_color=" + background_color
				+ ", background_icon=" + background_icon + ", background_price_color=" + background_price_color
				+ ", background_bottom_color=" + background_bottom_color + ", ts=" + ts + ", token=" + token
				+ ", medal_info=" + medal_info + ", user_info=" + user_info + ", time=" + time + ", start_time="
				+ start_time + ", end_time=" + end_time + ", gift=" + gift + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new SuperChat(id, uid, price, rate, message, trans_mark, is_ranked, message_trans, background_image, background_color, background_icon, background_price_color, background_bottom_color, ts, token, medal_info, user_info, time, start_time, end_time, gift));
	}
}
