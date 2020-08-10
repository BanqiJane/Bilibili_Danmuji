package xyz.acproject.danmuji.entity.room_data;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName RoomInit
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:13
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class RoomInit {
	private Long room_id;
	private Integer short_id;
	private Long uid;
	private Short need_p2p;
	private Boolean is_hidden;
	private Boolean is_portrait;
	//0为不直播  1为直播中 2为轮播
	private Short live_status;
	private Short hidden_till;
	private Short lock_till;
	private Boolean encrypted;
	private Boolean pwd_verified;
	private Long live_time;
	private Short room_shield;
	private Short is_sp;
	private Short special_type;
	public RoomInit() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public RoomInit(Long room_id, Integer short_id, Long uid, Short need_p2p, Boolean is_hidden, Boolean is_portrait,
			Short live_status, Short hidden_till, Short lock_till, Boolean encrypted, Boolean pwd_verified,
			Long live_time, Short room_shield, Short is_sp, Short special_type) {
		super();
		this.room_id = room_id;
		this.short_id = short_id;
		this.uid = uid;
		this.need_p2p = need_p2p;
		this.is_hidden = is_hidden;
		this.is_portrait = is_portrait;
		this.live_status = live_status;
		this.hidden_till = hidden_till;
		this.lock_till = lock_till;
		this.encrypted = encrypted;
		this.pwd_verified = pwd_verified;
		this.live_time = live_time;
		this.room_shield = room_shield;
		this.is_sp = is_sp;
		this.special_type = special_type;
	}
	public Long getRoom_id() {
		return room_id;
	}
	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}
	public Integer getShort_id() {
		return short_id;
	}
	public void setShort_id(Integer short_id) {
		this.short_id = short_id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Short getNeed_p2p() {
		return need_p2p;
	}
	public void setNeed_p2p(Short need_p2p) {
		this.need_p2p = need_p2p;
	}
	public Boolean getIs_hidden() {
		return is_hidden;
	}
	public void setIs_hidden(Boolean is_hidden) {
		this.is_hidden = is_hidden;
	}
	public Boolean getIs_portrait() {
		return is_portrait;
	}
	public void setIs_portrait(Boolean is_portrait) {
		this.is_portrait = is_portrait;
	}
	public Short getLive_status() {
		return live_status;
	}
	public void setLive_status(Short live_status) {
		this.live_status = live_status;
	}
	public Short getHidden_till() {
		return hidden_till;
	}
	public void setHidden_till(Short hidden_till) {
		this.hidden_till = hidden_till;
	}
	public Short getLock_till() {
		return lock_till;
	}
	public void setLock_till(Short lock_till) {
		this.lock_till = lock_till;
	}
	public Boolean getEncrypted() {
		return encrypted;
	}
	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}
	public Boolean getPwd_verified() {
		return pwd_verified;
	}
	public void setPwd_verified(Boolean pwd_verified) {
		this.pwd_verified = pwd_verified;
	}
	public Long getLive_time() {
		return live_time;
	}
	public void setLive_time(Long live_time) {
		this.live_time = live_time;
	}
	public Short getRoom_shield() {
		return room_shield;
	}
	public void setRoom_shield(Short room_shield) {
		this.room_shield = room_shield;
	}
	public Short getIs_sp() {
		return is_sp;
	}
	public void setIs_sp(Short is_sp) {
		this.is_sp = is_sp;
	}
	public Short getSpecial_type() {
		return special_type;
	}
	public void setSpecial_type(Short special_type) {
		this.special_type = special_type;
	}
	@Override
	public String toString() {
		return "RoomInit [room_id=" + room_id + ", short_id=" + short_id + ", uid=" + uid + ", need_p2p=" + need_p2p
				+ ", is_hidden=" + is_hidden + ", is_portrait=" + is_portrait + ", live_status=" + live_status
				+ ", hidden_till=" + hidden_till + ", lock_till=" + lock_till + ", encrypted=" + encrypted
				+ ", pwd_verified=" + pwd_verified + ", live_time=" + live_time + ", room_shield=" + room_shield
				+ ", is_sp=" + is_sp + ", special_type=" + special_type + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new RoomInit(room_id, short_id, uid, need_p2p, is_hidden, is_portrait, live_status, hidden_till, lock_till, encrypted, pwd_verified, live_time, room_shield, is_sp, special_type));
	}
	
}
