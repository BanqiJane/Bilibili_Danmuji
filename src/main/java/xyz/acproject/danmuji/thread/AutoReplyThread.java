package xyz.acproject.danmuji.thread;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.AutoReplySet;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.http.HttpUserData;
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
								if (autoReply.getBarrage().contains(keyword)) {
									kNum++;
								}
							}
							if (kNum == kSize) {
								if (!StringUtils.isEmpty(autoReplySet.getReply())) {
									// 替换%NAME%参数
									if (!autoReplySet.getReply().equals("%NAME%")) {
										replyString = autoReplySet.getReply().replaceAll("%NAME%", autoReply.getName());
									} else {
										replyString = autoReply.getName();
									}
									// 替换%FANS%
									if (!replyString.equals("%FANS%")) {
										replyString = replyString.replaceAll("%FANS%",
												String.valueOf(PublicDataConf.FANSNUM));
									} else {
										replyString = String.valueOf(PublicDataConf.FANSNUM);
									}
									// 替换%TIME%
									if (!replyString.equals("%TIME%")) {
										replyString = replyString.replaceAll("%TIME%",
												JodaTimeUtils.format(System.currentTimeMillis()));
									} else {
										replyString =JodaTimeUtils.format(System.currentTimeMillis());
									}
									// 替换%BLOCK%参数 和 {{time}}时间参数
									if (replyString.contains("%BLOCK%")) {
										replyString = replyString.replaceAll("%BLOCK%", "");
										if (replyString.contains("{{") && replyString.contains("}}")) {
											hourString = replyString.substring(replyString.indexOf("{{") + 2,
													replyString.indexOf("}}"));
											if (hourString.matches("[0-9]+")) {
												if (hour <= 720 && hour > 0) {
													hour = Short.parseShort(hourString);
												}
											}
											hourReplace = replyString.substring(replyString.indexOf("{{"),
													replyString.indexOf("}}") + 2);
											if (!replyString.equals(hourReplace)) {
												replyString = replyString.replace(hourReplace, "");
											} else {
												replyString = "";
											}
										}
										if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
											try {
												HttpUserData.httpPostAddBlock(autoReply.getUid(), hour);
											} catch (Exception e) {
												// TODO: handle exception
											}
										}
									}
//									if(replyString.contains("%%")) {
//										
//									}
									if (!StringUtils.isEmpty(replyString)) {
										if (PublicDataConf.sendBarrageThread != null
												&& !PublicDataConf.sendBarrageThread.FLAG) {
											PublicDataConf.barrageString.add(replyString);
											is_send = true;
											synchronized (PublicDataConf.sendBarrageThread) {
												PublicDataConf.sendBarrageThread.notify();
											}
										}
									}
									break;
								}
							}
						}
					} else {
						kSize = autoReplySet.getKeywords().size();
						kNum = 0;
						is_shield = false;
						for (String keyword : autoReplySet.getKeywords()) {
							if (autoReply.getBarrage().contains(keyword)) {
								kNum++;
							}
						}
						if (kNum == kSize) {
							if (!StringUtils.isEmpty(autoReplySet.getReply())) {
								// 替换%NAME%参数
								if (!autoReplySet.getReply().equals("%NAME%")) {
									replyString = autoReplySet.getReply().replaceAll("%NAME%", autoReply.getName());
								} else {
									replyString = autoReply.getName();
								}
								// 替换%FANS%
								if (!replyString.equals("%FANS%")) {
									replyString = replyString.replaceAll("%FANS%",
											String.valueOf(PublicDataConf.FANSNUM));
								} else {
									replyString = String.valueOf(PublicDataConf.FANSNUM);
								}
								// 替换%TIME%
								if (!replyString.equals("%TIME%")) {
									replyString = replyString.replaceAll("%TIME%",
											JodaTimeUtils.format(System.currentTimeMillis()));
								} else {
									replyString =JodaTimeUtils.format(System.currentTimeMillis());
								}
								// 替换%BLOCK%参数 和 {{time}}时间参数
								if (replyString.contains("%BLOCK%")) {
									replyString = replyString.replaceAll("%BLOCK%", "");
									if (replyString.contains("{{") && replyString.contains("}}")) {
										hourString = replyString.substring(replyString.indexOf("{{") + 2,
												replyString.indexOf("}}"));
										if (hourString.matches("[0-9]+")) {
											if (hour <= 720 && hour > 0) {
												hour = Short.parseShort(hourString);
											}
										}
										hourReplace = replyString.substring(replyString.indexOf("{{"),
												replyString.indexOf("}}") + 2);
										//bug 标记
										System.out.println(hourReplace);
										if (!replyString.equals(hourReplace)) {
											replyString = replyString.replace(hourReplace, "");
										} else {
											replyString = "";
										}
									}
									if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
										try {
											HttpUserData.httpPostAddBlock(autoReply.getUid(), hour);
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
								}
								if (!StringUtils.isEmpty(replyString)) {
									if (PublicDataConf.sendBarrageThread != null
											&& !PublicDataConf.sendBarrageThread.FLAG) {
										PublicDataConf.barrageString.add(replyString);
										is_send = true;
										synchronized (PublicDataConf.sendBarrageThread) {
											PublicDataConf.sendBarrageThread.notify();
										}
									}
								}
								break;
							}
						}
					}
				}
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
