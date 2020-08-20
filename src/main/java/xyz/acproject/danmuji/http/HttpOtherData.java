package xyz.acproject.danmuji.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.utils.OkHttp3Utils;

/**
 * @ClassName HttpOtherData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:55
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpOtherData {
	private static Logger LOGGER = LogManager.getLogger(HttpUserData.class);
	public static String httpGetNewEdition() {
		String data = null;
		JSONObject jsonObject = null;
		String edition = null;
		String code="-1";
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		datas = new HashMap<>(4);
		datas.put("roomid", PublicDataConf.centerSetConf.getRoomid().toString());
		datas.put("edition", PublicDataConf.EDITION);
		datas.put("time", String.valueOf(System.currentTimeMillis()));
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("http://129.204.79.75/getEdition", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			edition="获取公告失败";
			LOGGER.error("请求服务器超时，获取最新版本失败");
			data = null;
		}
		if (data == null)
			return edition;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getString("code");
		if (code.equals("200")) {
			edition = ((JSONObject)jsonObject.get("result")).getString("value");
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return edition;
	}
	public static String httpGetNewAnnounce() {
		String data = null;
		JSONObject jsonObject = null;
		String announce = null;
		String code="-1";
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		datas = new HashMap<>(4);
		datas.put("roomid", PublicDataConf.centerSetConf.getRoomid().toString());
		datas.put("edition", PublicDataConf.EDITION);
		datas.put("time", String.valueOf(System.currentTimeMillis()));
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("http://129.204.79.75/getAnnounce", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			announce="获取最新公告失败";
			LOGGER.error("请求服务器超时，获取最新公告失败");
			data = null;
		}
		if (data == null)
			return announce;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getString("code");
		if (code.equals("200")) {
			announce = ((JSONObject)jsonObject.get("result")).getString("value");
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return announce;
	}
	public static String httpGetIp() {
		String data = null;
		JSONObject jsonObject = null;
		String status = null;
		String ip = null;
		Map<String, String> headers = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("http://ip-api.com/json/", headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			data = null;
		}
		if (data == null)
			return null;
		jsonObject = JSONObject.parseObject(data);
		try {
			status = jsonObject.getString("status");
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (status.equals("success")) {
			ip = jsonObject.getString("query");
		} else {
			LOGGER.error("获取ip失败" + jsonObject.toString());
		}
		return ip;
	}
}
