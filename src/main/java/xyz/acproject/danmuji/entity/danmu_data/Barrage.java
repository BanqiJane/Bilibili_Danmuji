package xyz.acproject.danmuji.entity.danmu_data;

import lombok.Data;

import java.io.Serializable;

/**
 * 弹幕信息
 * 
 * @author zjian
 *
 */
/**
 * @ClassName Barrage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:08
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
public class Barrage implements Serializable,Cloneable {
	
	/**
	 * 
	 */
	private static Barrage barrage  =new Barrage();
	private static final long serialVersionUID = 434878878226926991L;
	//用户uid() 位置info[2][0]
	private Long uid;  
	//用户名称() 位置info[2][1]
	private String uname;
	// 弹幕 ()位置info[1]
	private String msg;
	//是否为礼物弹幕(现在推测0不是 2为礼物) 位置 info[0][9]
	//已知 0普通弹幕  2礼物弹幕
	private Short msg_type;
	//表情弹幕( 0否 1是)位置 info[0][12]
	private Short msg_emoticon;
	//弹幕发送时间 ()位置info[0][4]
	private Long timestamp;
	//是否为房管( 0否 1是)位置 info[2][2] 0也是可以是主播 注意
	private Short manager;
	//是否为老爷 (0否 1是)位置 info[2][3]
	private Short vip;
	//是否为年费老爷 (0否 1是)位置 info[2][4]
	private Short svip; 
	//是否为非正式会员或正式会员 (推测5000非 10000正)位置 info[2][5]
	private Integer uidentity; 
	//是否绑定手机(0否 1是)位置 info[2][6]
	private Short iphone;
	//勋章等级() 位置info[3][0]  或者[]
	private Short medal_level;
	//勋章名称() 位置info[3][1]   或者[]
	private String medal_name;
	//勋章归属主播()位置info[3][2]  或者[]
	private String medal_anchor;
	//勋章归宿房间号()位置info[3][3]   或者[]
	private Long medal_room;
	//用户等级位置info[4][0]
	private Short ulevel;
	//用户等级排名info[4][3]
	private String ulevel_rank;
	//老头衔位置info[5][0]
	private String old_title;
	//新头衔 位置info[5][1]
	private String title;
	//用户本房间舰队身份(0非舰队，1总督，2提督，3舰长)位置info[7]
	private Short uguard;
	//表情弹幕唯一标识(赞->"official_147")位置 info[0][13].emoticon_unique
	private String msg_emoticon_name;
	//表情弹幕的图片地址 () 位置 info[0][13].url
	private String msg_emoticon_url;

	
	public Barrage() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public static Barrage getBarrage() {
		try {
			return (Barrage)barrage.clone();
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new Barrage();
	}
    public static Barrage getBarrage(Long uid, String uname, String msg, Short msg_type,Short msg_emoticon, Long timestamp, Short manager, Short vip,
			Short svip, Integer uidentity, Short iphone, Short medal_level, String medal_name, String medal_anchor,
			Long medal_room, Short ulevel, String ulevel_rank, String old_title, String title, Short uguard,String msg_emoticon_name,String msg_emoticon_url) {
    	try {
			Barrage b= (Barrage)barrage.clone();
			b.uid = uid;
			b.uname = uname;
			b.msg = msg;
			b.msg_type = msg_type;
			b.msg_emoticon = msg_emoticon;
			b.timestamp = timestamp;
			b.manager = manager;
			b.vip = vip;
			b.svip = svip;
			b.uidentity = uidentity;
			b.iphone = iphone;
			b.medal_level = medal_level;
			b.medal_name = medal_name;
			b.medal_anchor = medal_anchor;
			b.medal_room = medal_room;
			b.ulevel = ulevel;
			b.ulevel_rank = ulevel_rank;
			b.old_title = old_title;
			b.title = title;
			b.uguard = uguard;
			b.msg_emoticon_name = msg_emoticon_name;
			b.msg_emoticon_url = msg_emoticon_url;
			return b;
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    	return new Barrage();
    }
}
