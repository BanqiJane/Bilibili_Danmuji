package xyz.acproject.danmuji.tools;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.danmu_data.Barrage;

/**
 * @ClassName ParseIndentityTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:31:19
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ParseIndentityTools {

	/**
	 * 判断舰长类型返回相应的字符串
	 * 
	 * @param type short型的数字 目前范围[0,3]
	 * @return 舰长类型字符串
	 */
	public static String parseGuard(Short type) {
		switch (type) {
		case 0:
			return "";
		case 1:
			return "[总督]";
		case 2:
			return "[提督]";
		case 3:
			return "[舰长]";
		default:
			return "";
		}
	}

	/**
	 * 判断是否为房管 1 yes  当 uid相同时即是主播
	 * 
	 * @param uid 用户uid用于 判断是否是主播
	 * @param type short类型数据 目前范围[0,1]
	 * @return 类型字符串
	 */
	public static String parseManager(Long uid, Short type) {
		if (type == 1) {
			return "[房管]";
		}
		if (uid.equals(PublicDataConf.AUID)) {
			return "[主播]";
		}
		return "";
	}

	/**
	 * 判断老爷类型 vip1 svip1 0 0
	 * 
	 * @param barrage 弹幕对象
	 * @return 类型字符串
	 */
	public static String parseVip(Barrage barrage) {
		if (barrage.getVip() == 1 || barrage.getSvip() == 1) {
			return "[爷]";
		} else {
			return "";
		}
	}

	/**
	 * 醒目留言时间++
	 * 
	 * @param time
	 * @return
	 */
	public static Integer parseTime(Integer time) {
		if (time != null && time.toString().endsWith("9")) {
			return time + 1;
		}
		return time;
	}
	
	/**
	 * 过滤金银瓜子类型
	 * @param coin_type
	 * @return
	 */
	public static short parseCoin_type(String coin_type) {
		if(StringUtils.isEmpty(coin_type)) {
			return -1;
		}
		switch(coin_type.trim()) {
		case "silver":
			return 0;
		case "gold":
			return 1;
		default:
			return -1;
		}
	}
}
