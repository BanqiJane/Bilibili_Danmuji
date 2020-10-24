package xyz.acproject.danmuji.component;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.conf.set.ThankGiftSetConf;
import xyz.acproject.danmuji.enums.ShieldMessage;

/**
 * @ClassName ThreadComponent
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:20:47
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public interface ThreadComponent {
	// 开启处理弹幕包线程 core
	boolean startParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf);

	// 开启心跳线程 core
	boolean startHeartByteThread();

	// 开启日志线程
	boolean startLogThread();

	// 开启公告线程 need login
	boolean startAdvertThread(CenterSetConf centerSetConf);

	// 开启自动回复线程 need login
	boolean startAutoReplyThread(CenterSetConf centerSetConf);

	// 开启发送弹幕线程 need login
	boolean startSendBarrageThread();

	// 开启用户在线线程 need login
	boolean startUserOnlineThread();
	
	// 开启用户小心心线程
	boolean startSmallHeartThread();

	// 开启礼物屏蔽线程
	boolean startGiftShieldThread(String giftName, int time);

	// 开启关注屏蔽线程
	boolean startFollowShieldThread(int time);

	// 开启public的礼物感谢线程
	void startParseThankGiftThread(ThankGiftSetConf thankGiftSetConf, HashSet<ThankGiftRuleSet> thankGiftRuleSets);

	// 开启public的关注感谢线程
	void startParseThankFollowThread(ThankFollowSetConf thankFollowSetConf);

	// 设置处理弹幕包线程
	void setParseMessageThread(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap,
			CenterSetConf centerSetConf);

	// 设置公告线程 need login
	void setAdvertThread(CenterSetConf centerSetConf);

	// 设置自动回复线程 need login
	void setAutoReplyThread(CenterSetConf centerSetConf);

	// 关闭处理弹幕包线程 core
	void closeParseMessageThread();

	// 关闭心跳线程 core
	void closeHeartByteThread();
	
	// 关闭用户心跳线程
	void closeSmallHeartThread();

	// 关闭日志线程
	void closeLogThread();

	void closeAdvertThread();

	void closeAutoReplyThread();

	void closeSendBarrageThread();

	void closeGiftShieldThread();

	void closeFollowShieldThread();

	// 关闭用户在线线程
	void closeUserOnlineThread();

}
