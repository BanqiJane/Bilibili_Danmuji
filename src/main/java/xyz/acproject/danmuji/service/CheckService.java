package xyz.acproject.danmuji.service;

import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.SetMethodCode;
import xyz.acproject.danmuji.conf.ThreadConf;
import xyz.acproject.danmuji.conf.set.AdvertSetConf;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;
import xyz.acproject.danmuji.conf.set.ThankGiftSetConf;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.tools.BASE64Encoder;
import xyz.acproject.danmuji.tools.ProFileTools;

@Service
public class CheckService {
	private Logger LOGGER = LogManager.getLogger(CheckService.class);
	private String cookies ="ySZL4SBB";

	public void start() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		String cookieString = null;
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			hashtable.putAll(ProFileTools.read("DanmujiProfile"));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		//cookie
		try {
			cookieString =!StringUtils.isEmpty(hashtable.get(cookies)) 
					? new String(base64Encoder
							.decode(hashtable.get(cookies)))
					: null;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error("获取本地cookie失败,请重新登录" + e);
			cookieString = null;
		}
		if (!StringUtils.isEmpty(cookieString)) {
			if(StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.USERCOOKIE = cookieString;
			}
		}
		HttpUserData.httpGetUserInfo();
		if (PublicDataConf.USER == null) {
			PublicDataConf.USERCOOKIE = null;
		} else {
			if(!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				hashtable.put(cookies,
				base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
			}
		}
		//set
		try {
			PublicDataConf.centerSetConf =!StringUtils.isEmpty(hashtable.get("set"))?JSONObject.parseObject(
					new String(base64Encoder.decode(hashtable.get("set"))),
					CenterSetConf.class):null;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件失败,尝试重新读取" + e);
			PublicDataConf.centerSetConf = null;
		}
		if (PublicDataConf.centerSetConf == null) {
			PublicDataConf.centerSetConf = new CenterSetConf(new ThankGiftSetConf(), new AdvertSetConf(),
					new ThankFollowSetConf());
		}
		hashtable.put("set", base64Encoder.encode(PublicDataConf.centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject.parseObject(
					new String(base64Encoder.decode(hashtable.get("set"))),
					CenterSetConf.class);
			LOGGER.debug("读取配置文件成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件失败" + e);
		}
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)&&PublicDataConf.COOKIE==null) {	
			String key = null;
			String value = null;
			int controlNum = 0;
			String cookie = PublicDataConf.USERCOOKIE;
			PublicDataConf.COOKIE = new UserCookie();
			cookie = cookie.trim();
			String[] a = cookie.split(";");
			for (String string : a) {
				key = string.split("=")[0];
				value = string.split("=")[1];
				if (key.equals("DedeUserID")) {
					PublicDataConf.COOKIE.setDedeUserID(value);
					controlNum++;
				} else if (key.equals("bili_jct")) {
					PublicDataConf.COOKIE.setBili_jct(value);
					controlNum++;
				} else if (key.equals("DedeUserID__ckMd5")) {
					PublicDataConf.COOKIE.setDedeUserID__ckMd5(value);
					controlNum++;
				} else if (key.equals("sid")) {
					PublicDataConf.COOKIE.setSid(value);
					controlNum++;
				} else if (key.equals("SESSDATA")) {
					PublicDataConf.COOKIE.setSESSDATA(value);
					controlNum++;
				} else {
					LOGGER.debug("获取cookie失败，字段为" + key);
				}
			}
			if (controlNum == 5) {
				LOGGER.debug("用户cookie装载成功");
				controlNum = 0;
			} else {
				LOGGER.debug("用户cookie装载失败");
				PublicDataConf.COOKIE=null;
			}
			if (PublicDataConf.ROOMID != null) {
				SetMethodCode.modifySet(PublicDataConf.centerSetConf);
			}
		}
		base64Encoder=null;
		hashtable.clear();
	}

	public void changeSet(CenterSetConf centerSetConf) {
		if (centerSetConf.toJson().equals(PublicDataConf.centerSetConf.toJson())) {
			LOGGER.debug("保存配置文件成功");
			return;
		}
		if(PublicDataConf.ROOMID!=null) {
			centerSetConf.setRoomid(PublicDataConf.ROOMID);
	    }
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		if (PublicDataConf.USER != null) {
			hashtable.put(cookies,
					base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
		}
		hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject.parseObject(
					new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
					CenterSetConf.class);
			if (PublicDataConf.ROOMID != null) {
				SetMethodCode.modifySet(centerSetConf);
			}
			LOGGER.debug("保存配置文件成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("保存配置文件失败:" + e);
		}
		base64Encoder=null;
		hashtable.clear();
	}
	public void connectSet(CenterSetConf centerSetConf) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		if (PublicDataConf.USER != null) {
			hashtable.put(cookies,
					base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
		}
		hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject.parseObject(
					new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
					CenterSetConf.class);
			if (PublicDataConf.ROOMID != null) {
				SetMethodCode.modifySet(centerSetConf);
			}
			LOGGER.debug("读取配置文件历史房间成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件历史房间失败:" + e);
		}
		base64Encoder=null;
		hashtable.clear();
	}

	public void quit() {
		PublicDataConf.COOKIE=null;
		PublicDataConf.USER = null;
		PublicDataConf.USERCOOKIE=null;
		PublicDataConf.USERBARRAGEMESSAGE=null;
		ThreadConf.closeAdvertThread();
		ThreadConf.closeUserOnlineThread();
		ThreadConf.closeGiftShieldThread();
		ThreadConf.closeSendBarrageThread();
		SetMethodCode.modifySet(PublicDataConf.centerSetConf);
	}
}
