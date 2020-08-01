package xyz.acproject.danmuji.conf;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.enums.AdvertStatus;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.thread.AdvertThread;
import xyz.acproject.danmuji.thread.FollowShieldThread;
import xyz.acproject.danmuji.thread.GiftShieldThread;
import xyz.acproject.danmuji.thread.LogThread;
import xyz.acproject.danmuji.thread.SendBarrageThread;
import xyz.acproject.danmuji.thread.core.HeartByteThread;
import xyz.acproject.danmuji.thread.core.ParseMessageThread;
import xyz.acproject.danmuji.thread.online.HeartBeatThread;
import xyz.acproject.danmuji.thread.online.HeartBeatsThread;
import xyz.acproject.danmuji.thread.online.UserOnlineHeartThread;

/**
 * @author zjian
 *
 */
public class ThreadConf {

	
	/**
	 * core 处理弹幕信息线程 中心线程
	 * 
	 * @param messageControlMap
	 * @param centerSetConf
	 * @return
	 */
	public static boolean startParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf) {
		if (PublicDataConf.parseMessageThread != null) {
			PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
			PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
			PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
			return false;
		}
		PublicDataConf.parseMessageThread = new ParseMessageThread();
		PublicDataConf.parseMessageThread.FLAG = false;
		PublicDataConf.parseMessageThread.start();
		PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
		PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
		PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
		if (PublicDataConf.parseMessageThread != null
				&& !PublicDataConf.parseMessageThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * core 关闭处理弹幕信息线程线程
	 */
	public static void closeParseMessageThread() {
		if (PublicDataConf.parseMessageThread != null) {
			PublicDataConf.parseMessageThread.FLAG = true;
			PublicDataConf.parseMessageThread.interrupt();
			PublicDataConf.parseMessageThread = null;
		}
	}

	/**
	 * 
	 * core 开启服务器心跳包线程
	 * 
	 * @return
	 */
	public static boolean startHeartByteThread() {
		if (PublicDataConf.heartByteThread != null) {
			return false;
		}
		PublicDataConf.heartByteThread = new HeartByteThread();
		PublicDataConf.heartByteThread.HFLAG = false;
		PublicDataConf.heartByteThread.start();
		if (PublicDataConf.heartByteThread != null
				&& !PublicDataConf.heartByteThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * core 关闭服务器心跳包线程
	 */
	public static void closeHeartByteThread() {
		if (PublicDataConf.heartByteThread != null) {
			PublicDataConf.heartByteThread.HFLAG = true;
			PublicDataConf.heartByteThread.interrupt();
			PublicDataConf.heartByteThread = null;
		}
	}

	/**
	 * 
	 * 日志线程开
	 * 
	 * @return
	 */
	public static boolean startLogThread() {
		if (PublicDataConf.logThread != null) {
			return false;
		}
		PublicDataConf.logThread = new LogThread();
		PublicDataConf.logThread.FLAG = false;
		PublicDataConf.logThread.start();
		if (PublicDataConf.logThread != null && !PublicDataConf.logThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 日志线程关
	 * 
	 * @return
	 */
	public static void closeLogThread() {
		if (PublicDataConf.logThread != null) {
			PublicDataConf.logThread.FLAG = true;
			PublicDataConf.logThread.interrupt();
			PublicDataConf.logThread = null;
		}
	}


	/**
	 * 
	 * 开启公告线程
	 * 
	 * @param advertStatus
	 * @param seconds
	 * @param advertBarrage
	 * @return
	 */
	public static boolean startAdvertThread(AdvertStatus advertStatus, short seconds, String advertBarrage) {
		if (PublicDataConf.advertThread != null || StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.advertThread.setAdvertStatus(advertStatus);
			PublicDataConf.advertThread.setTime(seconds);
			PublicDataConf.advertThread.setAdvertBarrage(advertBarrage);
			return false;
		}
		PublicDataConf.advertThread = new AdvertThread();
		PublicDataConf.advertThread.FLAG = false;
		PublicDataConf.advertThread.setAdvertStatus(advertStatus);
		PublicDataConf.advertThread.setTime(seconds);
		PublicDataConf.advertThread.setAdvertBarrage(advertBarrage);
		PublicDataConf.advertThread.start();
		startSendBarrageThread();
		if (PublicDataConf.advertThread != null
				&& !PublicDataConf.advertThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 关闭公告线程
	 */
	public static void closeAdvertThread() {
		if (PublicDataConf.advertThread != null) {
			PublicDataConf.advertThread.FLAG = true;
			PublicDataConf.advertThread.interrupt();
			PublicDataConf.advertThread = null;
		}
		if (PublicDataConf.parsethankFollowThread == null) {
			closeSendBarrageThread();
		}
	}

	/**
	 * 
	 * 开启弹幕发送线程
	 * 
	 * @return
	 */
	public static boolean startSendBarrageThread() {
		if (PublicDataConf.sendBarrageThread != null || StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			return false;
		}
		PublicDataConf.sendBarrageThread = new SendBarrageThread();
		PublicDataConf.sendBarrageThread.FLAG = false;
		PublicDataConf.sendBarrageThread.start();
		return false;
	}

	/**
	 * 
	 * 关闭弹幕发送线程
	 */
	public static void closeSendBarrageThread() {
		if (PublicDataConf.sendBarrageThread != null) {
			PublicDataConf.sendBarrageThread.FLAG = true;
			PublicDataConf.sendBarrageThread.interrupt();
			PublicDataConf.sendBarrageThread = null;
		}

	}

	/**
	 * 
	 * 用户在线心跳线程 三合一;
	 * 
	 * @return
	 */
	public static boolean startUserOnlineThread() {
		if (PublicDataConf.heartBeatThread != null || PublicDataConf.heartBeatsThread != null
				|| PublicDataConf.userOnlineHeartThread != null) {
			return false;
		}
		PublicDataConf.heartBeatThread = new HeartBeatThread();
		PublicDataConf.heartBeatThread.FLAG = false;
		PublicDataConf.heartBeatThread.start();

		PublicDataConf.heartBeatsThread = new HeartBeatsThread();
		PublicDataConf.heartBeatsThread.FLAG = false;
		PublicDataConf.heartBeatsThread.start();

		PublicDataConf.userOnlineHeartThread = new UserOnlineHeartThread();
		PublicDataConf.userOnlineHeartThread.FLAG = false;
		PublicDataConf.userOnlineHeartThread.start();

		if (PublicDataConf.heartBeatThread != null && PublicDataConf.heartBeatsThread != null
				&& PublicDataConf.userOnlineHeartThread != null
				&& !PublicDataConf.heartBeatThread.getState().toString().equals("TERMINATED")
				&& !PublicDataConf.heartBeatsThread.getState().toString().equals("TERMINATED")
				&& !PublicDataConf.userOnlineHeartThread.getState().toString().equals("TERMINATED")) {
			return true;
		} else {
			closeUserOnlineThread();
		}
		return false;
	}

	/**
	 * 
	 * 关闭用户在线心跳线程
	 */
	public static void closeUserOnlineThread() {
		if (PublicDataConf.userOnlineHeartThread != null) {
			PublicDataConf.userOnlineHeartThread.FLAG = true;
			PublicDataConf.userOnlineHeartThread.interrupt();
			PublicDataConf.userOnlineHeartThread = null;
		}
		if (PublicDataConf.heartBeatThread != null) {
			PublicDataConf.heartBeatThread.FLAG = true;
			PublicDataConf.heartBeatThread.interrupt();
			PublicDataConf.heartBeatThread = null;
		}
		if (PublicDataConf.heartBeatsThread != null) {
			PublicDataConf.heartBeatsThread.FLAG = true;
			PublicDataConf.heartBeatsThread.interrupt();
			PublicDataConf.heartBeatsThread = null;
		}
	}

	public static boolean startGiftShieldThread(String giftName, int time) {
		if (PublicDataConf.parsethankGiftThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parsethankGiftThread.getState().toString().equals("NEW")) {
			PublicDataConf.giftShieldThread = new GiftShieldThread();
			PublicDataConf.giftShieldThread.FLAG = false;
			PublicDataConf.giftShieldThread.setGiftName(giftName);
			PublicDataConf.giftShieldThread.setTime(time);
			PublicDataConf.giftShieldThread.start();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 关闭天选礼物屏蔽线程
	 */
	public static void closeGiftShieldThread() {
		if (PublicDataConf.giftShieldThread!=null&&!PublicDataConf.giftShieldThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.giftShieldThread.FLAG = false;
			PublicDataConf.giftShieldThread.interrupt();
		}
	}
	
	public static boolean startFollowShieldThread(int time) {
		if (PublicDataConf.parsethankFollowThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parsethankFollowThread.getState().toString().equals("NEW")) {
			PublicDataConf.followShieldThread = new FollowShieldThread();
			PublicDataConf.followShieldThread.FLAG =false;
			PublicDataConf.followShieldThread.setTime(time);
			PublicDataConf.followShieldThread.start();
			return true;
		}
		return false;
	}
	
	public static void closeFollowShieldThread() {
		if (PublicDataConf.followShieldThread!=null&&!PublicDataConf.followShieldThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.followShieldThread.FLAG =false;
			PublicDataConf.followShieldThread.interrupt();
		}
	}
	/**
	 * 设置过滤信息线程信息
	 * 
	 * @param messageControlMap
	 * @param centerSetConf
	 */
	public static void setParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf) {
		if(PublicDataConf.parseMessageThread!=null) {
			PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
			PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
			PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
		}
	}
	/**
	 * 设置公告线程信息
	 * @param advertStatus
	 * @param seconds
	 * @param advertBarrage
	 */
	public static void setAdvertThread(AdvertStatus advertStatus, short seconds, String advertBarrage) {
		if(PublicDataConf.advertThread!=null) {
			PublicDataConf.advertThread.setAdvertStatus(advertStatus);
			PublicDataConf.advertThread.setTime(seconds);
			PublicDataConf.advertThread.setAdvertBarrage(advertBarrage);
		}
	}
}
