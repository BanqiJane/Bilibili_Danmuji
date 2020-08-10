package xyz.acproject.danmuji.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.RoomBarrageMsg.UserBarrageMsg;
import xyz.acproject.danmuji.entity.login_data.LoginData;
import xyz.acproject.danmuji.entity.login_data.Qrcode;
import xyz.acproject.danmuji.entity.user_data.User;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

/**
 * @ClassName HttpUserData
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:05
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpUserData {
	private static Logger LOGGER = LogManager.getLogger(HttpUserData.class);

	/**
	 * 初始化 获取用户信息+判断是否登陆状态
	 * 
	 */
	public static void httpGetUser() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://account.bilibili.com/home/USERInfo";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			LOGGER.debug("已经登录:" + jsonObject.toString());
		} else if (code == -101) {
			LOGGER.debug("未登录:" + jsonObject.toString());
		} else {
			LOGGER.error("未知错误,原因未知" + jsonObject.toString());
		}
	}

	/**
	 * 获取登陆二维码
	 * 
	 * @return
	 */
	public static Qrcode httpGetQrcode() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		Qrcode qrcode = null;
		try {
			String urlString = "https://passport.bilibili.com/qrcode/getLoginUrl";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			qrcode = JSONObject.parseObject(jsonObject.getString("data"), Qrcode.class);
		} else {
			LOGGER.error("获取二维码失败,未知错误,原因未知" + jsonObject.toString());
		}
		return qrcode;
	}

	/**
	 * 判断扫码状态 扫码确定后 获取用户cookie
	 * 
	 * @param logindata
	 * @return
	 */
	public static String httpPostCookie(LoginData logindata) {

		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			return "";
		}
		OutputStreamWriter out = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		URL url = null;
		StringBuilder stringBuilder = new StringBuilder();
		String cookieVal = "";
		String key = null;
		HashSet<String> hashSet = new HashSet<String>();
		try {
			String urlString = "https://passport.bilibili.com/qrcode/getLoginInfo";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://passport.bilibili.com/login");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
			String param = "oauthKey=" + logindata.getOauthKey() + "&gourl=" + logindata.getGourl();
			out.write(param);
			out.flush();
			// 取cookie
			for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++) {
				if (key.equalsIgnoreCase("set-cookie")) {
					cookieVal = httpURLConnection.getHeaderField(i);
					cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
					hashSet.add(cookieVal);
//					sessionId = sessionId + cookieVal + ";";
				}
			}
			hashSet.forEach(value -> {
				stringBuilder.append(value);
				stringBuilder.append(";");
			});
			if (!StringUtils.isEmpty(stringBuilder.toString())) {
				if (stringBuilder.length()>13&&StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
					PublicDataConf.USERCOOKIE = stringBuilder.toString();
					if(PublicDataConf.ROOMID!=null) {
						httpGetUserBarrageMsg();
					}
					LOGGER.debug("扫码登录成功");
				}
			}
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
			bufferedReader.close();
			httpURLConnection.disconnect();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
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
		return data;
	}

	/**
	 * 获取用户信息 需要cookie 初始化
	 * 
	 */
	public static void httpGetUserInfo() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/User/getUserInfo";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
		jsonObject = JSONObject.parseObject(data);
		if (jsonObject.getString("code").equals("REPONSE_OK")) {
			PublicDataConf.USER = new User();
			PublicDataConf.USER = JSONObject.parseObject(jsonObject.getString("data"), User.class);
			LOGGER.debug("已经登录，获取信息成功");
		} else if (jsonObject.getShort("code") == -500) {
			LOGGER.debug("未登录，请登录:" + jsonObject.toString());
			PublicDataConf.USERCOOKIE=null;
			PublicDataConf.USER=null;
		} else {
			LOGGER.error("未知错误,原因未知" + jsonObject.toString());
			PublicDataConf.USERCOOKIE=null;
			PublicDataConf.USER=null;
		}
	}

	/**
	 * 获取用户在目标房间所能发送弹幕的最大长度
	 * 
	 */
	public static void httpGetUserBarrageMsg() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByUser?room_id="
					+ PublicDataConf.ROOMID;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
				data = msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			LOGGER.debug("获取本房间可发送弹幕长度成功");
			PublicDataConf.USERBARRAGEMESSAGE = JSONObject
					.parseObject((((JSONObject) jsonObject.get("data")).getString("property")), UserBarrageMsg.class);
		} else if (code == -101) {
			LOGGER.debug("未登录，请登录:" + jsonObject.toString());
		} else if (code == -400) {
			LOGGER.debug("房间号不存在或者未输入房间号:" + jsonObject.toString());
		} else {
			LOGGER.error("未知错误,原因未知" + jsonObject.toString());
		}

	}

	/**
	 * 发送弹幕
	 * 
	 * @param msg 弹幕信息
	 * @return
	 */
	public static Short httpGetSendBarrage(String msg) {
		JSONObject jsonObject = null;
		OutputStreamWriter out = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		URL url = null;
		short code= -1;
		StringBuilder stringBuilder = new StringBuilder();
		String param = null;
		try {
			String urlString = "https://api.live.bilibili.com/msg/send";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			httpURLConnection.setConnectTimeout(6000);
			httpURLConnection.setReadTimeout(6000);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
			msg = URLDecoder.decode(msg, "UTF-8");
			msg = URLDecoder.decode(msg, "UTF-8");
			if (PublicDataConf.USERBARRAGEMESSAGE != null&&PublicDataConf.COOKIE!=null) {
				stringBuilder.append("color=").append(PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getColor())
				.append("&fontsize=").append(25).append("&mode=").append(PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getMode())
				.append("&msg=").append(msg).append("&rnd=").append(Long.valueOf((String.valueOf(System.currentTimeMillis()).substring(0, 10))))
				.append("&roomid=").append(PublicDataConf.ROOMID).append("&bubble=").append(PublicDataConf.USERBARRAGEMESSAGE.getBubble())
				.append("&csrf_token=").append(PublicDataConf.COOKIE.getBili_jct()).append("&csrf=").append(PublicDataConf.COOKIE.getBili_jct());
				param = stringBuilder.toString();
			}
			out.write(param);
			out.flush();
			stringBuilder.delete(0, stringBuilder.length());
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msgs = null;
			while (null != (msgs = bufferedReader.readLine())) {
				data = msgs;
			}
			bufferedReader.close();
			httpURLConnection.disconnect();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
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
		jsonObject = JSONObject.parseObject(data);
//		System.out.println(jsonObject.toJSONString().toString());
		if(jsonObject!=null) {
		code = jsonObject.getShort("code");
		if (code == 0) {
			if(StringUtils.isEmpty(jsonObject.getString("message").trim())) {
//				LOGGER.debug("发送弹幕成功");
			}else if(jsonObject.getString("message").equals("msg in 1s")||jsonObject.getString("message").equals("msg repeat")){
				LOGGER.debug("发送弹幕失败，尝试重新发送"+jsonObject.getString("message"));
				PublicDataConf.barrageString.add(msg);
				synchronized (PublicDataConf.sendBarrageThread) {
					PublicDataConf.sendBarrageThread.notify();
			}
			}else{
				LOGGER.debug(jsonObject.toString());
				LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
				code = -402;
			}
		} else if (code == -111) {
			LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
		} else if (code == -500) {
			LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
		} else if (code == 11000) {
			LOGGER.error("发送弹幕失败,原因:弹幕含有关键字或者弹幕颜色不存在:" + jsonObject.getString("message"));
		} else {
			LOGGER.error("发送弹幕失败,未知错误,原因未知" + jsonObject.toString());
		}
		}else {
			return code;
		}
		return code;
	}
	/**
	 * 发送私聊
	 * 
	 * @param recId 接受人uid
	 * @param msg 信息
	 * @return 
	 */
	public static Short httpPostSendMsg(long recId,String msg) {
		JSONObject jsonObject = null;
		OutputStreamWriter out = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		URL url = null;
		StringBuilder stringBuilder = new StringBuilder();
		String param = null;
		String content = null;
		try {
			String urlString = "https://api.vc.bilibili.com/web_im/v1/web_im/send_msg";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://message.bilibili.com/");
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
			if (PublicDataConf.USERBARRAGEMESSAGE != null&&PublicDataConf.COOKIE!=null) {
				try {
					msg = URLDecoder.decode(msg, "UTF-8");
					msg = URLDecoder.decode(msg, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				content = "{\"content\":\""+msg+"\"}";
				stringBuilder.append("msg%5Bsender_uid%5D=").append(PublicDataConf.USER.getUid())
				.append("&msg%5Breceiver_id%5D=").append(recId)
				.append("&msg%5Breceiver_type%5D=1").append("&msg%5Bmsg_type%5D=1").append("&msg%5Bmsg_status%5D=0")
				.append("&msg%5Bcontent%5D=").append(content).append("&msg%5Btimestamp%5D=")
				.append(JodaTimeUtils.getTimestamp()).append("&msg%5Bdev_id%5D=")
				.append("&build=0").append("&mobi_app=web").append("&csrf_token=")
				.append(PublicDataConf.COOKIE.getBili_jct());
				
				param = stringBuilder.toString();
			}
			out.write(param);
			out.flush();
            stringBuilder.delete(0, stringBuilder.length());
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msgs = null;
			while (null != (msgs = bufferedReader.readLine())) {
				data = msgs;
			}
			bufferedReader.close();
			httpURLConnection.disconnect();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
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
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			if(jsonObject.getString("message").equals("ok")) {
				//发送私聊成功
			}else{
				LOGGER.error("发送私聊失败,未知错误,原因未知v" + jsonObject.toString());
			}
		} else {
			LOGGER.error("发送私聊失败,未知错误,原因未知" + jsonObject.toString());
		}
		return code;
	}
	/**
	 * 禁言
	 * 
	 * @param uid 被禁言人uid
	 * @param hour 禁言时间 单位小时
	 * @return
	 */
	public static Short httpPostAddBlock(long uid,short hour) {
		JSONObject jsonObject = null;
		OutputStreamWriter out = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		URL url = null;
		StringBuilder stringBuilder = new StringBuilder();
		String param = null;
		if(hour<1) {
			hour=1;
		}
		if(hour>720) {
			hour=720;
		}
		try {
			String urlString = "https://api.live.bilibili.com/banned_service/v2/Silent/add_block_user";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/"+PublicDataConf.ROOMID);
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			out = new OutputStreamWriter(httpURLConnection.getOutputStream(), "utf-8");
			if (PublicDataConf.USERBARRAGEMESSAGE != null&&PublicDataConf.COOKIE!=null) {
				stringBuilder.append("roomid=").append(PublicDataConf.ROOMID)
				.append("&block_uid=").append(uid).append("&hour=").append(hour)
				.append("&csrf_token=").append(PublicDataConf.COOKIE.getBili_jct()).append("&csrf=")
				.append(PublicDataConf.COOKIE.getBili_jct()).append("&visit_id=");
				param = stringBuilder.toString();
			}
			out.write(param);
			out.flush();
            stringBuilder.delete(0, stringBuilder.length());
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String msgs = null;
			while (null != (msgs = bufferedReader.readLine())) {
				data = msgs;
			}
			bufferedReader.close();
			httpURLConnection.disconnect();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
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
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			//禁言成功
//			System.out.println(jsonObject.getString("data"));
		}else {
			LOGGER.error("禁言失败,原因" + jsonObject.getString("msg"));
		}
		return code;
	}
	
	
	
	/**
	 * 
	 * 退出 删除cookie
	 * 
	 */
	public static void quit() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		URL url = null;
		try {
			String urlString = "https://passport.bilibili.com/login?act=exit";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("USER-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://www.bilibili.com/");
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			@SuppressWarnings("unused")
			String msg = null;
			while (null != (msg = bufferedReader.readLine())) {
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
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
	}
}
