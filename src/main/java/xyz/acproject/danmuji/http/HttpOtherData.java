package xyz.acproject.danmuji.http;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.heart.XData;
import xyz.acproject.danmuji.entity.other.Weather;
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

	public static String httpPostEncsUrl() {
		String data = null;
		JSONObject jsonObject = null;
		String url = null;
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
					.httpPostForm("http://129.204.79.75/getEncsServer", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			url=null;
			LOGGER.error("请求服务器超时，获取服务器链接失败");
			data = null;
		}
		if (data == null)
			return url;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getString("code");
		if (code.equals("200")) {
			url = jsonObject.getString("result");
			PublicDataConf.SMALLHEART_ADRESS = url;
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return url;
	}
	/**
	 * 加密s函数方法来自 https://github.com/lkeme/bilibili-pcheartbeat
	 * 服务器来自 https://github.com/lkeme/BiliHelper-personal
	 * 
	 * @param xData
	 * @param ts
	 * @return
	 */
	public static String httpPostencS(XData xData,long ts) {
		String data = null;
		JSONObject jsonObject = null;
		String s= null;
		String url  = PublicDataConf.SMALLHEART_ADRESS;
		if(StringUtils.isEmpty(url)){
			return null;
		}
		Map<String, String> headers = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		JSONObject t = new JSONObject();
		t.put("id", xData.getId());
		t.put("device", xData.getDevice());
		t.put("ets", xData.getEts());
		t.put("benchmark", xData.getBenchmark());
		t.put("time", xData.getTime());
		t.put("ts", ts);
		t.put("ua", xData.getUa());
		JSONObject json = new JSONObject();
		json.put("t", t);
		json.put("r", xData.getSecret_rule());
		if(StringUtils.isEmpty(url)){
			return null;
		}
		try {
			data=OkHttp3Utils.getHttp3Utils().httpPostJson(url, headers, json.toJSONString()).body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error("连接至加密服务器错误？不存在");
			data = null;
//			e.printStackTrace();
		}
		if (data == null)
			return null;
		jsonObject = JSONObject.parseObject(data);
		try {
			s=jsonObject.getString("s");
		} catch (Exception e) {
			LOGGER.error("加密s错误");
			// TODO: handle exception
			s=null;
		}
		return s;
	}
	public static Weather httpPostWeather(String city,Short day) {
		String data = null;
		JSONObject jsonObject = null;
		String code="-1";
		Weather weather = null;
		Map<String, String> headers = null;
		Map<String, String> datas = null;
		headers = new HashMap<>(2);
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		datas = new HashMap<>(3);
		datas.put("city",city);
		datas.put("day", day.toString());
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpPostForm("http://129.204.79.75/getWeather", headers, datas)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			LOGGER.error("请求服务器超时，获取服务器链接失败");
			data = null;
		}
		if (data == null)
			return null;
		jsonObject = JSONObject.parseObject(data);
		code = jsonObject.getString("code");
		if (code.equals("200")) {
			weather = JSONObject.parseObject(jsonObject.getString("result"),Weather.class);
		} else {
			LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
		}
		return weather;
	}
	// 天气接口http://wthrcdn.etouch.cn/weather_mini?city=北京  备用

	public static Map<String, List<Weather>> httpGetweather(String city){
		String data = null;
		JSONObject jsonObject = null;
		short code=-1;
		Map<String, String> headers = null;
		headers = new HashMap<>(2);
		Map<String, List<Weather>> weathers = null;
		String cname = null;
		String ganmao = null;
		String wendu = null;
		headers.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
		try {
			data = OkHttp3Utils.getHttp3Utils()
					.httpGet("http://wthrcdn.etouch.cn/weather_mini?city="+city, headers, null)
					.body().string();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error(e);
			LOGGER.error("请求服务器超时，获取天气失败");
			data = null;
		}
		if (data == null)
			return null;
		try {
			jsonObject = JSONObject.parseObject(data);
			code = jsonObject.getShort("status");
			if (code==1000) {
				weathers = new HashMap<String, List<Weather>>();
				cname = ((JSONObject)jsonObject.get("data")).getString("city");
				ganmao = ((JSONObject)jsonObject.get("data")).getString("ganmao");
				wendu = ((JSONObject)jsonObject.get("data")).getString("wendu");
				List<Weather> oldWeathers = new ArrayList<>();
				Weather oldWeather = JSONObject.parseObject(((JSONObject)jsonObject.get("data")).getString("yesterday"),Weather.class);
				oldWeather.setCity(cname);
				oldWeather.setGanmao(ganmao);
				oldWeather.setWendu(wendu);
				oldWeathers.add(oldWeather);
				weathers.put("old",oldWeathers);
				List<Weather> newWeathers = JSONArray.parseArray(((JSONObject)jsonObject.get("data")).getString("forecast"),Weather.class);
				weathers.put("new",newWeathers);
				for(Iterator<Weather> iterator = newWeathers.iterator();iterator.hasNext();){
					Weather weather = iterator.next();
					weather.setWendu(wendu);
					weather.setGanmao(ganmao);
					weather.setCity(cname);
				}
			} else {
				LOGGER.error("未知错误,原因:" + jsonObject.getString("desc"));
			}
		} catch (Exception e) {
			weathers = null;
		}
		return weathers;
	}
}
