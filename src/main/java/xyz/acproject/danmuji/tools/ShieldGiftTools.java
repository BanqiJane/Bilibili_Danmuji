package xyz.acproject.danmuji.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.enums.ListGiftShieldStatus;
import xyz.acproject.danmuji.enums.ListPeopleShieldStatus;
import xyz.acproject.danmuji.enums.ShieldGift;
import xyz.acproject.danmuji.thread.core.ParseMessageThread;

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
	private final static Logger LOGGER = LogManager.getLogger(ShieldGiftTools.class);

	public static Gift shieldGift(Gift gift,
								  ListGiftShieldStatus listGiftShieldStatus,
								  ListPeopleShieldStatus listPeopleShieldStatus,ShieldGift shieldGift,
								  Set<String> giftStrings,
								  Set<ThankGiftRuleSet> thankGiftRuleSets) {
		if (gift == null) {
			return null;
		}
		//先人员
		switch (listPeopleShieldStatus) {
//			case ALL:
//				break;
			case MEDAL:
				if(PublicDataConf.MEDALINFOANCHOR!=null){
					if(StringUtils.isBlank(PublicDataConf.MEDALINFOANCHOR.getMedal_name())){
						break;
					}
					//舰长的这里是空的
					if(gift.getMedal_info()==null){
						break;
					}
					if(!PublicDataConf.MEDALINFOANCHOR.getMedal_name().equals(gift.getMedal_info().getMedal_name())){
/*						LOGGER.info("礼物感谢姬人员屏蔽[勋章模式]:{}", gift.getMedal_info().getMedal_name());*/
						return null;
					}
				}
				break;
			case GUARD:
				if (gift.getMedal_info().getGuard_level()<=0) {
//					LOGGER.info("礼物感谢姬人员屏蔽[舰长模式]:{}", ParseIndentityTools.parseGuard(gift.getMedal_info().getGuard_level()));
					return null;
				}
			default:
				break;
		}
		//再礼物
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
			//这里是白黑名单处理
			switch (listGiftShieldStatus) {
				case BLACK:
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
				case WHITE:
					if (giftStrings != null && giftStrings.size() > 0) {
						//仅仅是模糊
						if (giftStrings.contains(gift.getGiftName())) {
							return gift;
						} else {
							return null;
						}
					} else {
						return gift;
					}
				default:
					break;
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
