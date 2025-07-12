package xyz.acproject.danmuji.component.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.thread.*;
import xyz.acproject.danmuji.thread.core.HeartByteThread;
import xyz.acproject.danmuji.thread.core.ParseMessageThread;
import xyz.acproject.danmuji.thread.online.HeartBeatThread;
import xyz.acproject.danmuji.thread.online.HeartBeatsThread;
import xyz.acproject.danmuji.thread.online.SmallHeartThread;
import xyz.acproject.danmuji.thread.online.UserOnlineHeartThread;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;

import java.util.HashSet;
import java.util.Iterator;

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
	
	
	
	
	
	//关闭全部线程
	public void closeAll(){
		closeHeartByteThread();
		closeUserOnlineThread();
		closeAdvertThread();
		closeSendBarrageThread();
		closeLogThread();
		closeGiftShieldThread();
		closeFollowShieldThread();
		closeWelcomeShieldThread();
		closeAutoReplyThread();
		closeSmallHeartThread();
		closeParseMessageThread();
	}


	//关闭用户相关线程
	public void closeUser(boolean close){

		closeUserOnlineThread();
		closeAdvertThread();
		closeSendBarrageThread();
		closeLogThread();
		closeGiftShieldThread();
		closeFollowShieldThread();
		closeWelcomeShieldThread();
		closeAutoReplyThread();
		closeSmallHeartThread();
		if (close) {
			closeHeartByteThread();
			closeParseMessageThread();
		}

	}

	/**
	 * O
	 * 
	 * 开启弹幕处理线程
	 *
	 */
	@Override
	public boolean startParseMessageThread(
			CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		HashSet<ThankGiftRuleSet> thankGiftRuleSets = new HashSet<>();
		// thankGiftRuleSets
		for (Iterator<ThankGiftRuleSet> iterator = centerSetConf.getThank_gift().getThankGiftRuleSets()
				.iterator(); iterator.hasNext();) {
			ThankGiftRuleSet thankGiftRuleSet = iterator.next();
			if (thankGiftRuleSet.is_open()) {
				thankGiftRuleSets.add(thankGiftRuleSet);
			}
		}
		if (PublicDataConf.parseMessageThread != null && !PublicDataConf.parseMessageThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.parseMessageThread.setCenterSetConf(centerSetConf);
			PublicDataConf.parseMessageThread.setThankGiftRuleSets(thankGiftRuleSets);
			return false;
		}
		PublicDataConf.parseMessageThread = new ParseMessageThread();
		PublicDataConf.parseMessageThread.FLAG = false;
		PublicDataConf.parseMessageThread.start();
		PublicDataConf.parseMessageThread.setCenterSetConf(centerSetConf);
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

//	@Override
//	public boolean startAdvertThread(CenterSetConf centerSetConf) {
//		// TODO 自动生成的方法存根
//		if (PublicDataConf.advertThread != null || StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
//			PublicDataConf.advertThread
//					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
//			PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
//			PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
//			return false;
//		}
//		PublicDataConf.advertThread = new AdvertThread();
//		PublicDataConf.advertThread.FLAG = false;
//		PublicDataConf.advertThread
//				.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
//		PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
//		PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
//		PublicDataConf.advertThread.start();
//		startSendBarrageThread();
//		if (PublicDataConf.advertThread != null
//				&& !PublicDataConf.advertThread.getState().toString().equals("TERMINATED")) {
//			return true;
//		}
//		return false;
//	}

	@Override
	public boolean startAdvertThread(AdvertSetConf advertSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.advertThread != null || StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.advertThread
					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(advertSetConf.getStatus()));
			PublicDataConf.advertThread.setTime(advertSetConf.getTime());
			PublicDataConf.advertThread.setTime2(advertSetConf.getTime2());
			PublicDataConf.advertThread.setAdvertBarrage(advertSetConf.getAdverts());
			return false;
		}
		PublicDataConf.advertThread = new AdvertThread();
		PublicDataConf.advertThread.FLAG = false;
		PublicDataConf.advertThread
				.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(advertSetConf.getStatus()));
		PublicDataConf.advertThread.setTime(advertSetConf.getTime());
		PublicDataConf.advertThread.setTime2(advertSetConf.getTime2());
		PublicDataConf.advertThread.setAdvertBarrage(advertSetConf.getAdverts());
		PublicDataConf.advertThread.start();
		startSendBarrageThread();
		if (PublicDataConf.advertThread != null
				&& !PublicDataConf.advertThread.getState().toString().equals("TERMINATED")) {
			return true;
		}
		return false;
	}

