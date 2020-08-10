package xyz.acproject.danmuji.conf.set;

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
public class ThankGiftSetConf implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418947096472064467L;
	
	//是否开启
	private boolean is_open= false;
	//是否直播有效
	private boolean is_live_open =false;
	//是否开启屏蔽天选礼物
	private boolean is_tx_shield=false;
	//礼物屏蔽模式 0 1 2 3
	private short shield_status =0;
	//自定义礼物屏蔽  0 
	private HashSet<String> giftStrings;
	//自定义感谢屏蔽规则 3
	private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
	//感谢模式 0 1 2
	private short thank_status = 0;
	//最多多种 仅在感谢模式1 2下有用
	private short num = 2;
	//发送感谢语延迟时间
	private double delaytime = 3;
	//感谢语 默认感谢模式0下的
	private String thank ="感谢%uName%大佬%Type%的%GiftName% x%Num%~";
	//是否舰长私信
	private boolean is_guard_report =false;
	//是否开启本地存储舰长
	private boolean is_guard_local=false;
	//舰长私信语
	private String report;
	//舰长私信成功后发送弹幕
	private String report_barrage;
	//是否开启感谢数量显示
	private boolean is_num=true;
	public ThankGiftSetConf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	

	public ThankGiftSetConf(boolean is_open, boolean is_live_open, boolean is_tx_shield, short shield_status,
			HashSet<String> giftStrings, HashSet<ThankGiftRuleSet> thankGiftRuleSets, short thank_status, short num,
			double delaytime, String thank, boolean is_guard_report, boolean is_guard_local, String report,
			String report_barrage, boolean is_num) {
		super();
		this.is_open = is_open;
		this.is_live_open = is_live_open;
		this.is_tx_shield = is_tx_shield;
		this.shield_status = shield_status;
		this.giftStrings = giftStrings;
		this.thankGiftRuleSets = thankGiftRuleSets;
		this.thank_status = thank_status;
		this.num = num;
		this.delaytime = delaytime;
		this.thank = thank;
		this.is_guard_report = is_guard_report;
		this.is_guard_local = is_guard_local;
		this.report = report;
		this.report_barrage = report_barrage;
		this.is_num = is_num;
	}

	public boolean isIs_open() {
		return is_open;
	}
	public void setIs_open(boolean is_open) {
		this.is_open = is_open;
	}
	public boolean isIs_live_open() {
		return is_live_open;
	}
	public void setIs_live_open(boolean is_live_open) {
		this.is_live_open = is_live_open;
	}
	public boolean isIs_tx_shield() {
		return is_tx_shield;
	}
	public void setIs_tx_shield(boolean is_tx_shield) {
		this.is_tx_shield = is_tx_shield;
	}
	public short getShield_status() {
		return shield_status;
	}
	public void setShield_status(short shield_status) {
		this.shield_status = shield_status;
	}
	
	
	public boolean isIs_guard_local() {
		return is_guard_local;
	}



	public void setIs_guard_local(boolean is_guard_local) {
		this.is_guard_local = is_guard_local;
	}



	public HashSet<String> getGiftStrings() {
		return giftStrings;
	}
	public void setGiftStrings(HashSet<String> giftStrings) {
		this.giftStrings = giftStrings;
	}
	public HashSet<ThankGiftRuleSet> getThankGiftRuleSets() {
		return thankGiftRuleSets;
	}
	public void setThankGiftRuleSets(HashSet<ThankGiftRuleSet> thankGiftRuleSets) {
		this.thankGiftRuleSets = thankGiftRuleSets;
	}
	public short getThank_status() {
		return thank_status;
	}
	public void setThank_status(short thank_status) {
		this.thank_status = thank_status;
	}
	public short getNum() {
		return num;
	}
	public void setNum(short num) {
		this.num = num;
	}
	public double getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(double delaytime) {
		this.delaytime = delaytime;
	}
	public String getThank() {
		return thank;
	}
	public void setThank(String thank) {
		this.thank = thank;
	}
	public boolean isIs_guard_report() {
		return is_guard_report;
	}
	public void setIs_guard_report(boolean is_guard_report) {
		this.is_guard_report = is_guard_report;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getReport_barrage() {
		return report_barrage;
	}
	public void setReport_barrage(String report_barrage) {
		this.report_barrage = report_barrage;
	}

	public boolean isIs_num() {
		return is_num;
	}
	public void setIs_num(boolean is_num) {
		this.is_num = is_num;
	}
}
