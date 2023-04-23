package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.base.ThankLiveSetConf;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @ClassName ThankGiftSetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:24
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThankGiftSetConf extends ThankLiveSetConf implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418947096472064467L;
	//礼物屏蔽模式 0 自定义 1 免费 2 低价 3 规则
	private short shield_status =0;

	//礼物名单限制模式 0黑名单 1白名单  默认黑
	@JSONField(name = "list_gift_shield_status")
	private short list_gift_shield_status = 0;
	//人员感谢过滤 0全部 1仅勋章 2仅舰长
	@JSONField(name = "list_people_shield_status")
	private short list_people_shield_status = 0;
	//自定义礼物屏蔽  0
	private HashSet<String> giftStrings;
	//自定义感谢屏蔽规则 3
	private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
	//感谢模式 0 单人单种 1 单人多种 2 多人多种
	private short thank_status = 0;
	//感谢语 默认感谢模式0下的
	private String thank ="感谢%uName%大佬%Type%的%GiftName% x%Num%~";
	//是否舰长私信
	@JSONField(name = "is_guard_report")
	private boolean is_guard_report =false;
	//是否开启本地存储舰长
	@JSONField(name = "is_guard_local")
	private boolean is_guard_local=false;
	//是否开启礼物码模式
	@JSONField(name = "is_gift_code")
	private boolean is_gift_code = false;
	//舰长私信语
	private String report;
	//舰长私信成功后发送弹幕
	private String report_barrage;
	//礼物码HashSet
	private HashSet<String> codeStrings;
	//是否开启感谢数量显示
	@JSONField(name = "is_num")
	private boolean is_num=true;
	public ThankGiftSetConf(boolean is_open) {
		super();
		super.set_open(is_open);
	}


	//方法区
	public boolean is_giftThank(){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		if(is_live_open()) {
			//没在直播
			if(PublicDataConf.lIVE_STATUS !=1){
				return false;
			}else{
				if(is_open()) {
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(is_open()) {
				return true;
			}else{
				return false;
			}
		}
	}

	public boolean is_giftThank(short live_status){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		if(is_live_open()) {
			//没在直播
			if(live_status!=1){
				return false;
			}else{
				if(is_open()) {
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(is_open()) {
				return true;
			}else{
				return false;
			}
		}
	}


	public HashSet<ThankGiftRuleSet> getThankGiftRuleSets() {
		if(thankGiftRuleSets!=null) {
			return thankGiftRuleSets.stream().sorted(Comparator.comparing(ThankGiftRuleSet::getGift_name)).collect(Collectors.toCollection(LinkedHashSet::new));
		}
		return new HashSet<>();
	}

	public HashSet<String> getCodeStrings() {
		if(codeStrings!=null) {
			return codeStrings.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
		}
		return  new HashSet<>();
	}
}
