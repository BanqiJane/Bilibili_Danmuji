package xyz.acproject.danmuji.thread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.SetMethodCode;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.enums.ShieldGift;
import xyz.acproject.danmuji.enums.ThankGiftStatus;
import xyz.acproject.danmuji.tools.ShieldGiftTools;

public class ParseThankGiftThread extends Thread {
//	@SuppressWarnings("unused")
//	private static Logger LOGGER = LogManager.getLogger(ParseThankGiftThread.class);
	public volatile boolean TFLAG = false;
	private Long delaytime = 3000L;
	private Long timestamp;
	private String thankGiftString = "感谢%uName%大佬%Type%的%GiftName% x%Num%";
	private ThankGiftStatus thankGiftStatus;
	private Short num = 2;
	private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
	private boolean is_num = true;

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		String thankGiftStr = null;
		Vector<Gift> gifts = null;
		StringBuilder stringBuilder = new StringBuilder(200);
		synchronized (timestamp) {
			while (!TFLAG) {
				if (TFLAG) {
					return;
				}
				if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
					return;
				}
				long nowTime = System.currentTimeMillis();
				if (nowTime - getTimestamp() < getDelaytime()) {
				} else {
					if (PublicDataConf.thankGiftConcurrentHashMap.size() > 0) {
						for (Entry<String, Vector<Gift>> entry : PublicDataConf.thankGiftConcurrentHashMap.entrySet()) {
							gifts = entry.getValue();
							for (Iterator<Gift> iterator = gifts.iterator(); iterator.hasNext();) {
								Gift gift = iterator.next();
								if (SetMethodCode.getGiftShieldStatus(PublicDataConf.centerSetConf.getThank_gift()
										.getShield_status()) == ShieldGift.CUSTOM_RULE) {
									if (ShieldGiftTools.shieldGift(gift, ShieldGift.CUSTOM_RULE, null,
											getThankGiftRuleSets()) == null) {
										iterator.remove();
									}
								}
							}
//							gifts.sort((g1,g2)->g1.getTimestamp().compareTo(g2.getTimestamp()));
//							Collections.reverse(gifts);
							// 每人(uName)每种礼物(GiftName)感谢 延迟内(delaytime)
							if (getThankGiftStatus() == ThankGiftStatus.one_people) {
								for (Iterator<Gift> iterator = gifts.iterator(); iterator.hasNext();) {
									Gift gift = iterator.next();
									thankGiftStr = getThankGiftString().replaceAll("%uName%", gift.getUname());
									thankGiftStr = thankGiftStr.replaceAll("%GiftName%", gift.getGiftName());
									thankGiftStr = thankGiftStr.replaceAll("%Num%", gift.getNum().toString());
									thankGiftStr = thankGiftStr.replaceAll("%Type%", gift.getAction());
									if (PublicDataConf.sendBarrageThread != null
											&& !PublicDataConf.sendBarrageThread.FLAG) {
										PublicDataConf.barrageString.add(thankGiftStr);
										synchronized (PublicDataConf.sendBarrageThread) {
											PublicDataConf.sendBarrageThread.notify();
										}
									}
								}
							}
							// 每人(uName)多种礼物(Gifts)感谢 最多不超过几种(num)礼物 延迟内(delaytime) end beta版
							if (getThankGiftStatus() == ThankGiftStatus.some_people) {
								for (int i = 0; i < gifts.size(); i += getNum()) {
									for (int j = i; j < i + getNum(); j++) {
										if (j >= gifts.size()) {
											break;
										}
										thankGiftStr = getThankGiftString().replaceAll("%uName%", entry.getKey());
										if (isIs_num()) {
											stringBuilder.append(gifts.get(j).getNum()).append("个")
													.append(gifts.get(j).getGiftName()).append(",");
										} else {
											stringBuilder.append(gifts.get(j).getGiftName()).append(",");
										}
									}
									stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
									thankGiftStr = thankGiftStr.replaceAll("%Gifts%", stringBuilder.toString());
									stringBuilder.delete(0, stringBuilder.length());
									if (PublicDataConf.sendBarrageThread != null
											&& !PublicDataConf.sendBarrageThread.FLAG) {
										PublicDataConf.barrageString.add(thankGiftStr);
										synchronized (PublicDataConf.sendBarrageThread) {
											PublicDataConf.sendBarrageThread.notify();
										}
									}
									thankGiftStr = getThankGiftString();
								}
								// 已删除传统没有手动选择几种的写法 即全部打印
//								thankGiftStr = getThankGiftString().replaceAll("%uName%", entry.getKey());
//								for (Iterator<Gift> iterator = gifts.iterator(); iterator.hasNext();) {
//									Gift gift = iterator.next();
//									stringBuilder.append(gift.getNum()).append("个").append(gift.getGiftName())
//											.append(",");
//								}
//								stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
//								thankGiftStr = thankGiftStr.replaceAll("%Gifts%", stringBuilder.toString());
//								if (PublicDataConf.sendBarrageThread != null
//										&& !PublicDataConf.sendBarrageThread.FLAG) {
//									PublicDataConf.barrageString.add(thankGiftStr);
//									synchronized (PublicDataConf.sendBarrageThread) {
//										PublicDataConf.sendBarrageThread.notify();
//									}
//								}
								stringBuilder.delete(0, stringBuilder.length());
							}
						}
						// 多人(uNames)多种(Gifts)礼物感谢 最多多少个人及多种(num)礼物 延迟内(delaytime) end beta版
						if (getThankGiftStatus() == ThankGiftStatus.some_peoples) {
							// 多次
							int page = (int) Math.ceil(
									(double) PublicDataConf.thankGiftConcurrentHashMap.size() / (double) getNum());
							if (getNum() > 1 && PublicDataConf.thankGiftConcurrentHashMap.size() > 1) {
								for (int i = 0; i < page; i++) {
									if (PublicDataConf.sendBarrageThread != null
											&& !PublicDataConf.sendBarrageThread.FLAG) {
										PublicDataConf.barrageString
												.add(somePeoplesHandle(PublicDataConf.thankGiftConcurrentHashMap,
														getNum(), getThankGiftString()));
										synchronized (PublicDataConf.sendBarrageThread) {
											PublicDataConf.sendBarrageThread.notify();
										}
									}
								}
							} else {
								// 单次
								for (Entry<String, Vector<Gift>> entry : PublicDataConf.thankGiftConcurrentHashMap
										.entrySet()) {
									gifts = entry.getValue();
									for (int i = 0; i < gifts.size(); i += getNum()) {
										for (int j = i; j < i + getNum(); j++) {
											if (j >= gifts.size()) {
												break;
											}
											thankGiftStr = getThankGiftString().replaceAll("%uNames%", entry.getKey());
											if (isIs_num()) {
												stringBuilder.append(gifts.get(j).getNum()).append("个")
														.append(gifts.get(j).getGiftName()).append(",");
											} else {
												stringBuilder.append(gifts.get(j).getGiftName()).append(",");
											}
										}
										stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
										thankGiftStr = thankGiftStr.replaceAll("%Gifts%", stringBuilder.toString());
										stringBuilder.delete(0, stringBuilder.length());
										if (PublicDataConf.sendBarrageThread != null
												&& !PublicDataConf.sendBarrageThread.FLAG) {
											PublicDataConf.barrageString.add(thankGiftStr);
											synchronized (PublicDataConf.sendBarrageThread) {
												PublicDataConf.sendBarrageThread.notify();
											}
										}
										thankGiftStr = getThankGiftString();
									}
									stringBuilder.delete(0, stringBuilder.length());
								}
							}
						}
					}
//					for (Iterator<Entry<String, Vector<Gift>>> iterator = PublicDataConf.thankGiftConcurrentHashMap.entrySet()
//							.iterator(); iterator.hasNext();) {
//						iterator.remove();
//					}
					PublicDataConf.thankGiftConcurrentHashMap.clear();
					break;
				}
			}
		}
	}

	private String somePeoplesHandle(ConcurrentHashMap<String, Vector<Gift>> hashMap, int max, String giftString) {
		int i = 1;
		StringBuilder stringBuilderName = new StringBuilder(150);
		StringBuilder stringBuilderGifts = new StringBuilder(200);
		for (Iterator<Entry<String, Vector<Gift>>> iterator = hashMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, Vector<Gift>> entryMap = iterator.next();
			stringBuilderName.append(entryMap.getKey()).append(",");
			for (Gift gift : entryMap.getValue()) {
				if(isIs_num()) {
				stringBuilderGifts.append(gift.getNum()).append("个").append(gift.getGiftName()).append(",");
				}else {
					stringBuilderGifts.append(gift.getGiftName()).append(",");
				}
			}
			i++;
			iterator.remove();
			if (i > max) {
				break;
			}
		}
		stringBuilderGifts.delete(stringBuilderGifts.length() - 1, stringBuilderGifts.length());
		stringBuilderName.delete(stringBuilderName.length() - 1, stringBuilderName.length());
		giftString = giftString.replaceAll("%uNames%", stringBuilderName.toString());
		giftString = giftString.replaceAll("%Gifts%", stringBuilderGifts.toString());
		return giftString;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(Long delaytime) {
		this.delaytime = delaytime;
	}

	public String getThankGiftString() {
		return thankGiftString;
	}

	public void setThankGiftString(String thankGiftString) {
		this.thankGiftString = thankGiftString;
	}

	public ThankGiftStatus getThankGiftStatus() {
		return thankGiftStatus;
	}

	public void setThankGiftStatus(ThankGiftStatus thankGiftStatus) {
		this.thankGiftStatus = thankGiftStatus;
	}

	public Short getNum() {
		return num;
	}

	public void setNum(Short num) {
		this.num = num;
	}

	public HashSet<ThankGiftRuleSet> getThankGiftRuleSets() {
		return thankGiftRuleSets;
	}

	public void setThankGiftRuleSets(HashSet<ThankGiftRuleSet> thankGiftRuleSets) {
		this.thankGiftRuleSets = thankGiftRuleSets;
	}

	public boolean isIs_num() {
		return is_num;
	}

	public void setIs_num(boolean is_num) {
		this.is_num = is_num;
	}

}
