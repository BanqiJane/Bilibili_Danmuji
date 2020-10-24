package xyz.acproject.danmuji.entity.high_level_danmu;

import java.io.Serializable;

import xyz.acproject.danmuji.entity.danmu_data.Barrage;

/**
 * @ClassName Hbarrage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年9月4日 下午12:17:17
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Hbarrage implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static Hbarrage hbarrage = new Hbarrage();
	private static final long serialVersionUID = -699907643016533390L;
	// 用户uid() 位置info[2][0]
	private Long uid;
	// 用户名称() 位置info[2][1]
	private String uname;
	// 弹幕 ()位置info[1]
	private String msg;
	// 弹幕发送时间 ()位置info[0][4]
	private Long timestamp;
	// 是否为房管( 0否 1是)位置 info[2][2] 0也是可以是主播 注意
	private Short manager;
	// 是否为老爷 (0否 1是)位置 info[2][3]
	private Short vip;
	// 是否为年费老爷 (0否 1是)位置 info[2][4]
	private Short svip;
	// 勋章等级() 位置info[3][0] 或者[]
	private Short medal_level;
	// 勋章名称() 位置info[3][1] 或者[]
	private String medal_name;
	// 勋章归属主播()位置info[3][2] 或者[]
	private String medal_anchor;
	// 勋章归宿房间号()位置info[3][3] 或者[]
	private Long medal_room;
	// 用户等级位置info[4][0]
	private Short ulevel;
	// 用户本房间舰队身份(0非舰队，1总督，2提督，3舰长)位置info[7]
	private Short uguard;
	
	
	
	private Hbarrage() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public static Hbarrage getHbarrage() {
		try {
			return (Hbarrage) hbarrage.clone();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
//			e.printStackTrace();
		}
		return new Hbarrage();
	}

	public static Hbarrage getHbarrage(Long uid, String uname, String msg, Long timestamp, Short manager, Short vip, Short svip,
			Short medal_level, String medal_name, String medal_anchor, Long medal_room, Short ulevel, Short uguard) {
		try {
			Hbarrage h = (Hbarrage) hbarrage.clone();
			h.setUid(uid);
			h.setUname(uname);
			h.setMsg(msg);
			h.setTimestamp(timestamp);
			h.setManager(manager);
			h.setVip(svip);
			h.setSvip(svip);
			h.setMedal_level(medal_level);
			h.setMedal_name(medal_name);
			h.setMedal_anchor(medal_anchor);
			h.setMedal_room(medal_room);
			h.setUlevel(ulevel);
			h.setUguard(uguard);
			return h;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new Hbarrage();
	}
	 public static Hbarrage copyHbarrage(Barrage barrage) {
		 try {
				Hbarrage h = (Hbarrage) hbarrage.clone();
				h.setUid(barrage.getUid());
				h.setUname(barrage.getUname());
				h.setMsg(barrage.getMsg());
				h.setTimestamp(barrage.getTimestamp());
				h.setManager(barrage.getManager());
				h.setVip(barrage.getVip());
				h.setSvip(barrage.getSvip());
				h.setMedal_level(barrage.getMedal_level());
				h.setMedal_name(barrage.getMedal_name());
				h.setMedal_anchor(barrage.getMedal_anchor());
				h.setMedal_room(barrage.getMedal_room());
				h.setUlevel(barrage.getUlevel());
				h.setUguard(barrage.getUguard());
				return h;
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return new Hbarrage();
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Short getManager() {
		return manager;
	}
	public void setManager(Short manager) {
		this.manager = manager;
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
	public Short getMedal_level() {
		return medal_level;
	}
	public void setMedal_level(Short medal_level) {
		this.medal_level = medal_level;
	}
	public String getMedal_name() {
		return medal_name;
	}
	public void setMedal_name(String medal_name) {
		this.medal_name = medal_name;
	}
	public String getMedal_anchor() {
		return medal_anchor;
	}
	public void setMedal_anchor(String medal_anchor) {
		this.medal_anchor = medal_anchor;
	}
	public Long getMedal_room() {
		return medal_room;
	}
	public void setMedal_room(Long medal_room) {
		this.medal_room = medal_room;
	}
	public Short getUlevel() {
		return ulevel;
	}
	public void setUlevel(Short ulevel) {
		this.ulevel = ulevel;
	}
	public Short getUguard() {
		return uguard;
	}
	public void setUguard(Short uguard) {
		this.uguard = uguard;
	}

	
	
}
