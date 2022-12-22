package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;
import java.util.HashSet;

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
public class ThankGiftSetConf implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418947096472064467L;
	
	//是否开启
	@JSONField(name = "is_open")
	private boolean is_open= false;
	//是否直播有效
	@JSONField(name = "is_live_open")
	private boolean is_live_open =false;
	//是否开启屏蔽天选礼物
	@JSONField(name = "is_tx_shield")
	private boolean is_tx_shield=false;
	//礼物屏蔽模式 0 自定义 1 免费 2 低价 3 规则
	private short shield_status =0;
	//自定义礼物屏蔽  0 
	private HashSet<String> giftStrings;
	//自定义感谢屏蔽规则 3
	private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
	//感谢模式 0 单人单种 1 单人多种 2 多人多种
	private short thank_status = 0;
	//最多多种 仅在感谢模式1 2下有用
	private short num = 2;
	//发送感谢语延迟时间
	private double delaytime = 3;
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
		this.is_open = is_open;
	}


	//方法区
	public boolean is_giftThank(){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return false;
		}
		if(is_live_open) {
			//没在直播
			if(PublicDataConf.lIVE_STATUS !=1){
				return false;
			}else{
				if(is_open) {
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(is_open) {
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
		if(is_live_open) {
			//没在直播
			if(live_status!=1){
				return false;
			}else{
				if(is_open) {
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(is_open) {
				return true;
			}else{
				return false;
			}
		}
	}
}
