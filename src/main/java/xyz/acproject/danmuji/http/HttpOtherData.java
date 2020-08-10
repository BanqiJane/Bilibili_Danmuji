package xyz.acproject.danmuji.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;

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
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		String edition = null;
		try {
			String urlString = "http://129.204.79.75/getEdition?roomid="+PublicDataConf.centerSetConf.getRoomid()+"&edition="+PublicDataConf.EDITION+"&time="+System.currentTimeMillis();
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setConnectTimeout(4000);
			httpURLConnection.setReadTimeout(4000);
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
//			e.printStackTrace();
			edition="获取公告失败";
			LOGGER.error("请求服务器超时，获取最新版本失败");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		if(StringUtils.isEmpty(data)) {
			return edition;
		}
		jsonObject = JSONObject.parseObject(data);
		String code = jsonObject.getString("code");
		if (code.equals("200")) {
			edition = ((JSONObject)jsonObject.get("result")).getString("value");
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return edition;
	}
	public static String httpGetNewAnnounce() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		String announce = null;
		try {
			String urlString = "http://129.204.79.75/getAnnounce?roomid="+PublicDataConf.centerSetConf.getRoomid()+"&edition="+PublicDataConf.EDITION+"&time="+System.currentTimeMillis();
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setConnectTimeout(4000);
			httpURLConnection.setReadTimeout(4000);
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
//			e.printStackTrace();
			LOGGER.error("请求服务器超时，获取最新公告失败");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		if(StringUtils.isEmpty(data)) {
			return announce;
		}
		jsonObject = JSONObject.parseObject(data);
		String code = jsonObject.getString("code");
		if (code.equals("200")) {
			announce = ((JSONObject)jsonObject.get("result")).getString("value");
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return announce;
	}
}
