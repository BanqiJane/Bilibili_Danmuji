package xyz.acproject.danmuji.conf;

import xyz.acproject.danmuji.conf.set.AdvertSetConf;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;
import xyz.acproject.danmuji.conf.set.ThankGiftSetConf;
import xyz.acproject.danmuji.utils.FastJsonUtils;

public class CenterSetConf {
	//弹幕显示舰长和老爷图标
	private boolean is_barrage_guardAndvip = true;
	//弹幕显示房管图标
	private boolean is_barrage_manager = true;
	//弹幕显示勋章图标
	private boolean is_barrage_medal = false;
	//弹幕显示用户等级图标
	private boolean is_barrage_ul = false;
	//信息是否显示房管禁言消息
	private boolean is_block = true;
	//信息是否显示礼物消息
	private boolean is_gift = true;
	//信息是否显示欢迎老爷舰长进入直播间消息
	private boolean is_welcome =true;
	//是否开启关注线程
	private boolean is_follow = true;
	//是否开启日志线程
	private boolean is_log = false;
	
//-------------------------以下设置需要用户登录后----------------------------------------
	//是否开启用户在线心跳线程
	private boolean is_online = false;
	//是否开启礼物感谢
	private ThankGiftSetConf thank_gift;
	//是否开启广告公告线程
	private AdvertSetConf advert;
	//是否开启感谢关注
	private ThankFollowSetConf follow;
	
	
	public CenterSetConf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public CenterSetConf( ThankGiftSetConf thank_gift, AdvertSetConf advert,
			ThankFollowSetConf follow) {
		super();
		this.thank_gift = thank_gift;
		this.advert = advert;
		this.follow = follow;
	}
	public CenterSetConf(boolean is_barrage_guardAndvip, boolean is_barrage_manager, boolean is_barrage_medal,
			boolean is_barrage_ul, boolean is_block, boolean is_gift, boolean is_welcome, boolean is_follow,
			boolean is_log, boolean is_online, ThankGiftSetConf thank_gift, AdvertSetConf advert,
			ThankFollowSetConf follow) {
		super();
		this.is_barrage_guardAndvip = is_barrage_guardAndvip;
		this.is_barrage_manager = is_barrage_manager;
		this.is_barrage_medal = is_barrage_medal;
		this.is_barrage_ul = is_barrage_ul;
		this.is_block = is_block;
		this.is_gift = is_gift;
		this.is_welcome = is_welcome;
		this.is_follow = is_follow;
		this.is_log = is_log;
		this.is_online = is_online;
		this.thank_gift = thank_gift;
		this.advert = advert;
		this.follow = follow;
	}
	public boolean isIs_barrage_guardAndvip() {
		return is_barrage_guardAndvip;
	}
	public void setIs_barrage_guardAndvip(boolean is_barrage_guardAndvip) {
		this.is_barrage_guardAndvip = is_barrage_guardAndvip;
	}
	public boolean isIs_barrage_manager() {
		return is_barrage_manager;
	}
	public void setIs_barrage_manager(boolean is_barrage_manager) {
		this.is_barrage_manager = is_barrage_manager;
	}
	public boolean isIs_barrage_medal() {
		return is_barrage_medal;
	}
	public void setIs_barrage_medal(boolean is_barrage_medal) {
		this.is_barrage_medal = is_barrage_medal;
	}
	public boolean isIs_barrage_ul() {
		return is_barrage_ul;
	}
	public void setIs_barrage_ul(boolean is_barrage_ul) {
		this.is_barrage_ul = is_barrage_ul;
	}
	public boolean isIs_block() {
		return is_block;
	}
	public void setIs_block(boolean is_block) {
		this.is_block = is_block;
	}
	public boolean isIs_gift() {
		return is_gift;
	}
	public void setIs_gift(boolean is_gift) {
		this.is_gift = is_gift;
	}
	public boolean isIs_welcome() {
		return is_welcome;
	}
	public void setIs_welcome(boolean is_welcome) {
		this.is_welcome = is_welcome;
	}
	public boolean isIs_follow() {
		return is_follow;
	}
	public void setIs_follow(boolean is_follow) {
		this.is_follow = is_follow;
	}
	public boolean isIs_log() {
		return is_log;
	}
	public void setIs_log(boolean is_log) {
		this.is_log = is_log;
	}
	public boolean isIs_online() {
		return is_online;
	}
	public void setIs_online(boolean is_online) {
		this.is_online = is_online;
	}
	public ThankGiftSetConf getThank_gift() {
		return thank_gift;
	}
	public void setThank_gift(ThankGiftSetConf thank_gift) {
		this.thank_gift = thank_gift;
	}
	public AdvertSetConf getAdvert() {
		return advert;
	}
	public void setAdvert(AdvertSetConf advert) {
		this.advert = advert;
	}
	public ThankFollowSetConf getFollow() {
		return follow;
	}
	public void setFollow(ThankFollowSetConf follow) {
		this.follow = follow;
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new CenterSetConf(is_barrage_guardAndvip, is_barrage_manager, is_barrage_medal, is_barrage_ul, is_block, is_gift, is_welcome, is_follow, is_log, is_online, thank_gift, advert, follow));
	}
}
