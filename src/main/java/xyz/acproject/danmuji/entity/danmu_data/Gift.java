package xyz.acproject.danmuji.entity.danmu_data;

import lombok.Data;
import xyz.acproject.danmuji.entity.superchat.MedalInfo;

import java.io.Serializable;

/**
 * @ClassName Gift
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:26
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
public class Gift implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5959529631245949132L;
	/*
	 * 经典辣条json格式
	 * {"cmd":"SEND_GIFT","data":{"giftName":"辣条","num":22,"uname":"热心市民王yy","face":
	 * "http:\/\/i1.hdslb.com\/bfs\/face\/975463ebd1e9575970964dc04ca234aa636a70c4.jpg"
	 * ,"guard_level":0,"rcost":628504329,"uid":145943674,"top_list":[],"timestamp":
	 * 1589641574,"giftId":1,"giftType":0,"action":"喂食","super":0,"super_gift_num":0
	 * ,"super_batch_gift_num":0,"batch_combo_id":"","price":100,"rnd":
	 * "871C9560-1174-4EF6-A09E-814E68124DB5","newMedal":0,"newTitle":0,"medal":[],
	 * "title":"","beatId":"","biz_source":"live","metadata":"","remain":0,"gold":0,
	 * "silver":0,"eventScore":0,"eventNum":0,"smalltv_msg":[],"specialGift":null,
	 * "notice_msg":[],"smallTVCountFlag":true,"capsule":null,"addFollow":0,
	 * "effect_block":1,"coin_type":"silver","total_coin":2200,"effect":0,
	 * "broadcast_id":0,"draw":0,"crit_prob":0,"tag_image":"","send_master":null,
	 * "is_first":true,"demarcation":2,"combo_stay_time":3,"combo_total_coin":0,
	 * "tid":"1589641574132400004"}}
	 */
	private static Gift gift = new Gift();
	// 礼物id
	private Integer giftId;
	// 礼物类型(未知)
	private Short giftType;
	// 礼物名称
	private String giftName;
	// 赠送礼物数量
	private Integer num;
	// 赠送人
	private String uname;
	// 赠送人头像
	private String face;
	// 赠送人舰长 等级 0 1总督 2提督 3舰长
	private Short guard_level;
	// 赠送人uid
	private Long uid;
	// 赠送时间
	private Long timestamp;
	// 赠送类型
	private String action;
	// 单价
	private Integer price;
	// 瓜子类型 silver银:gold金//经工具过滤 0为银 1为金
	private Short coin_type;
	// 总价格
	private Long total_coin;

	private MedalInfo medal_info;

	public Gift() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public static Gift getGift() {
		try {
			return (Gift) gift.clone();
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new Gift();
	}

	public static Gift getGift(Integer giftId, Short giftType, String giftName, Integer num, String uname, String face,
			Short guard_level, Long uid, Long timestamp, String action, Integer price, Short coin_type,
			Long total_coin,MedalInfo medal_info) {
		try {
			Gift g = (Gift) gift.clone();
			g.giftId = giftId;
			g.giftType = giftType;
			g.giftName = giftName;
			g.num = num;
			g.uname = uname;
			g.face = face;
			g.guard_level = guard_level;
			g.uid = uid;
			g.timestamp = timestamp;
			g.action = action;
			g.price = price;
			g.coin_type = coin_type;
			g.total_coin = total_coin;
			g.medal_info = medal_info;
			return g;
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new Gift();
	}


}
