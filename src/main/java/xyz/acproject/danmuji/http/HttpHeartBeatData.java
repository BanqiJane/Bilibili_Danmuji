package xyz.acproject.danmuji.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.utils.OkHttp3Utils;

/**
 * @ClassName HttpHeartBeatData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:52
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpHeartBeatData {
	private static Logger LOGGER = LogManager.getLogger(HttpHeartBeatData.class);

	public static void httpGetHeartBeatOrS(Long timestamp) {
		String data = null;
		JSONObject jsonObject = null;
		String urlString = null;
		if (timestamp != null) {
			urlString = "https://api.live.bilibili.com/relation/v1/Feed/heartBeat?_=" + timestamp;
		} else {
			urlString = "https://api.live.bilibili.com/relation/v1/Feed/heartBeat";
		}
		Map<String, String> headers = null;
		headers = new HashMap<>(4);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.put("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			headers.put("cookie", PublicDataConf.USERCOOKIE);
		}
		try {
			data = OkHttp3Utils.getHttp3Utils().httpGet(urlString, headers, null).body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return;

		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			if (jsonObject.getString("msg").equals("success")) {
//				LOGGER.debug("在线心跳包get发送成功"+jsonObject.getString("data"));
			} else {
				LOGGER.error("在线心跳包get发送失败" + jsonObject.toString());
			}
		} else {
			LOGGER.error("在线心跳包get发送失败" + jsonObject.toString());
		}
	}

	public static void httpPostUserOnlineHeartBeat() {
		JSONObject jsonObject = null;
		String data = null;
		Map<String, String> headers = null;
		Map<String, String> params = null;
		if (PublicDataConf.USERBARRAGEMESSAGE == null && PublicDataConf.COOKIE == null)
			return;
		headers = new HashMap<>(4);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		headers.put("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			headers.put("cookie", PublicDataConf.USERCOOKIE);
		}
		params = new HashMap<>(4);
		params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
		params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
		params.put("visit_id", "");
		try {
			data = OkHttp3Utils.getHttp3Utils().httpPostForm("https://api.live.bilibili.com/User/userOnlineHeart", headers, params)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return;
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			if (jsonObject.getString("message").equals("0")) {
//				LOGGER.debug("心跳包post发送成功" + jsonObject.getString("data"));
			} else {
				LOGGER.error("心跳包post发送失败,未知错误,原因未知v" + jsonObject.toString());
			}
		} else {
			LOGGER.error("发跳包post发送失败,未知错误,原因未知" + jsonObject.toString());
		}
	}
}
