package xyz.acproject.danmuji.service.impl;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.component.ServerAddressComponent;
import xyz.acproject.danmuji.component.TaskRegisterComponent;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.AdvertSetConf;
import xyz.acproject.danmuji.conf.set.AutoReplySetConf;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;
import xyz.acproject.danmuji.conf.set.ThankGiftSetConf;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.file.ProFileTools;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.BASE64Encoder;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;
import xyz.acproject.danmuji.utils.SchedulingRunnableUtil;

/**
 * @ClassName SetServiceImpl
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:17:03
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@SuppressWarnings("all")
@Service
public class SetServiceImpl implements SetService {
	private Logger LOGGER = LogManager.getLogger(SetServiceImpl.class);
	private final String cookies = "ySZL4SBB";
	@Autowired
	private ClientService clientService;
	@Autowired
	private ThreadComponent threadComponent;
	@Autowired
	private ServerAddressComponent serverAddressComponent;
	@Autowired
	private TaskRegisterComponent taskRegisterComponent;

	public void init(int i) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		String cookieString = null;
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			hashtable.putAll(ProFileTools.read("DanmujiProfile"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 读取本地cookie
		try {
			cookieString = !StringUtils.isEmpty(hashtable.get(cookies))
					? new String(base64Encoder.decode(hashtable.get(cookies)))
					: null;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error("获取本地cookie失败,请重新登录" + e);
			cookieString = null;
		}
		if (!StringUtils.isEmpty(cookieString)) {
			if (StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				PublicDataConf.USERCOOKIE = cookieString;
			}
		}
		HttpUserData.httpGetUserInfo();
		if (PublicDataConf.USER == null) {
			PublicDataConf.USERCOOKIE = null;
		} else {
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				hashtable.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
			}
		}
		// 读取本地set
		try {
			PublicDataConf.centerSetConf = !StringUtils.isEmpty(hashtable.get("set")) ? JSONObject
					.parseObject(new String(base64Encoder.decode(hashtable.get("set"))), CenterSetConf.class) : null;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件失败,尝试重新读取" + e);
			PublicDataConf.centerSetConf = null;
		}
		if (PublicDataConf.centerSetConf == null) {
			PublicDataConf.centerSetConf = new CenterSetConf(new ThankGiftSetConf(), new AdvertSetConf(),
					new ThankFollowSetConf(), new AutoReplySetConf());
		} else {
			if (PublicDataConf.centerSetConf.getRoomid() != null && PublicDataConf.centerSetConf.getRoomid() > 0)
				PublicDataConf.ROOMID_SAFE = PublicDataConf.centerSetConf.getRoomid();
			if (PublicDataConf.ROOMID_SAFE != null && PublicDataConf.ROOMID_SAFE > 0)
				PublicDataConf.centerSetConf.setRoomid(PublicDataConf.ROOMID_SAFE);
		}
		if (PublicDataConf.centerSetConf.getAdvert() == null) {
			PublicDataConf.centerSetConf.setAdvert(new AdvertSetConf());
		}
		if (PublicDataConf.centerSetConf.getFollow() == null) {
			PublicDataConf.centerSetConf.setFollow(new ThankFollowSetConf());
		}
		if (PublicDataConf.centerSetConf.getThank_gift() == null) {
			PublicDataConf.centerSetConf.setThank_gift(new ThankGiftSetConf());
		}
		if (PublicDataConf.centerSetConf.getReply() == null) {
			PublicDataConf.centerSetConf.setReply(new AutoReplySetConf());
		}
		hashtable.put("set", base64Encoder.encode(PublicDataConf.centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject
					.parseObject(new String(base64Encoder.decode(hashtable.get("set"))), CenterSetConf.class);
			LOGGER.debug("读取配置文件成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件失败" + e);
		}
		// 分离cookie
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE) && PublicDataConf.COOKIE == null) {
			String key = null;
			String value = null;
			int controlNum = 0;
			String cookie = PublicDataConf.USERCOOKIE;
			PublicDataConf.COOKIE = new UserCookie();
			cookie = cookie.trim();
			String[] a = cookie.split(";");
			for (String string : a) {
				if (string.contains("=")) {
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
//						LOGGER.debug("获取cookie失败，字段为" + key);
					}
				}
			}
			if (controlNum == 5) {
				LOGGER.debug("用户cookie装载成功");
				controlNum = 0;
			} else {
				LOGGER.debug("用户cookie装载失败");
				PublicDataConf.COOKIE = null;
			}
			if (PublicDataConf.ROOMID != null) {
				holdSet(PublicDataConf.centerSetConf);
			}
		}
		if (i == 0) {
			// 公告和检查更新
			System.out.println();
			System.out.println();
			System.out.println(
					"参考本地浏览器进入设置页面地址: 1、http://127.0.0.1:" + serverAddressComponent.getPort() + ";2、http://localhost:"
							+ serverAddressComponent.getPort() + ";3、" + serverAddressComponent.getAddress());
			System.out.println("参考局域网浏览器进入设置页面地址: 1、" + serverAddressComponent.getAddress());
			System.out.println("参考远程(无代理)浏览器进入设置页面地址: 1、" + serverAddressComponent.getRemoteAddress());
			System.out.println();
			System.out.println("最新公告：" + HttpOtherData.httpGetNewAnnounce());
			String edition = HttpOtherData.httpGetNewEdition();
			if (!StringUtils.isEmpty(edition)) {
				if (!edition.equals(PublicDataConf.EDITION)) {
					System.out.println("查询最新版本：" + edition
							+ "目前脚本有可用更新哦，请到github官网查看更新https://github.com/BanqiJane/Bilibili_Danmuji");
				} else {
					System.out.println("查询最新版本：目前使用的版本为最新版本，暂无可用更新");
				}
			} else {
				System.out.println("查询最新版本失败,打印目前版本：" + PublicDataConf.EDITION);
			}
			System.out.println();
			// 自动连接
			if (PublicDataConf.centerSetConf.isIs_auto() && PublicDataConf.centerSetConf.getRoomid() > 0) {
				try {
					clientService.startConnService(PublicDataConf.centerSetConf.getRoomid());
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			// window用默认浏览器打开网页
			try {
				Runtime.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler " + serverAddressComponent.getAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(
						"自动打开浏览器错误:当前系统缺少rundll32 url.dll组件或者不是window系统，无法自动启动默认浏览器打开配置页面，请手动打开浏览器地址栏输入http://127.0.0.1:23333进行配置");
			}
		}
		base64Encoder = null;
		hashtable.clear();
	}

	// 保存配置文件
	public void changeSet(CenterSetConf centerSetConf) {
		if (centerSetConf.toJson().equals(PublicDataConf.centerSetConf.toJson())) {
			LOGGER.debug("保存配置文件成功");
			return;
		}
		if (PublicDataConf.ROOMID_SAFE != null && PublicDataConf.ROOMID_SAFE > 0) {
			centerSetConf.setRoomid(PublicDataConf.ROOMID_SAFE);
		}
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		if (PublicDataConf.USER != null) {
			hashtable.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
		}
		hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject.parseObject(
					new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
					CenterSetConf.class);
			if (PublicDataConf.ROOMID != null) {
				holdSet(centerSetConf);
			}
			LOGGER.debug("保存配置文件成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("保存配置文件失败:" + e);
		}
		base64Encoder = null;
		hashtable.clear();
	}

	public void connectSet(CenterSetConf centerSetConf) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		if (PublicDataConf.USER != null) {
			hashtable.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
		}
		hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
		ProFileTools.write(hashtable, "DanmujiProfile");
		try {
			PublicDataConf.centerSetConf = JSONObject.parseObject(
					new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
					CenterSetConf.class);
			if (PublicDataConf.ROOMID != null) {
				holdSet(centerSetConf);
			}
			LOGGER.debug("读取配置文件历史房间成功");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("读取配置文件历史房间失败:" + e);
		}
		base64Encoder = null;
		hashtable.clear();
	}


	/**
	 * 保存配置并执行
	 *
	 */
	public void holdSet(CenterSetConf centerSetConf) {
		if (PublicDataConf.centerSetConf.isIs_dosign()) {
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				if(!PublicDataConf.is_sign) {
				HttpUserData.httpGetDoSign();
				SchedulingRunnableUtil task = new SchedulingRunnableUtil("dosignTask", "dosign",null);
				taskRegisterComponent.addTask(task, "0 30 0 * * ?");
				PublicDataConf.is_sign = true;
				}
			}
		}else {
			try {
				taskRegisterComponent.destroy();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				LOGGER.error("清理定时任务错误："+e);
			}
		}
		if (PublicDataConf.ROOMID == null) {
			return;
		}
		if (PublicDataConf.webSocketProxy == null) {
			return;
		}
		if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
			return;
		}
		// parsemessagethread start
		threadComponent.startParseMessageThread(
				ParseSetStatusTools.getMessageConcurrentHashMap(centerSetConf, PublicDataConf.lIVE_STATUS),
				centerSetConf);
		// logthread
		if (centerSetConf.isIs_log()) {
			threadComponent.startLogThread();
		} else {
			threadComponent.closeLogThread();
		}
		// need login
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			// advertthread
			if (centerSetConf.getAdvert().isIs_live_open()) {
				if (PublicDataConf.lIVE_STATUS != 1) {
					threadComponent.closeAdvertThread();
				} else {
					if (centerSetConf.getAdvert().isIs_open()) {
						threadComponent.startAdvertThread(centerSetConf);
					} else {
						threadComponent.setAdvertThread(centerSetConf);
						threadComponent.closeAdvertThread();
					}
				}
			} else {
				if (centerSetConf.getAdvert().isIs_open()) {
					threadComponent.startAdvertThread(centerSetConf);
				} else {
					threadComponent.setAdvertThread(centerSetConf);
					threadComponent.closeAdvertThread();
				}
			}
			// autoreplythread
			if (centerSetConf.getReply().isIs_live_open()) {
				if (PublicDataConf.lIVE_STATUS != 1) {
					threadComponent.closeAutoReplyThread();
				} else {
					if (centerSetConf.getReply().isIs_open()) {
						threadComponent.startAutoReplyThread(centerSetConf);
					} else {
						threadComponent.setAutoReplyThread(centerSetConf);
						threadComponent.closeAutoReplyThread();
					}
				}
			} else {
				if (centerSetConf.getReply().isIs_open()) {
					threadComponent.startAutoReplyThread(centerSetConf);
				} else {
					threadComponent.setAutoReplyThread(centerSetConf);
					threadComponent.closeAutoReplyThread();
				}
			}
			// useronlinethread && smallHeartThread
			if (centerSetConf.isIs_online()) {
				threadComponent.startUserOnlineThread();
				if (centerSetConf.isIs_sh()&&PublicDataConf.lIVE_STATUS==1) {
					threadComponent.startSmallHeartThread();
				} else {
					threadComponent.closeSmallHeartThread();
				}
			} else {
				threadComponent.closeSmallHeartThread();
				threadComponent.closeUserOnlineThread();
			}
			// sendbarragethread
			if (PublicDataConf.advertThread == null
					&& !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_followThank)
					&& !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_giftThank)
					&& PublicDataConf.autoReplyThread == null) {
				threadComponent.closeSendBarrageThread();
				PublicDataConf.replys.clear();
				PublicDataConf.thankGiftConcurrentHashMap.clear();
				PublicDataConf.barrageString.clear();
				PublicDataConf.interacts.clear();
			} else {
				threadComponent.startSendBarrageThread();
			}
		} else {

			PublicDataConf.COOKIE = null;
			PublicDataConf.USER = null;
			PublicDataConf.USERCOOKIE = null;
			PublicDataConf.USERBARRAGEMESSAGE = null;
			threadComponent.closeAdvertThread();
			threadComponent.closeUserOnlineThread();
			threadComponent.closeGiftShieldThread();
			threadComponent.closeSendBarrageThread();
			threadComponent.closeFollowShieldThread();
			threadComponent.closeSmallHeartThread();
		}
		if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
			threadComponent.closeHeartByteThread();
			threadComponent.closeParseMessageThread();
			threadComponent.closeUserOnlineThread();
			threadComponent.closeFollowShieldThread();
			threadComponent.closeAdvertThread();
			threadComponent.closeSendBarrageThread();
			threadComponent.closeLogThread();
			threadComponent.closeGiftShieldThread();
			threadComponent.closeAutoReplyThread();
			threadComponent.closeSmallHeartThread();
			PublicDataConf.SHIELDGIFTNAME = null;
			PublicDataConf.replys.clear();
			PublicDataConf.resultStrs.clear();
			PublicDataConf.thankGiftConcurrentHashMap.clear();
			PublicDataConf.barrageString.clear();
			PublicDataConf.logString.clear();
			PublicDataConf.interacts.clear();
		}
	}

	public void quit() {
		PublicDataConf.COOKIE = null;
		PublicDataConf.USER = null;
		PublicDataConf.USERCOOKIE = null;
		PublicDataConf.USERBARRAGEMESSAGE = null;
		threadComponent.closeAdvertThread();
		threadComponent.closeUserOnlineThread();
		threadComponent.closeGiftShieldThread();
		threadComponent.closeFollowShieldThread();
		threadComponent.closeAutoReplyThread();
		threadComponent.closeSendBarrageThread();
		threadComponent.closeSmallHeartThread();
		// remove task all shutdown !!!!!!
		try {
			taskRegisterComponent.destroy();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.error("清理定时任务错误："+e);
		}
		PublicDataConf.replys.clear();
		PublicDataConf.thankGiftConcurrentHashMap.clear();
		PublicDataConf.barrageString.clear();
		PublicDataConf.interacts.clear();
		holdSet(PublicDataConf.centerSetConf);
		LOGGER.debug("用户退出成功");
	}
}
