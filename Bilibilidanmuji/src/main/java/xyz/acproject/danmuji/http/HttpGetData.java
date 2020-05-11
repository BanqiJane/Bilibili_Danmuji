package xyz.acproject.danmuji.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.entity.Conf;
import xyz.acproject.danmuji.entity.Room;

public class HttpGetData {
	private static Logger LOGGER = LogManager.getLogger(HttpGetData.class);
	public static Conf httpGetConf(long roomid) {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		Conf conf =null;
		URL url  = null;
		try {
			roomid = httpGetRoomData(roomid)==null?0:Long.valueOf(httpGetRoomData(roomid).getRoomid());
			String urlString  = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id="+roomid+"&platform=pc&player=web";
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
			String msg = null;
			while (null!=(msg=bufferedReader.readLine())) {
				data =msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if(httpURLConnection!=null) {
				httpURLConnection.disconnect();
			}
		}
		LOGGER.debug("获取到的conf:"+data);
		jsonObject = JSONObject.parseObject(data);
		if(jsonObject.getInteger("code")==0) {
		conf = jsonObject.getObject("data", Conf.class);
		}else {
		LOGGER.error("未知错误,原因:"+jsonObject.getString("message"));
		}
		return conf;
	}
	public static Room httpGetRoomData(long roomid) {
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;
		String data = null;
		JSONObject jsonObject = null;
		Room room =null;
		URL url  = null;
		try {
			String urlString  = "https://api.live.bilibili.com/room_ex/v1/RoomNews/get?roomid="+roomid;
			url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
			String msg = null;
			while (null!=(msg=bufferedReader.readLine())) {
				data =msg;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if(httpURLConnection!=null) {
				httpURLConnection.disconnect();
			}
		}
		LOGGER.debug("获取到的room:"+data);
		jsonObject = JSONObject.parseObject(data);
		if(jsonObject.getInteger("code")==0) {
		room = jsonObject.getObject("data",Room.class);
		}else {
		LOGGER.error("直播房间号不存在，或者未知错误，请尝试更换房间号,原因:"+jsonObject.getString("message"));
		}
		return room;
	}
}
