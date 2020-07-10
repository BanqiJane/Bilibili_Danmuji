package xyz.acproject.danmuji.tools;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.danmu_data.Barrage;

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

	public static Integer parseTime(Integer time) {
		if (time != null && time.toString().endsWith("9")) {
			return time + 1;
		}
		return time;
	}
}