//	@Override
//	public boolean startAutoReplyThread(CenterSetConf centerSetConf) {
//		// TODO 自动生成的方法存根
//		HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
//		for (Iterator<AutoReplySet> iterator = centerSetConf.getReply().getAutoReplySets().iterator(); iterator
//				.hasNext();) {
//			AutoReplySet autoReplySet = iterator.next();
//			if (autoReplySet.is_open()) {
//				autoReplySets.add(autoReplySet);
//			}
//		}
//		if (PublicDataConf.autoReplyThread != null || StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
//			PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
//			PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
//			return false;
//		}
//		PublicDataConf.autoReplyThread = new AutoReplyThread();
//		PublicDataConf.autoReplyThread.FLAG = false;
//		PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
//		PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
//		PublicDataConf.autoReplyThread.start();
//		startSendBarrageThread();
//		if (PublicDataConf.autoReplyThread != null
//				&& !PublicDataConf.autoReplyThread.getState().toString().equals("TERMINATED")) {
//			return true;
//		}
//		return false;
//	}

	@Override
	public boolean startAutoReplyThread(AutoReplySetConf autoReplySetConf) {
		// TODO 自动生成的方法存根
		HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
		for (Iterator<AutoReplySet> iterator = autoReplySetConf.getAutoReplySets().iterator(); iterator
				.hasNext();) {
			AutoReplySet autoReplySet = iterator.next();
			if (autoReplySet.is_open()) {
				autoReplySets.add(autoReplySet);
			}
		}
		if (PublicDataConf.autoReplyThread != null || StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
			PublicDataConf.autoReplyThread.setTime(autoReplySetConf.getTime());
			PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
			return false;
		}
		PublicDataConf.autoReplyThread = new AutoReplyThread();
		PublicDataConf.autoReplyThread.FLAG = false;
		PublicDataConf.autoReplyThread.setTime(autoReplySetConf.getTime());
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
		if (PublicDataConf.sendBarrageThread != null || StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
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
		if(PublicDataConf.centerSetConf.getPrivacy().is_open()) {
			PublicDataConf.SMALLHEART_ADRESS = PublicDataConf.centerSetConf.getPrivacy().getSmall_heart_url();
		}else{
			PublicDataConf.SMALLHEART_ADRESS = HttpOtherData.httpPostEncsUrl();
		}
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
	public boolean startWelcomeShieldThread(int time) {
		if (PublicDataConf.parseThankWelcomeThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parseThankWelcomeThread.getState().toString().equals("NEW")) {
			PublicDataConf.welcomeShieldThread = new WelcomeShieldThread();
			PublicDataConf.welcomeShieldThread.FLAG = false;
			PublicDataConf.welcomeShieldThread.setTime(time);
			PublicDataConf.welcomeShieldThread.start();
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
			PublicDataConf.parsethankGiftThread.set_num(thankGiftSetConf.is_num());
			PublicDataConf.parsethankGiftThread.setListGiftShieldStatus(
					ParseSetStatusTools.getListGiftShieldStatus(thankGiftSetConf.getList_gift_shield_status()));
			PublicDataConf.parsethankGiftThread.setListPeopleShieldStatus(
					ParseSetStatusTools.getListPeopleShieldStatus(thankGiftSetConf.getList_people_shield_status()));
		} else {
			PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parsethankGiftThread.setThankGiftString(thankGiftSetConf.getThank());
			PublicDataConf.parsethankGiftThread
					.setThankGiftStatus(ParseSetStatusTools.getThankGiftStatus(thankGiftSetConf.getThank_status()));
			PublicDataConf.parsethankGiftThread.setThankGiftRuleSets(thankGiftRuleSets);
			PublicDataConf.parsethankGiftThread.setNum(thankGiftSetConf.getNum());
			PublicDataConf.parsethankGiftThread.set_num(thankGiftSetConf.is_num());
			PublicDataConf.parsethankGiftThread.setListGiftShieldStatus(
					ParseSetStatusTools.getListGiftShieldStatus(thankGiftSetConf.getList_gift_shield_status()));
			PublicDataConf.parsethankGiftThread.setListPeopleShieldStatus(
					ParseSetStatusTools.getListPeopleShieldStatus(thankGiftSetConf.getList_people_shield_status()));
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
	public void startParseThankWelcomeThread(ThankWelcomeSetConf thankWelcomeSetConf) {
		if (PublicDataConf.parseThankWelcomeThread.getState().toString().equals("TERMINATED")
				|| PublicDataConf.parseThankWelcomeThread.getState().toString().equals("NEW")) {
			PublicDataConf.parseThankWelcomeThread = new ParseThankWelcomeThread();
			PublicDataConf.parseThankWelcomeThread.setDelaytime((long) (1000 * thankWelcomeSetConf.getDelaytime()));
			PublicDataConf.parseThankWelcomeThread.start();
			PublicDataConf.parseThankWelcomeThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parseThankWelcomeThread.setThankWelcomeString(thankWelcomeSetConf.getWelcomes());
			PublicDataConf.parseThankWelcomeThread.setNum(thankWelcomeSetConf.getNum());
		} else {
			PublicDataConf.parseThankWelcomeThread.setTimestamp(System.currentTimeMillis());
			PublicDataConf.parseThankWelcomeThread.setThankWelcomeString(thankWelcomeSetConf.getWelcomes());
			PublicDataConf.parseThankWelcomeThread.setNum(thankWelcomeSetConf.getNum());
		}
	}

	@Override
	public void setParseMessageThread(
			CenterSetConf centerSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.parseMessageThread != null) {
			HashSet<ThankGiftRuleSet> thankGiftRuleSets = new HashSet<>();
			// thankGiftRuleSets
			for (Iterator<ThankGiftRuleSet> iterator = centerSetConf.getThank_gift().getThankGiftRuleSets()
					.iterator(); iterator.hasNext();) {
				ThankGiftRuleSet thankGiftRuleSet = iterator.next();
				if (thankGiftRuleSet.is_open()) {
					thankGiftRuleSets.add(thankGiftRuleSet);
				}
			}
			PublicDataConf.parseMessageThread.setCenterSetConf(centerSetConf);
			PublicDataConf.parseMessageThread.setThankGiftRuleSets(thankGiftRuleSets);
		}
	}

//	@Override
//	public void setAdvertThread(CenterSetConf centerSetConf) {
//		// TODO 自动生成的方法存根
//		if (PublicDataConf.advertThread != null) {
//			PublicDataConf.advertThread
//					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(centerSetConf.getAdvert().getStatus()));
//			PublicDataConf.advertThread.setTime(centerSetConf.getAdvert().getTime());
//			PublicDataConf.advertThread.setAdvertBarrage(centerSetConf.getAdvert().getAdverts());
//		}
//	}

	@Override
	public void setAdvertThread(AdvertSetConf advertSetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.advertThread != null) {
			PublicDataConf.advertThread
					.setAdvertStatus(ParseSetStatusTools.getAdvertStatus(advertSetConf.getStatus()));
			PublicDataConf.advertThread.setTime(advertSetConf.getTime());
			PublicDataConf.advertThread.setTime2(advertSetConf.getTime2());
			PublicDataConf.advertThread.setAdvertBarrage(advertSetConf.getAdverts());
		}
	}

//	@Override
//	public void setAutoReplyThread(CenterSetConf centerSetConf) {
//		// TODO 自动生成的方法存根
//		if (PublicDataConf.autoReplyThread != null) {
//			HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
//			for (Iterator<AutoReplySet> iterator = centerSetConf.getReply().getAutoReplySets().iterator(); iterator
//					.hasNext();) {
//				AutoReplySet autoReplySet = iterator.next();
//				if (autoReplySet.is_open()) {
//					autoReplySets.add(autoReplySet);
//				}
//			}
//			PublicDataConf.autoReplyThread.setTime(centerSetConf.getReply().getTime());
//			PublicDataConf.autoReplyThread.setAutoReplySets(autoReplySets);
//		}
//	}

	@Override
	public void setAutoReplyThread(AutoReplySetConf autoReplySetConf) {
		// TODO 自动生成的方法存根
		if (PublicDataConf.autoReplyThread != null) {
			HashSet<AutoReplySet> autoReplySets = new HashSet<AutoReplySet>();
			for (Iterator<AutoReplySet> iterator = autoReplySetConf.getAutoReplySets().iterator(); iterator
					.hasNext();) {
				AutoReplySet autoReplySet = iterator.next();
				if (autoReplySet.is_open()) {
					autoReplySets.add(autoReplySet);
				}
			}
			PublicDataConf.autoReplyThread.setTime(autoReplySetConf.getTime());
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
			if (!PublicDataConf.centerSetConf.getFollow().is_open()
					&& !PublicDataConf.centerSetConf.getThank_gift().is_open()
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
	public void closeWelcomeShieldThread() {
		if (PublicDataConf.welcomeShieldThread != null
				&& !PublicDataConf.welcomeShieldThread.getState().toString().equals("TERMINATED")) {
			PublicDataConf.welcomeShieldThread.FLAG = false;
			PublicDataConf.welcomeShieldThread.interrupt();
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
