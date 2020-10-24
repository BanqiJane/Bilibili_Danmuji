package xyz.acproject.danmuji.http;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.room_data.CheckTx;
import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.entity.room_data.RoomInfo;
import xyz.acproject.danmuji.entity.room_data.RoomInit;
import xyz.acproject.danmuji.entity.server_data.Conf;
import xyz.acproject.danmuji.entity.view.RoomGift;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.ParseIndentityTools;
import xyz.acproject.danmuji.utils.OkHttp3Utils;

/**
 * @ClassName HttpRoomData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:59
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpRoomData {
	private static Logger LOGGER = LogManager.getLogger(HttpRoomData.class);

	/**
	 * 获取连接目标房间websocket端口 接口
	 * 
	 * @param roomid
	 * @return
	 */
	public static Conf httpGetConf() {
		String data = null;
		JSONObject jsonObject = null;
		Conf conf = null;
		short code = -1;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		headers = new HashMap<>(3);
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			headers.put("cookie", PublicDataConf.USERCOOKIE);
		}
		datas = new HashMap<>(3);
		datas.put("id", PublicDataConf.ROOMID.toString());
		datas.put("type", "0");
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return null;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			conf = jsonObject.getObject("data", Conf.class);
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("message"));
		}
		return conf;
	}

	/**
	 * 获取目标房间部分信息
	 * 
	 * @param roomid
	 * @return
	 */
	public static Room httpGetRoomData(long roomid) {
		String data = null;
		JSONObject jsonObject = null;
		Room room = null;
		short code = -1;
		Map<String, String> headers = null;
		headers = new HashMap<>(3);
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//			headers.put("cookie", PublicDataConf.USERCOOKIE);
//		}
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/room_ex/v1/RoomNews/get?roomid=" + roomid, headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return room;
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			room = jsonObject.getObject("data", Room.class);
		} else {
			LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
		}
		return room;
	}

	/**
	 * 获取房间信息
	 * 
	 * @param roomid
	 */
	public static RoomInit httpGetRoomInit(long roomid) {
		String data = null;
		RoomInit roomInit = null;
		JSONObject jsonObject = null;
		short code = -1;
		Map<String, String> headers = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//			headers.put("cookie", PublicDataConf.USERCOOKIE);
//		}
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/room/v1/Room/room_init?id=" + roomid, headers, null).body()
					.string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return roomInit;
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			roomInit = jsonObject.getObject("data", RoomInit.class);
		} else {
			LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
		}
		;
		return roomInit;
	}

	/**
	 * 获取房间最详细信息 日后扩容 目前只是获取主播uid 改
	 * 
	 * @return
	 */
	public static RoomInfo httpGetRoomInfo() {
		String data = null;
		JSONObject jsonObject = null;
		RoomInfo roomInfo = null;
		short code = -1;
		Map<String, String> headers = null;
		headers = new HashMap<>(3);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//			headers.put("cookie", PublicDataConf.USERCOOKIE);
//		}
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id="
							+ CurrencyTools.parseRoomId(), headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return roomInfo;
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			roomInfo = JSON.parseObject(((JSONObject) jsonObject.get("data")).getString("room_info"),
					RoomInfo.class);
		} else {
			LOGGER.error("获取房间详细信息失败，请稍后尝试:" + jsonObject.getString("message"));
		}
		return roomInfo;
	}

	/**
	 * 获取关注名字集合
	 * 
	 * @return 关注uname集
	 */
	public static ConcurrentHashMap<Long, String> httpGetFollowers() {
		String data = null;
		JSONObject jsonObject = null;
		Integer page = null;
		JSONArray jsonArray = null;
		short code = -1;
		ConcurrentHashMap<Long, String> followConcurrentHashMap = null;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		if (PublicDataConf.AUID == null) {
			return null;
		}
		if (PublicDataConf.FANSNUM.equals(null) || PublicDataConf.FANSNUM.equals(0L)) {
			page = 1;
		} else {
			page = (int) Math.ceil((float) PublicDataConf.FANSNUM / 20F);
			page = page > 5 ? 5 : page;
		}
		followConcurrentHashMap = new ConcurrentHashMap<Long, String>();
		while (page > 0) {
			headers = new HashMap<>(3);
			headers.put("referer", "https://space.bilibili.com/{" + PublicDataConf.AUID + "}/");
			headers.put("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//				headers.put("cookie", PublicDataConf.USERCOOKIE);
//			}
			datas = new HashMap<>(6);
			datas.put("vmid", PublicDataConf.AUID.toString());
			datas.put("pn", String.valueOf(page));
			datas.put("ps", "50");
			datas.put("order", "desc");
			datas.put("jsonp", "jsonp");
			try {
				data = OkHttp3Utils.getHttp3Utils()
						.httpGet("https://api.bilibili.com/x/relation/followers", headers, datas).body().string();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				LOGGER.error(e);
				data = null;
			}
			if (data == null)
				return null;
			jsonObject = JSONObject.parseObject(data);
			try {
				code = jsonObject.getShort("code");
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("获取关注错误");
				return followConcurrentHashMap;
			}

			if (code == 0) {
				PublicDataConf.FANSNUM = ((JSONObject) jsonObject.get("data")).getLong("total");
				jsonArray = ((JSONObject) jsonObject.get("data")).getJSONArray("list");
				for (Object object : jsonArray) {
					followConcurrentHashMap.put(((JSONObject) object).getLong("mid"),
							((JSONObject) object).getString("uname"));
				}
			} else {
				LOGGER.error("获取关注数失败，请重试" + jsonObject.getString("message"));
			}
			page--;
		}
		return followConcurrentHashMap;
	}

	/**
	 * 获取关注数
	 * 
	 * @return 返回关注数
	 */
	public static Long httpGetFollowersNum() {
		String data = null;
		JSONObject jsonObject = null;
		short code = -1;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		Long followersNum = 0L;
		if (PublicDataConf.AUID == null) {
			return followersNum;
		}
		headers = new HashMap<>(3);
		headers.put("referer", "https://space.bilibili.com/{" + PublicDataConf.AUID + "}/");
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//				headers.put("cookie", PublicDataConf.USERCOOKIE);
//			}
		datas = new HashMap<>(6);
		datas.put("vmid", PublicDataConf.AUID.toString());
		datas.put("pn", String.valueOf(1));
		datas.put("ps", "50");
		datas.put("order", "desc");
		datas.put("jsonp", "jsonp");
		try {
			data = OkHttp3Utils.getHttp3Utils().httpGet("https://api.bilibili.com/x/relation/followers", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return followersNum;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			followersNum = ((JSONObject) jsonObject.get("data")).getLong("total");
		} else {
			LOGGER.error("获取关注数失败，请重试" + jsonObject.getString("message"));
		}
		return followersNum;
	}

	public static Hashtable<Long, String> httpGetGuardList() {
		String data = null;
		Hashtable<Long, String> hashtable = new Hashtable<Long, String>();
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		short code = -1;
		int totalSize = httpGetGuardListTotalSize();
		int page = 0;
		if (totalSize == 0) {
			return null;
		}
		page = (int) Math.ceil((float) totalSize / 29F);
		if (page == 0) {
			page = 1;
		}
		for (int i = 1; i <= page; i++) {
			headers = new HashMap<>(3);
			headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
			headers.put("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//				headers.put("cookie", PublicDataConf.USERCOOKIE);
//			}
			datas = new HashMap<>(4);
			datas.put("roomid", PublicDataConf.ROOMID.toString());
			datas.put("page", String.valueOf(i));
			datas.put("ruid", PublicDataConf.AUID.toString());
			datas.put("page_size", "29");
			try {
				data = OkHttp3Utils.getHttp3Utils()
						.httpGet("https://api.live.bilibili.com/xlive/app-room/v1/guardTab/topList", headers, datas)
						.body().string();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				LOGGER.error(e);
				data = null;
			}
			if (data == null)
				return null;
			jsonObject = JSONObject.parseObject(data);
			code = jsonObject.getShort("code");
			if (code == 0) {
				jsonArray = ((JSONObject) jsonObject.get("data")).getJSONArray("list");
				for (Object object : jsonArray) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					hashtable.put(((JSONObject) object).getLong("uid"), ((JSONObject) object).getString("username"));
				}
				if (i == 1) {
					jsonArray = ((JSONObject) jsonObject.get("data")).getJSONArray("top3");
					for (Object object : jsonArray) {
						hashtable.put(((JSONObject) object).getLong("uid"),
								((JSONObject) object).getString("username"));
					}
				}
			} else {
				LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
			}
		}
		return hashtable;
	}

	public static int httpGetGuardListTotalSize() {
		String data = null;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		int num = 0;
		JSONObject jsonObject = null;
		short code = -1;
		headers = new HashMap<>(3);
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//			headers.put("cookie", PublicDataConf.USERCOOKIE);
//		}
		datas = new HashMap<>(5);
		datas.put("roomid", PublicDataConf.ROOMID.toString());
		datas.put("page", String.valueOf(1));
		datas.put("ruid", PublicDataConf.AUID.toString());
		datas.put("page_size", "29");
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/xlive/app-room/v1/guardTab/topList", headers, datas).body()
					.string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return num;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			num = ((JSONObject) ((JSONObject) jsonObject.get("data")).get("info")).getInteger("num");
		} else {
			LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
		}
		return num;
	}

	public static CheckTx httpGetCheckTX() {
		String data = null;
		JSONObject jsonObject = null;
		short code = -1;
		Map<String, String> headers = null;
		headers = new HashMap<>(3);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//			headers.put("cookie", PublicDataConf.USERCOOKIE);
//		}
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/xlive/lottery-interface/v1/Anchor/Check?roomid="
							+ CurrencyTools.parseRoomId(), headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return null;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			if (jsonObject.get("data") != null) {
				return new CheckTx(((JSONObject) jsonObject.get("data")).getLong("room_id"),
						((JSONObject) jsonObject.get("data")).getString("gift_name"),
						((JSONObject) jsonObject.get("data")).getShort("time"));
			}
		} else {
			LOGGER.error("检查天选礼物失败,原因:" + jsonObject.getString("message"));
		}
		return null;
	}

	public static void httpGetRoomGifts() {
		String data = null;
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		short code = -1;
		Map<String, String> headers = null;
		headers = new HashMap<>(3);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("https://api.live.bilibili.com/xlive/web-room/v1/giftPanel/giftConfig?platform=pc&room_id="
							+ CurrencyTools.parseRoomId(), headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getShort("code");
		if (code == 0) {
			jsonArray = ((JSONObject) jsonObject.get("data")).getJSONArray("list");
			if (PublicDataConf.roomGiftConcurrentHashMap.size() < 1) {
				for (Object object : jsonArray) {
					PublicDataConf.roomGiftConcurrentHashMap.put(((JSONObject) object).getInteger("id"),
							new RoomGift(((JSONObject) object).getInteger("id"), ((JSONObject) object).getString("name"), ParseIndentityTools.parseCoin_type(((JSONObject) object).getString("coin_type")), ((JSONObject) object).getString("webp")));
				}
			}
		} else {
			LOGGER.error("获取礼物失败,原因:" + jsonObject.getString("message"));
		}
	}
}
