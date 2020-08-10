package xyz.acproject.danmuji.entity.danmu_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Guard
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:31
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Guard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2253756094203399238L;
	//用户uid
	private Long uid;
	//用户名称
	private String username;
	//舰队类型 1总督 2提督 3舰长
	private Short guard_level;
	//数量
	private Integer num;
	//价格
	private Integer price;
	//角色名称？？？？ 直译 随机数吧
	private Long role_name;
	//礼物名称
	private String gift_name;
	//开始时间
	private Long start_time;
	//结束时间
	private Long end_time;
	public Guard() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Guard(Long uid, String username, Short guard_level, Integer num, Integer price, Long role_name,
			String gift_name, Long start_time, Long end_time) {
		super();
		this.uid = uid;
		this.username = username;
		this.guard_level = guard_level;
		this.num = num;
		this.price = price;
		this.role_name = role_name;
		this.gift_name = gift_name;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Short getGuard_level() {
		return guard_level;
	}
	public void setGuard_level(Short guard_level) {
		this.guard_level = guard_level;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Long getRole_name() {
		return role_name;
	}
	public void setRole_name(Long role_name) {
		this.role_name = role_name;
	}
	public String getGift_name() {
		return gift_name;
	}
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
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
	@Override
	public String toString() {
		return "Guard [uid=" + uid + ", username=" + username + ", guard_level=" + guard_level + ", num=" + num
				+ ", price=" + price + ", role_name=" + role_name + ", gift_name=" + gift_name + ", start_time="
				+ start_time + ", end_time=" + end_time + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new Guard(uid,username,guard_level,num,price,role_name,gift_name,start_time,end_time));
	}
}
