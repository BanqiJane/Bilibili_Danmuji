package xyz.acproject.danmuji.conf;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.enums.AdvertStatus;
import xyz.acproject.danmuji.enums.ShieldGift;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.enums.ThankGiftStatus;

public class SetMethodCode {
	public static ShieldGift getGiftShieldStatus(short code) {
		switch (code) {
		case 0:
			return ShieldGift.OPTIONAL;
		case 1:
			return ShieldGift.SILVER;
		case 2:
			return ShieldGift.HIGH_PRICE;
		case 3:
			return ShieldGift.CUSTOM_RULE;
		default:
			return ShieldGift.OPTIONAL;
		}
	}

	public static ThankGiftStatus getThankGiftStatus(short code) {
		switch (code) {
		case 0:
			return ThankGiftStatus.one_people;
		case 1:
			return ThankGiftStatus.some_people;
		case 2:
			return ThankGiftStatus.some_peoples;
		default:
			return ThankGiftStatus.one_people;
		}
	}

	public static AdvertStatus getAdvertStatus(short code) {
		switch (code) {
		case 0:
			return AdvertStatus.deafult;
		case 1:
			return AdvertStatus.random;
		default:
			return AdvertStatus.deafult;
		}
	}

	public static ConcurrentHashMap<ShieldMessage, Boolean> getMessageConcurrentHashMap(CenterSetConf centerSetConf,
			short live_status) {
		ConcurrentHashMap<ShieldMessage, Boolean> messageConcurrentHashMap = new ConcurrentHashMap<ShieldMessage, Boolean>(
				11);
		if (centerSetConf.isIs_barrage_guardAndvip()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_guardAndvip, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_guardAndvip, false);
		}
		if (centerSetConf.isIs_barrage_manager()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_manager, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_manager, false);
		}
		if (centerSetConf.isIs_barrage_medal()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_medal, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_medal, false);
		}
		if (centerSetConf.isIs_barrage_ul()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_ul, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_ul, false);
		}
		if (centerSetConf.isIs_block()) {
			messageConcurrentHashMap.put(ShieldMessage.is_block, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_block, false);
		}
		if (centerSetConf.isIs_gift()) {
			messageConcurrentHashMap.put(ShieldMessage.is_gift, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_gift, false);
		}
		if (centerSetConf.isIs_welcome()) {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome, false);
		}
		if (centerSetConf.getThank_gift().isIs_tx_shield()) {
			messageConcurrentHashMap.put(ShieldMessage.is_giftShield, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_giftShield, false);
		}
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			if (centerSetConf.getThank_gift().isIs_guard_report()) {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_report, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_report, false);
			}
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_guard_report, false);
		}
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			if (centerSetConf.getThank_gift().isIs_live_open()) {
				if (live_status != 1) {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
				} else {
					if (centerSetConf.getThank_gift().isIs_open()) {
						messageConcurrentHashMap.put(ShieldMessage.is_giftThank, true);
					} else {
						messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
					}
				}
			} else {
				if (centerSetConf.getThank_gift().isIs_open()) {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, true);
				} else {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
				}
			}
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
		}
		return messageConcurrentHashMap;
	}

	public static void modifySet(CenterSetConf centerSetConf) {
		if (PublicDataConf.ROOMID == null) {
			return;
		}
		if (PublicDataConf.webSocketProxy == null) {
			return;
		}
		if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
			return;
		}
		HashSet<String> giftStrings = centerSetConf.getThank_gift().getGiftStrings();
		double secondst = centerSetConf.getThank_gift().getDelaytime();
		String thankGiftString = centerSetConf.getThank_gift().getThank();
		short numt = centerSetConf.getThank_gift().getNum();
		short sg = centerSetConf.getThank_gift().getShield_status();
		short st = centerSetConf.getThank_gift().getThank_status();
		String guardReport = centerSetConf.getThank_gift().getReport();
		String barrageReport = centerSetConf.getThank_gift().getReport_barrage();
		HashSet<ThankGiftRuleSet> thankGiftRuleSets = centerSetConf.getThank_gift().getThankGiftRuleSets();
		for (Iterator<ThankGiftRuleSet> iterator = thankGiftRuleSets.iterator(); iterator.hasNext();) {
			ThankGiftRuleSet thankGiftRuleSet = iterator.next();
			if (!thankGiftRuleSet.isIs_open()) {
				iterator.remove();
			}
		}
		ThreadConf.startParseMessageThread(getMessageConcurrentHashMap(centerSetConf, PublicDataConf.lIVE_STATUS),
				getGiftShieldStatus(sg), giftStrings, secondst, getThankGiftStatus(st), thankGiftString, numt,
				thankGiftRuleSets, guardReport, barrageReport);
		if (centerSetConf.isIs_log()) {
			ThreadConf.startLogThread();
		} else {
			ThreadConf.closeLogThread();
		}
		boolean is_thankFollow = centerSetConf.getFollow().isIs_open();
		String thankFollowString = centerSetConf.getFollow().getFollows();
		short numf = centerSetConf.getFollow().getNum();
		short maxnum = centerSetConf.getFollow().getMax_num();
		
		ThreadConf.startFollowThread(false, thankFollowString, numf,is_thankFollow,maxnum);
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			short as = centerSetConf.getAdvert().getStatus();
			short secondsa = centerSetConf.getAdvert().getTime();
			String advertBarrage = centerSetConf.getAdvert().getAdverts();
			if (centerSetConf.getAdvert().isIs_live_open()) {
				if (PublicDataConf.lIVE_STATUS != 1) {
					ThreadConf.closeAdvertThread();
				} else {
					if (centerSetConf.getAdvert().isIs_open()) {
						ThreadConf.startAdvertThread(getAdvertStatus(as), secondsa, advertBarrage);
					} else {
						ThreadConf.setAdvertThread(getAdvertStatus(as), secondsa, advertBarrage);
						ThreadConf.closeAdvertThread();
					}
				}
			} else {
				if (centerSetConf.getAdvert().isIs_open()) {
					ThreadConf.startAdvertThread(getAdvertStatus(as), secondsa, advertBarrage);
				} else {
					ThreadConf.setAdvertThread(getAdvertStatus(as), secondsa, advertBarrage);
					ThreadConf.closeAdvertThread();
				}
			}

			if (centerSetConf.getFollow().isIs_live_open()) {
				if (PublicDataConf.lIVE_STATUS != 1) {
					ThreadConf.startFollowThread(false, thankFollowString, numf,centerSetConf.isIs_follow(),maxnum);
				} else {
					if (centerSetConf.getFollow().isIs_open()) {
						ThreadConf.startFollowThread(true, thankFollowString, numf,centerSetConf.isIs_follow(),maxnum);
					} else {
						ThreadConf.startFollowThread(false, thankFollowString, numf,centerSetConf.isIs_follow(),maxnum);
					}
				}
			} else {
				if (centerSetConf.getFollow().isIs_open()) {
					ThreadConf.startFollowThread(true, thankFollowString, numf,centerSetConf.isIs_follow(),maxnum);
				} else {
					ThreadConf.startFollowThread(false, thankFollowString, numf,centerSetConf.isIs_follow(),maxnum);
				}
			}
			if (StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				PublicDataConf.parsefollowThread.setIsThankFollow(false);
			}
			if (centerSetConf.isIs_online()) {
				ThreadConf.startUserOnlineThread();
			} else {
				ThreadConf.closeUserOnlineThread();
			}
		} else {
			if(centerSetConf.isIs_follow()) {
			ThreadConf.startFollowThread(false, thankFollowString, numf,true,maxnum);
			}else {
				ThreadConf.closeFollowThread();
			}
			PublicDataConf.COOKIE = null;
			PublicDataConf.USER = null;
			PublicDataConf.USERCOOKIE = null;
			PublicDataConf.USERBARRAGEMESSAGE = null;
			ThreadConf.closeAdvertThread();
			if (PublicDataConf.parsefollowThread != null) {
				PublicDataConf.parsefollowThread.setIsThankFollow(false);
			}
			ThreadConf.closeUserOnlineThread();
			ThreadConf.closeGiftShieldThread();
			ThreadConf.closeSendBarrageThread();
		}
		if(!centerSetConf.isIs_follow()&&!centerSetConf.getFollow().isIs_open()) {
			ThreadConf.closeFollowThread();
		}
		if (PublicDataConf.advertThread == null && !handleFollow()
				&& !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_giftThank)) {
			ThreadConf.closeSendBarrageThread();
		} else {
			ThreadConf.startSendBarrageThread();
		}
		if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
			ThreadConf.closeHeartByteThread();
			ThreadConf.closeParseMessageThread();
			ThreadConf.closeUserOnlineThread();
			ThreadConf.closeFollowThread();
			ThreadConf.closeAdvertThread();
			ThreadConf.closeSendBarrageThread();
			ThreadConf.closeLogThread();
			ThreadConf.closeGiftShieldThread();
		}
	}
	public static boolean handleFollow() {
		if(PublicDataConf.parsefollowThread==null) {
			return false;
		}
		return PublicDataConf.parsefollowThread.getIsThankFollow();
	}
}
