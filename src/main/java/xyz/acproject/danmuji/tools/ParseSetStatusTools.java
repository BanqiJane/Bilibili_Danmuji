package xyz.acproject.danmuji.tools;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.enums.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ParseSetStatusTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:31:23
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ParseSetStatusTools {
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

	public static ListGiftShieldStatus getListGiftShieldStatus(short code) {
		switch (code) {
		case 0:
			return ListGiftShieldStatus.BLACK;
		case 1:
			return ListGiftShieldStatus.WHITE;
		default:
			return ListGiftShieldStatus.BLACK;
		}
	}


	public static ListPeopleShieldStatus getListPeopleShieldStatus(short code) {
		switch (code) {
		case 0:
			return ListPeopleShieldStatus.ALL;
		case 1:
			return ListPeopleShieldStatus.MEDAL;
		case 2:
			return ListPeopleShieldStatus.GUARD;
		default:
			return ListPeopleShieldStatus.ALL;
		}
	}

	/**
	* 等待移除 2.4.9
	*/
	@Deprecated
	public static ConcurrentHashMap<ShieldMessage, Boolean> getMessageConcurrentHashMap(CenterSetConf centerSetConf,
			short live_status) {
		ConcurrentHashMap<ShieldMessage, Boolean> messageConcurrentHashMap = new ConcurrentHashMap<ShieldMessage, Boolean>(
				18);
		if (centerSetConf.is_barrage_guard()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_guard, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_guard, false);
		}
		if(centerSetConf.is_cmd()) {
			messageConcurrentHashMap.put(ShieldMessage.is_cmd, true);
		}else {
			messageConcurrentHashMap.put(ShieldMessage.is_cmd, false);
		}
		if (centerSetConf.is_barrage_vip()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_vip, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_vip, false);
		}
		if (centerSetConf.is_barrage_manager()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_manager, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_manager, false);
		}
		if (centerSetConf.is_barrage_medal()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_medal, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_medal, false);
		}
		if (centerSetConf.is_barrage_ul()) {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_ul, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_ul, false);
		}
		if(centerSetConf.is_barrage_anchor_shield()){
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_anchor_shield,true);
		}else{
			messageConcurrentHashMap.put(ShieldMessage.is_barrage_anchor_shield,false);
		}
		if (centerSetConf.is_block()) {
			messageConcurrentHashMap.put(ShieldMessage.is_block, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_block, false);
		}
		if (centerSetConf.is_follow_dm()) {
			messageConcurrentHashMap.put(ShieldMessage.is_follow, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_follow, false);
		}
		if (centerSetConf.is_gift()) {
			messageConcurrentHashMap.put(ShieldMessage.is_gift, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_gift, false);
		}
		if (centerSetConf.is_welcome_ye()) {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome, false);
		}
		if (centerSetConf.is_welcome_all()) {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome_all, true);
		} else {
			messageConcurrentHashMap.put(ShieldMessage.is_welcome_all, false);
		}
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			//礼物码模式
			if (centerSetConf.getThank_gift().is_gift_code()) {
				messageConcurrentHashMap.put(ShieldMessage.is_giftCode, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_giftCode, false);
			}
			//天选屏蔽
			if (centerSetConf.getThank_gift().is_tx_shield()) {
				messageConcurrentHashMap.put(ShieldMessage.is_giftShield, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_giftShield, false);
			}
			if (centerSetConf.getFollow().is_tx_shield()) {
				messageConcurrentHashMap.put(ShieldMessage.is_followShield, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_followShield, false);
			}
			if (centerSetConf.getWelcome().is_tx_shield()) {
				messageConcurrentHashMap.put(ShieldMessage.is_welcomeShield, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_welcomeShield, false);
			}
			//舰长私信
			if (centerSetConf.getThank_gift().is_guard_report()) {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_report, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_report, false);
			}
			//关注
			if(centerSetConf.getFollow().is_live_open()) {
				if(live_status!=1) {
					messageConcurrentHashMap.put(ShieldMessage.is_followThank, false);
				}else {
					if (centerSetConf.getFollow().is_open()) {
						messageConcurrentHashMap.put(ShieldMessage.is_followThank, true);
					} else {
						messageConcurrentHashMap.put(ShieldMessage.is_followThank, false);
					}
				}
			}else {
				if (centerSetConf.getFollow().is_open()) {
					messageConcurrentHashMap.put(ShieldMessage.is_followThank, true);
				} else {
					messageConcurrentHashMap.put(ShieldMessage.is_followThank, false);
				}
			}

			//欢迎感谢
			if(centerSetConf.getWelcome().is_live_open()) {
				if(live_status!=1) {
					messageConcurrentHashMap.put(ShieldMessage.is_welcomeThank, false);
				}else {
					if (centerSetConf.getWelcome().is_open()) {
						messageConcurrentHashMap.put(ShieldMessage.is_welcomeThank, true);
					} else {
						messageConcurrentHashMap.put(ShieldMessage.is_welcomeThank, false);
					}
				}
			}else {
				if (centerSetConf.getWelcome().is_open()) {
					messageConcurrentHashMap.put(ShieldMessage.is_welcomeThank, true);
				} else {
					messageConcurrentHashMap.put(ShieldMessage.is_welcomeThank, false);
				}
			}
			//舰长是否保持在本地
			if (centerSetConf.getThank_gift().is_guard_local()) {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_local, true);
			} else {
				messageConcurrentHashMap.put(ShieldMessage.is_guard_local, false);
			}
			//是否感谢礼物
			if (centerSetConf.getThank_gift().is_live_open()) {
				if (live_status != 1) {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
				} else {
					if (centerSetConf.getThank_gift().is_open()) {
						messageConcurrentHashMap.put(ShieldMessage.is_giftThank, true);
					} else {
						messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
					}
				}
			} else {
				if (centerSetConf.getThank_gift().is_open()) {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, true);
				} else {
					messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
				}
			}
		}else {
			//舰长私信
			messageConcurrentHashMap.put(ShieldMessage.is_guard_report, false);
			//关注感谢
			messageConcurrentHashMap.put(ShieldMessage.is_followThank, false);
			//舰长本地
			messageConcurrentHashMap.put(ShieldMessage.is_guard_local, false);
			//礼物感谢
			messageConcurrentHashMap.put(ShieldMessage.is_giftThank, false);
		}

		return messageConcurrentHashMap;
	}

	public static CenterSetConf initCenterChildConfig(CenterSetConf centerSetConf){
		if (centerSetConf != null) {
			if (centerSetConf.getAdvert() == null) {
				centerSetConf.setAdvert(new AdvertSetConf());
			}
			if (centerSetConf.getFollow() == null) {
				centerSetConf.setFollow(new ThankFollowSetConf());
			}
			if (centerSetConf.getThank_gift() == null) {
				centerSetConf.setThank_gift(new ThankGiftSetConf());
			}
			if (centerSetConf.getReply() == null) {
				centerSetConf.setReply(new AutoReplySetConf());
			}
			if (centerSetConf.getWelcome() == null) {
				centerSetConf.setWelcome(new ThankWelcomeSetConf());
			}
			if (centerSetConf.getAuto_gift() == null) {
				centerSetConf.setAuto_gift(new AutoSendGiftConf());
			}
			if(centerSetConf.getPrivacy()==null){
				centerSetConf.setPrivacy(new PrivacySetConf());
			}
			if(centerSetConf.getBlack()==null){
				centerSetConf.setBlack(new BlackListSetConf());
			}
		}
		return centerSetConf;
	}
}
