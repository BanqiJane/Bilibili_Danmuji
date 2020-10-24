package xyz.acproject.danmuji.component.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.AutoReplySet;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.conf.set.ThankGiftSetConf;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.thread.AdvertThread;
import xyz.acproject.danmuji.thread.AutoReplyThread;
import xyz.acproject.danmuji.thread.FollowShieldThread;
import xyz.acproject.danmuji.thread.GiftShieldThread;
import xyz.acproject.danmuji.thread.LogThread;
import xyz.acproject.danmuji.thread.ParseThankFollowThread;
import xyz.acproject.danmuji.thread.ParseThankGiftThread;
import xyz.acproject.danmuji.thread.SendBarrageThread;
import xyz.acproject.danmuji.thread.core.HeartByteThread;
import xyz.acproject.danmuji.thread.core.ParseMessageThread;
import xyz.acproject.danmuji.thread.online.HeartBeatThread;
import xyz.acproject.danmuji.thread.online.HeartBeatsThread;
import xyz.acproject.danmuji.thread.online.SmallHeartThread;
import xyz.acproject.danmuji.thread.online.UserOnlineHeartThread;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;

/**
 * @ClassName ThreadComponentImpl
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:20:38
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Component
public class ThreadComponentImpl implements ThreadComponent {

	/**
	 * O
	 * 
	 * 开启弹幕处理线程
	 *
	 */
	@Override
	public boolean startParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		HashSet<ThankGiftRuleSet> thankGiftRuleSets = new HashSet<>();
		// thankGiftRuleSets
		for (Iterator<ThankGiftRuleSet> iterator = centerSetConf.getThank_gift().getThankGiftRuleSets()
				.iterator(); iterator.hasNext();) {
			ThankGiftRuleSet thankGiftRuleSet = iterator.next();
			if (thankGiftRuleSet.isIs_open()) {
				thankGiftRuleSets.add(thankGiftRuleSet);
			}
		}
		if (PublicDataConf.parseMessageThread != null) {
			PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
			PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
			PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
			PublicDataConf.parseMessageThread.setThankGiftRuleSets(thankGiftRuleSets);
			return false;
		}
		PublicDataConf.parseMessageThread = new ParseMessageThread();
		PublicDataConf.parseMessageThread.FLAG = false;
		PublicDataConf.parseMessageThread.start();
		PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
		PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
		PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
		PublicDataConf.parseMessageThread.setThankGiftRuleSets(thankGiftRuleSets);
		if (PublicDataConf.parseMessageThread != null
				&& !PublicDataConf.parseMessageThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startHeartByteThread() {
		// TODO 自动生成的方法存根
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

	@Override
	public boolean startLogThread() {
		// TODO 自动生成的方法存根
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

	@Override
	public boolean startAdvertThread(CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.advertThread != null || StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.advertThread
					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
			PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
			PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
			return false;
		}
		PublicDataConf.advertThread = new AdvertThread();
		PublicDataConf.advertThread.FLAG = false;
		PublicDataConf.advertThread
				.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
		PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
		PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
		PublicDataConf.advertThread.start();
		startSendBarrageThread();
		if (PublicDataConf.advertThread != null
				&& !PublicDataConf.advertThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startAutoReplyThread(CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
		for (Iterator<AutoReplySet> iterator = centerSetConf.getReply().getAutoReplySets().iterator(); iterator
				.hasNext();) {
			AutoReplySet autoReplySet = iterator.next();
			if (autoReplySet.isIs_open()) {
				autoReplySets.add(autoReplySet);
			}
		}
		if (PublicDataConf.autoReplyThread != null || StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
			PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
			return false;
		}
		PublicDataConf.autoReplyThread = new AutoReplyThread();
		PublicDataConf.autoReplyThread.FLAG = false;
		PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
		PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
		PublicDataConf.autoReplyThread.start();
		startSendBarrageThread();
		if (PublicDataConf.autoReplyThread != null
				&& !PublicDataConf.autoReplyThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startSendBarrageThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.sendBarrageThread != null || StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			return false;
		}
		PublicDataConf.sendBarrageThread = new SendBarrageThread();
		PublicDataConf.sendBarrageThread.FLAG = false;
		PublicDataConf.sendBarrageThread.start();
		if (PublicDataConf.sendBarrageThread != null
				&& !PublicDataConf.sendBarrageThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startUserOnlineThread() {
		// TODO 自动生成的方法存根
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

	@Override
	public boolean startSmallHeartThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.smallHeartThread != null
				&& !PublicDataConf.smallHeartThread.getState().toString().equals("TERMINATED")) {
			return false;
		}
		if(null==PublicDataConf.userOnlineHeartThread) {
			return false;
		}
		PublicDataConf.SMALLHEART_ADRESS = HttpOtherData.httpPostEncsUrl();
		PublicDataConf.smallHeartThread = new SmallHeartThread();
		PublicDataConf.smallHeartThread.FLAG = false;
		PublicDataConf.smallHeartThread.start();
		if (PublicDataConf.smallHeartThread != null
				&& !PublicDataConf.smallHeartThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean startGiftShieldThread(String giftName, int time) {
		// TODO 自动生成的方法存根
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

	@Override
	public boolean startFollowShieldThread(int time) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parsethankFollowThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parsethankFollowThread.getState().toString().equals("NEW")) {
			PublicDataConf.followShieldThread = new FollowShieldThread();
			PublicDataConf.followShieldThread.FLAG = false;
			PublicDataConf.followShieldThread.setTime(time);
			PublicDataConf.followShieldThread.start();
			return true;
		}
		return false;
	}

	@Override
	public void closeUserOnlineThread() {
		// TODO 自动生成的方法存根
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

	@Override
	public void startParseThankGiftThread(ThankGiftSetConf thankGiftSetConf,
			HashSet<ThankGiftRuleSet> thankGiftRuleSets) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parsethankGiftThread == null) {
			PublicDataConf.parsethankGiftThread = new ParseThankGiftThread();
		}
		if (PublicDataConf.parsethankGiftThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parsethankGiftThread.getState().toString().equals("NEW")) {
			PublicDataConf.parsethankGiftThread = new ParseThankGiftThread();
			PublicDataConf.parsethankGiftThread.setDelaytime((long) (1000 * thankGiftSetConf.getDelaytime()));
			PublicDataConf.parsethankGiftThread.start();
			PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parsethankGiftThread.setThankGiftString(thankGiftSetConf.getThank());
			PublicDataConf.parsethankGiftThread
					.setThankGiftStatus(ParseSetStatusTools.getThankGiftStatus(thankGiftSetConf.getThank_status()));
			PublicDataConf.parsethankGiftThread.setThankGiftRuleSets(thankGiftRuleSets);
			PublicDataConf.parsethankGiftThread.setNum(thankGiftSetConf.getNum());
			PublicDataConf.parsethankGiftThread.setIs_num(thankGiftSetConf.isIs_num());
		} else {
			PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parsethankGiftThread.setThankGiftString(thankGiftSetConf.getThank());
			PublicDataConf.parsethankGiftThread
					.setThankGiftStatus(ParseSetStatusTools.getThankGiftStatus(thankGiftSetConf.getThank_status()));
			PublicDataConf.parsethankGiftThread.setThankGiftRuleSets(thankGiftRuleSets);
			PublicDataConf.parsethankGiftThread.setNum(thankGiftSetConf.getNum());
			PublicDataConf.parsethankGiftThread.setIs_num(thankGiftSetConf.isIs_num());
		}
	}

	@Override
	public void startParseThankFollowThread(ThankFollowSetConf thankFollowSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parsethankFollowThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parsethankFollowThread.getState().toString().equals("NEW")) {
			PublicDataConf.parsethankFollowThread = new ParseThankFollowThread();
			PublicDataConf.parsethankFollowThread.setDelaytime((long) (1000 * thankFollowSetConf.getDelaytime()));
			PublicDataConf.parsethankFollowThread.start();
			PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parsethankFollowThread.setThankFollowString(thankFollowSetConf.getFollows());
			PublicDataConf.parsethankFollowThread.setNum(thankFollowSetConf.getNum());
		} else {
			PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parsethankFollowThread.setThankFollowString(thankFollowSetConf.getFollows());
			PublicDataConf.parsethankFollowThread.setNum(thankFollowSetConf.getNum());
		}
	}

	@Override
	public void setParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parseMessageThread != null) {
			HashSet<ThankGiftRuleSet> thankGiftRuleSets = new HashSet<>();
			// thankGiftRuleSets
			for (Iterator<ThankGiftRuleSet> iterator = centerSetConf.getThank_gift().getThankGiftRuleSets()
					.iterator(); iterator.hasNext();) {
				ThankGiftRuleSet thankGiftRuleSet = iterator.next();
				if (thankGiftRuleSet.isIs_open()) {
					thankGiftRuleSets.add(thankGiftRuleSet);
				}
			}
			PublicDataConf.parseMessageThread.setMessageControlMap(messageControlMap);
			PublicDataConf.parseMessageThread.setThankGiftSetConf(centerSetConf.getThank_gift());
			PublicDataConf.parseMessageThread.setThankFollowSetConf(centerSetConf.getFollow());
			PublicDataConf.parseMessageThread.setThankGiftRuleSets(thankGiftRuleSets);
		}
	}

	@Override
	public void setAdvertThread(CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.advertThread != null) {
			PublicDataConf.advertThread
					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
			PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
			PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
		}
	}

	@Override
	public void setAutoReplyThread(CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.autoReplyThread != null) {
			HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
			for (Iterator<AutoReplySet> iterator = centerSetConf.getReply().getAutoReplySets().iterator(); iterator
					.hasNext();) {
				AutoReplySet autoReplySet = iterator.next();
				if (autoReplySet.isIs_open()) {
					autoReplySets.add(autoReplySet);
				}
			}
			PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
			PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
		}
	}

	@Override
	public void closeParseMessageThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parseMessageThread != null) {
			PublicDataConf.parseMessageThread.FLAG = true;
			PublicDataConf.parseMessageThread.interrupt();
			PublicDataConf.parseMessageThread = null;
		}
	}

	@Override
	public void closeHeartByteThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.heartByteThread != null) {
			PublicDataConf.heartByteThread.HFLAG = true;
			PublicDataConf.heartByteThread.interrupt();
			PublicDataConf.heartByteThread = null;
		}
	}

	@Override
	public void closeLogThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.logThread != null) {
			PublicDataConf.logThread.FLAG = true;
			PublicDataConf.logThread.interrupt();
			PublicDataConf.logThread = null;
		}
	}

	@Override
	public void closeAdvertThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.advertThread != null) {
			PublicDataConf.advertThread.FLAG = true;
			PublicDataConf.advertThread.interrupt();
			PublicDataConf.advertThread = null;
		}
		if (PublicDataConf.ROOMID != null) {
			if (!PublicDataConf.centerSetConf.getFollow().isIs_open()
					&& !PublicDataConf.centerSetConf.getThank_gift().isIs_open()
					&& null == PublicDataConf.autoReplyThread) {
				closeSendBarrageThread();
			}
		} else {
			closeSendBarrageThread();
		}
	}

	@Override
	public void closeAutoReplyThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.autoReplyThread != null) {
			PublicDataConf.autoReplyThread.FLAG = true;
			PublicDataConf.autoReplyThread.interrupt();
			PublicDataConf.autoReplyThread = null;
		}
	}

	@Override
	public void closeSendBarrageThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.sendBarrageThread != null) {
			PublicDataConf.sendBarrageThread.FLAG = true;
			PublicDataConf.sendBarrageThread.interrupt();
			PublicDataConf.sendBarrageThread = null;
		}
	}

	@Override
	public void closeGiftShieldThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.giftShieldThread != null
				&& !PublicDataConf.giftShieldThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.giftShieldThread.FLAG = false;
			PublicDataConf.giftShieldThread.interrupt();
		}
	}

	@Override
	public void closeFollowShieldThread() {
		// TODO 自动生成的方法存根
		if (PublicDataConf.followShieldThread != null
				&& !PublicDataConf.followShieldThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.followShieldThread.FLAG = false;
			PublicDataConf.followShieldThread.interrupt();
		}
	}

	@Override
	public void closeSmallHeartThread() {
		// TODO 自动生成的方法存根
		if(PublicDataConf.smallHeartThread!=null) {
			PublicDataConf.smallHeartThread.FLAG=true;
			PublicDataConf.smallHeartThread.interrupt();
			PublicDataConf.smallHeartThread=null;
		}
	}

}
