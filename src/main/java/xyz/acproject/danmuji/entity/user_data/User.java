package xyz.acproject.danmuji.entity.user_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName User
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:39
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4638128918041411710L;
	private Long uid;
	private String uname;
	private String face;
	private Long silver;
	private Integer gold;
	private Integer achieve;
	private Short vip;
	private Short svip;
	private Short user_level;
	private Short user_next_level;
	private Long user_Integerimacy;
	private Long user_next_Integerimacy;
	private String user_level_rank;
	private Short user_charged;
	private Integer billCoin;
	public User() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public User(Long uid, String uname, String face, Long silver, Integer gold, Integer achieve, Short vip, Short svip,
			Short user_level, Short user_next_level, Long user_Integerimacy, Long user_next_Integerimacy,
			String user_level_rank, Short user_charged, Integer billCoin) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.face = face;
		this.silver = silver;
		this.gold = gold;
		this.achieve = achieve;
		this.vip = vip;
		this.svip = svip;
		this.user_level = user_level;
		this.user_next_level = user_next_level;
		this.user_Integerimacy = user_Integerimacy;
		this.user_next_Integerimacy = user_next_Integerimacy;
		this.user_level_rank = user_level_rank;
		this.user_charged = user_charged;
		this.billCoin = billCoin;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public Long getSilver() {
		return silver;
	}
	public void setSilver(Long silver) {
		this.silver = silver;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public Integer getAchieve() {
		return achieve;
	}
	public void setAchieve(Integer achieve) {
		this.achieve = achieve;
	}
	public Short getVip() {
		return vip;
	}
	public void setVip(Short vip) {
		this.vip = vip;
	}
	public Short getSvip() {
		return svip;
	}
	public void setSvip(Short svip) {
		this.svip = svip;
	}
	public Short getUser_level() {
		return user_level;
	}
	public void setUser_level(Short user_level) {
		this.user_level = user_level;
	}
	public Short getUser_next_level() {
		return user_next_level;
	}
	public void setUser_next_level(Short user_next_level) {
		this.user_next_level = user_next_level;
	}
	public Long getUser_Integerimacy() {
		return user_Integerimacy;
	}
	public void setUser_Integerimacy(Long user_Integerimacy) {
		this.user_Integerimacy = user_Integerimacy;
	}
	public Long getUser_next_Integerimacy() {
		return user_next_Integerimacy;
	}
	public void setUser_next_Integerimacy(Long user_next_Integerimacy) {
		this.user_next_Integerimacy = user_next_Integerimacy;
	}
	public String getUser_level_rank() {
		return user_level_rank;
	}
	public void setUser_level_rank(String user_level_rank) {
		this.user_level_rank = user_level_rank;
	}
	public Short getUser_charged() {
		return user_charged;
	}
	public void setUser_charged(Short user_charged) {
		this.user_charged = user_charged;
	}
	public Integer getBillCoin() {
		return billCoin;
	}
	public void setBillCoin(Integer billCoin) {
		this.billCoin = billCoin;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", face=" + face + ", silver=" + silver + ", gold=" + gold
				+ ", achieve=" + achieve + ", vip=" + vip + ", svip=" + svip + ", user_level=" + user_level
				+ ", user_next_level=" + user_next_level + ", user_Integerimacy=" + user_Integerimacy
				+ ", user_next_Integerimacy=" + user_next_Integerimacy + ", user_level_rank=" + user_level_rank
				+ ", user_charged=" + user_charged + ", billCoin=" + billCoin + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new User(uid, uname, face, silver, gold, achieve, vip, svip, user_level, user_next_level, user_Integerimacy, user_next_Integerimacy, user_level_rank, user_charged, billCoin));
	}
	
	
}
