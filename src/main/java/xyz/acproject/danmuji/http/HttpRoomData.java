package xyz.acproject.danmuji.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.room_data.CheckTx;
import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.entity.room_data.RoomInit;
import xyz.acproject.danmuji.entity.server_data.Conf;

public class HttpRoomData {
	private static Logger LOGGER = LogManager.getLogger(HttpRoomData.class);

	/**
	 * 获取连接目标房间websocket端口 接口
	 * 
	 * @param roomid
	 * @return
	 */
	public static Conf httpGetConf() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		Conf conf = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?id=" + PublicDataConf.ROOMID
					+ "&type=0";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
//			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE) && PublicDataConf.USER != null) {
//				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
//			}
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
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		Room room = null;
		URL url = null;

		try {
			String urlString = "https://api.live.bilibili.com/room_ex/v1/RoomNews/get?roomid=" + roomid;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
			httpURLConnection.setConnectTimeout(6000);
			httpURLConnection.setReadTimeout(6000);
//			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
//				httpURLConnection.setRequestProperty("cookie", PublicDataConf.USERCOOKIE);
//			}
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
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
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
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		RoomInit roomInit = null;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/room/v1/Room/room_init?id=" + roomid;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
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
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			roomInit = jsonObject.getObject("data", RoomInit.class);
		} else {
			LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
		}
		;
		return roomInit;
	}

	/**
	 * 获取房间最详细信息 日后扩容 目前只是获取主播uid
	 * 
	 * @return
	 */
	public static Long httpGetRoomInfo() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		Long auid = 0L;
		try {
			String urlString = "https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id="
					+ PublicDataConf.ROOMID;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
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
//		LOGGER.debug("获取到的room:" + data);
		jsonObject = JSONObject.parseObject(data);
		short code = jsonObject.getShort("code");
		if (code == 0) {
			auid = ((JSONObject) ((JSONObject) jsonObject.get("data")).get("room_info")).getLong("uid");
		} else {
			LOGGER.error("获取房间详细信息失败，请稍后尝试:" + jsonObject.getString("message"));
		}
		return auid;
	}

	/**
	 * 获取关注名字集合
	 * 
	 * @return 关注uname集
	 */
	public static ConcurrentHashMap<Long, String> httpGetFollowers() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		Integer page = null;
		JSONArray jsonArray = null;
		short code = -1;
		ConcurrentHashMap<Long, String> followConcurrentHashMap = null;
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
			try {
				String urlString = "https://api.bilibili.com/x/relation/followers?vmid=" + PublicDataConf.AUID + "&pn="
						+ page + "&ps=50&order=desc&jsonp=jsonp";
				url = new URL(urlString);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setRequestProperty("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
				httpURLConnection.setRequestProperty("referer",
						"https://space.bilibili.com/{" + PublicDataConf.AUID + "}/");
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
			try {
				code = jsonObject.getShort("code");
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("获取关注错误");
				return followConcurrentHashMap;
			}

			if (code == 0) {
				PublicDataConf.FANSNUM =((JSONObject) jsonObject.get("data")).getLong("total");
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
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		Integer page = null;
		Long followersNum = 0L;
		if (PublicDataConf.AUID == null) {
			return followersNum;
		}
		try {
			page = 1;
			String urlString = "https://api.bilibili.com/x/relation/followers?vmid=" + PublicDataConf.AUID + "&pn="
					+ page + "&ps=50&order=desc&jsonp=jsonp";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer",
					"https://space.bilibili.com/{" + PublicDataConf.AUID + "}/");
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
			followersNum = ((JSONObject) jsonObject.get("data")).getLong("total");
		} else {
			LOGGER.error("获取关注数失败，请重试" + jsonObject.getString("message"));
		}
		return followersNum;
	}

	public static Hashtable<Long, String> httpGetGuardList() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		Hashtable<Long, String> hashtable = new Hashtable<Long, String>();
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		URL url = null;
		int totalSize = httpGetGuardListTotalSize();
		int page = 0;
		if (totalSize == 0) {
			return null;
		}
		page = (int) Math.ceil((float) totalSize / 29F);
		if (page == 0) {
			page = 1;
		}
		for (int i=1;i<=page;i++) {
			try {
				String urlString = "https://api.live.bilibili.com/xlive/app-room/v1/guardTab/topList?roomid="
						+ PublicDataConf.ROOMID + "&page=" + i + "&ruid=" + PublicDataConf.AUID + "&page_size=29";
				url = new URL(urlString);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setRequestProperty("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
				httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
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
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		int num = 0;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/xlive/app-room/v1/guardTab/topList?roomid="
					+ PublicDataConf.ROOMID + "&page=1&ruid=" + PublicDataConf.AUID + "&page_size=29";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
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
			num = ((JSONObject) ((JSONObject) jsonObject.get("data")).get("info")).getInteger("num");
		} else {
			LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:" + jsonObject.getString("message"));
		}
		return num;
	}
	public static CheckTx httpGetCheckTX() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		try {
			String urlString = "https://api.live.bilibili.com/xlive/lottery-interface/v1/Anchor/Check?roomid="+PublicDataConf.ROOMID;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			httpURLConnection.setRequestProperty("referer", "https://live.bilibili.com/" + PublicDataConf.ROOMID);
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
			if(jsonObject.get("data")!=null) {
				return new CheckTx(((JSONObject)jsonObject.get("data")).getLong("room_id"),((JSONObject)jsonObject.get("data")).getString("gift_name"),((JSONObject)jsonObject.get("data")).getShort("time"));
			}
		} else {
			LOGGER.error("检查天选礼物失败,原因:" + jsonObject.getString("message"));
		}
		return null;
	}
	public static String httpGetIp() {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		URL url = null;
		String status = null;
		String ip = null;
		try {
			String urlString = "http://ip-api.com/json/";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent",
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
		try {
			status = jsonObject.getString("status");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(status.equals("success")) {
			ip = jsonObject.getString("query");
		}else {
			LOGGER.error("获取ip失败"+jsonObject.toString());
		}
		return ip;
	}
}
