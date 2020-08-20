package xyz.acproject.danmuji.thread;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.AutoReplySet;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

/**
 * @ClassName AutoReplyThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:16:13
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class AutoReplyThread extends Thread {
	public volatile boolean FLAG = false;
	private short time = 3;
	private HashSet<AutoReplySet> autoReplySets;

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		int kSize = 0;
		int kNum = 0;
		String replyString = null;
		boolean is_shield = false;
		boolean is_send = false;
		String hourString = null;
		String hourReplace = null;
		String keywords[] = null;
		short hour = 1;
		while (!FLAG) {
			if (FLAG) {
				return;
			}
			if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
				return;
			}
			if (null != PublicDataConf.replys && !PublicDataConf.replys.isEmpty()
					&& !StringUtils.isEmpty(PublicDataConf.replys.get(0).getBarrage())) {
				AutoReply autoReply = PublicDataConf.replys.get(0);
				for (AutoReplySet autoReplySet : getAutoReplySets()) {
					if (null != autoReplySet.getShields() && !autoReplySet.getShields().isEmpty()
							&& autoReplySet.getShields().size() > 0) {
						kSize = autoReplySet.getKeywords().size();
						kNum = 0;
						is_shield = false;
						for (String shield : autoReplySet.getShields()) {
							if (autoReply.getBarrage().contains(shield)) {
								is_shield = true;
								break;
							}
						}
						if (!is_shield) {
							for (String keyword : autoReplySet.getKeywords()) {
								if (StringUtils.indexOf(keyword, "||") != -1) {
									keywords = StringUtils.split(keyword, "||");
									for (String k : keywords) {
										if (autoReply.getBarrage().contains(k)) {
											kNum++;
											break;
										}
									}
								} else {
									if (autoReply.getBarrage().contains(keyword)) {
										kNum++;
									}
								}
							}
							if (kNum == kSize) {
								if (!StringUtils.isEmpty(autoReplySet.getReply())) {
									handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
											is_send);
									break;
								}
							}
						}
					} else {
						kSize = autoReplySet.getKeywords().size();
						kNum = 0;
						is_shield = false;
						// 精确匹配
						if (autoReplySet.getKeywords().size() < 2 && autoReplySet.isIs_accurate()) {
							for (String keyword : autoReplySet.getKeywords()) {
								if (StringUtils.indexOf(keyword, "||") != -1) {
									keywords = StringUtils.split(keyword, "||");
									for (String k : keywords) {
										if (autoReply.getBarrage().equals(k)) {
											// do something
											handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
													is_send);
											break;
										}
									}
								} else {
									if (autoReply.getBarrage().equals(keyword)) {
										// do something
										handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
												is_send);
									}
								}
							}
						} else {
							for (String keyword : autoReplySet.getKeywords()) {
								if (StringUtils.indexOf(keyword, "||") != -1) {
									keywords = StringUtils.split(keyword, "||");
									for (String k : keywords) {
										if (autoReply.getBarrage().contains(k)) {
											kNum++;
											break;
										}
									}
								} else {
									if (autoReply.getBarrage().contains(keyword)) {
										kNum++;
									}
								}
							}
							if (kNum == kSize) {
								if (!StringUtils.isEmpty(autoReplySet.getReply())) {
									handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
											is_send);
									break;
								}
							}
						}
					}
				}
				keywords = null;
				kSize = 0;
				kNum = 0;
				is_shield = false;
				replyString = null;
				hourString = null;
				hourReplace = null;
				hour = 1;
				PublicDataConf.replys.remove(0);
				if (is_send) {
					try {
						Thread.sleep(getTime() * 1000);
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
//					e.printStackTrace();
					}
				}
				is_send = false;
			} else {
				synchronized (PublicDataConf.autoReplyThread) {
					try {
						PublicDataConf.autoReplyThread.wait();
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
//						e.printStackTrace();
					}
				}
			}
		}
	}

	private synchronized void handle(AutoReplySet autoReplySet, String replyString, AutoReply autoReply,
			String hourString, short hour, String hourReplace, boolean is_send) {
		// 替换%NAME%参数
		if (!autoReplySet.getReply().equals("%NAME%")) {
			replyString = StringUtils.replace(autoReplySet.getReply(), "%NAME%", autoReply.getName());
		} else {
			replyString = autoReply.getName();
		}
		// 替换%FANS%
		if (!replyString.equals("%FANS%")) {
			replyString = StringUtils.replace(replyString, "%FANS%", String.valueOf(PublicDataConf.FANSNUM));
		} else {
			replyString = String.valueOf(PublicDataConf.FANSNUM);
		}
		// 替换%TIME%
		if (!replyString.equals("%TIME%")) {
			replyString = StringUtils.replace(replyString, "%TIME%", JodaTimeUtils.format(System.currentTimeMillis()));
		} else {
			replyString = JodaTimeUtils.format(System.currentTimeMillis());
		}
		// 替换%LIVETIME%
		if (!replyString.equals("%LIVETIME%")) {
			if (PublicDataConf.lIVE_STATUS == 1) {
				replyString = StringUtils.replace(replyString, "%LIVETIME%",
						CurrencyTools.getGapTime(System.currentTimeMillis()
								- HttpRoomData.httpGetRoomInit(PublicDataConf.ROOMID).getLive_time() * 1000));
			} else {
				replyString = StringUtils.replace(replyString, "%LIVETIME%", "0");
			}
		} else {
			if (PublicDataConf.lIVE_STATUS == 1) {
				replyString = CurrencyTools.getGapTime(System.currentTimeMillis()
						- HttpRoomData.httpGetRoomInit(PublicDataConf.ROOMID).getLive_time() * 1000);
			} else {
				replyString = "0";
			}
		}
		// 替换%HOT%
		if (!replyString.equals("%HOT%")) {
			replyString = StringUtils.replace(replyString, "%HOT%", PublicDataConf.ROOM_POPULARITY.toString());
		} else {
			replyString = PublicDataConf.ROOM_POPULARITY.toString();
		}
		// 替换%BLOCK%参数 和 {{time}}时间参数
		if (replyString.contains("%BLOCK%")) {
			replyString = StringUtils.replace(replyString, "%BLOCK%", "");
			if (replyString.contains("{{") && replyString.contains("}}")) {
				hourString = replyString.substring(replyString.indexOf("{{") + 2, replyString.indexOf("}}"));
				if (hourString.matches("[0-9]+")) {
					if (hour <= 720 && hour > 0) {
						hour = Short.parseShort(hourString);
					}
				}
				hourReplace = replyString.substring(replyString.indexOf("{{"), replyString.indexOf("}}") + 2);
				if (!replyString.equals(hourReplace)) {
					replyString = StringUtils.replace(replyString, hourReplace, "");
				} else {
					replyString = "";
				}
			}
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				try {
					if (HttpUserData.httpPostAddBlock(autoReply.getUid(), hour) != 0)
						replyString = "";
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		if (!StringUtils.isEmpty(replyString)) {
			if (PublicDataConf.sendBarrageThread != null && !PublicDataConf.sendBarrageThread.FLAG) {
				PublicDataConf.barrageString.add(replyString);
				is_send = true;
				synchronized (PublicDataConf.sendBarrageThread) {
					PublicDataConf.sendBarrageThread.notify();
				}
			}
		}
	}

	public short getTime() {
		return time;
	}

	public void setTime(short time) {
		this.time = time;
	}

	public HashSet<AutoReplySet> getAutoReplySets() {
		return autoReplySets;
	}

	public void setAutoReplySets(HashSet<AutoReplySet> autoReplySets) {
		this.autoReplySets = autoReplySets;
	}

}
