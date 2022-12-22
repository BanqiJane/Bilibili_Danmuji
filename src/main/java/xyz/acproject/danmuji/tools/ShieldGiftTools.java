package xyz.acproject.danmuji.tools;

import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.enums.ShieldGift;

import java.util.Set;

/**
 * @ClassName ShieldGiftTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:31:27
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ShieldGiftTools {

	public static Gift shieldGift(Gift gift, ShieldGift shieldGift, Set<String> giftStrings,
			Set<ThankGiftRuleSet> thankGiftRuleSets) {
		if (gift == null) {
			return null;
		}
		switch (shieldGift) {
		// 2
		case HIGH_PRICE:
			//适配6.11破站更新金瓜子为电池  叔叔真有你的
			if (gift.getCoin_type()==1&&gift.getPrice() >= 28000) {
				return gift;
			} else {
				return null;
			}
			// 0
		case OPTIONAL:
			if (giftStrings != null && giftStrings.size() > 0) {
				//仅仅是模糊
				if (giftStrings.contains(gift.getGiftName())) {
					return null;
				} else {
					return gift;
				}
			} else {
				return gift;
			}
			// 1
		case SILVER:
			if (gift.getCoin_type()==1) {
				return gift;
			} else {
				return null;
			}
			// 3
		case CUSTOM_RULE:
			// 读取配置文件s
			if (thankGiftRuleSets != null && thankGiftRuleSets.size() > 0 && !thankGiftRuleSets.isEmpty()) {
				for (ThankGiftRuleSet thankGiftRuleSet : thankGiftRuleSets) {
					if (thankGiftRuleSet.getGift_name().trim().equals(gift.getGiftName().trim())) {
						if (thankGiftRuleSet.is_open()) {
							if (thankGiftRuleSet.getStatus() == 0) {
								if (gift.getNum() >= thankGiftRuleSet.getNum()) {
									return gift;
								} else {
									return null;
								}
							} else if (thankGiftRuleSet.getStatus() == 1) {
								if (gift.getTotal_coin()>= (thankGiftRuleSet.getNum()*100) ) {
									return gift;
								} else {
									return null;
								}
							} else {
								return gift;
							}
						} else {
							return gift;
						}
					}
				}
			} else {
				return gift;
			}
			return gift;
		default:
			break;
		}

		return null;
	}
}
