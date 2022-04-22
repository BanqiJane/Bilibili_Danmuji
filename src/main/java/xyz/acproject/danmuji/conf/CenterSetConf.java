package xyz.acproject.danmuji.conf;

import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.utils.FastJsonUtils;

import java.io.Serializable;

/**
 * @author BanqiJane
 * @ClassName CenterSetConf
 * @Description TODO
 * @date 2020年8月10日 下午12:21:29
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class CenterSetConf implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1162255349476806991L;
    //弹幕显示舰长和老爷图标
    private boolean is_barrage_guard = true;
    //弹幕显示舰长和老爷图标
    private boolean is_barrage_vip = true;
    //弹幕显示房管图标
    private boolean is_barrage_manager = true;
    //弹幕显示勋章图标
    private boolean is_barrage_medal = false;
    //弹幕显示用户等级图标
    private boolean is_barrage_ul = false;
    //是否屏蔽非当前房间勋章弹幕
    private boolean is_barrage_anchor_shield = false;
    //信息是否显示房管禁言消息
    private boolean is_block = true;
    //信息是否显示礼物消息
    private boolean is_gift = true;
    //信息是否显示欢迎老爷舰长进入直播间消息
    private boolean is_welcome = true;
    //信息是否显示全部欢迎
    private boolean is_welcome_all = false;
    //是否开启关注显示
    private boolean is_follow = true;
    //是否开启日志线程
    private boolean is_log = false;
    //是否控制台打印
    private boolean is_cmd = true;
    //房间号
    private Long roomid = 0l;
    //是否自动连接
    private boolean is_auto = false;
    //window是否自动打开设置页面 默认open
    private boolean win_auto_openSet = true;

    //是否开启登录
    private boolean is_manager_login = false;
    //123
    private String manager_key = "202cb962ac59075b964b07152d234b70";
    //每次登录最大尝试次数
    private int manager_maxSize = 10;

    //-------------------------以下设置需要用户登录后----------------------------------------
    //是否开启用户在线心跳线程
    private boolean is_online = false;
    //是否开启用户小心心线程 前提是is_online为true
    private boolean is_sh = false;
    //是否开启用户自动签到
    private boolean is_dosign = false;
    //自定义的签到时间
    private String sign_time = "00:30:00";
    //是否开启礼物感谢线程对象体
    private ThankGiftSetConf thank_gift;
    //是否开启广告公告线程对象体
    private AdvertSetConf advert;
    //是否开启感谢关注线程对象体
    private ThankFollowSetConf follow;
    //是否开启自动回复线程对象体
    private AutoReplySetConf reply;
    //开启自动打卡设置对象体
    private ClockInSetConf clock_in;
    //是否开启欢迎进入直播间线程对象体
    private ThankWelcomeSetConf welcome;
    //是否开启自动送礼对象体
    private AutoSendGiftConf auto_gift;
    //是否开启隐私模式 不再调用服务器
    private PrivacySetConf privacy;


    public CenterSetConf() {
        super();
        // TODO 自动生成的构造函数存根
    }

    public CenterSetConf(ThankGiftSetConf thank_gift, AdvertSetConf advert,
                         ThankFollowSetConf follow, AutoReplySetConf reply, ClockInSetConf clock_in, ThankWelcomeSetConf welcome,AutoSendGiftConf auto_gift,PrivacySetConf privacy) {
        super();
        this.thank_gift = thank_gift;
        this.advert = advert;
        this.follow = follow;
        this.reply = reply;
        this.clock_in = clock_in;
        this.welcome = welcome;
        this.auto_gift= auto_gift;
        this.privacy = privacy;
    }


    public CenterSetConf(boolean is_barrage_guard, boolean is_barrage_vip, boolean is_barrage_manager, boolean is_barrage_medal, boolean is_barrage_ul,boolean is_barrage_anchor_shield, boolean is_block, boolean is_gift, boolean is_welcome, boolean is_welcome_all, boolean is_follow, boolean is_log, boolean is_cmd, Long roomid, boolean is_auto,boolean win_auto_openSet, boolean is_manager_login, String manager_key, int manager_maxSize, boolean is_online, boolean is_sh, boolean is_dosign, String sign_time, ThankGiftSetConf thank_gift, AdvertSetConf advert, ThankFollowSetConf follow, AutoReplySetConf reply, ClockInSetConf clock_in, ThankWelcomeSetConf welcome,AutoSendGiftConf auto_gift,PrivacySetConf privacy) {
        this.is_barrage_guard = is_barrage_guard;
        this.is_barrage_vip = is_barrage_vip;
        this.is_barrage_manager = is_barrage_manager;
        this.is_barrage_medal = is_barrage_medal;
        this.is_barrage_ul = is_barrage_ul;
        this.is_barrage_anchor_shield  = is_barrage_anchor_shield;
        this.is_block = is_block;
        this.is_gift = is_gift;
        this.is_welcome = is_welcome;
        this.is_welcome_all = is_welcome_all;
        this.is_follow = is_follow;
        this.is_log = is_log;
        this.is_cmd = is_cmd;
        this.roomid = roomid;
        this.is_auto = is_auto;
        this.win_auto_openSet = win_auto_openSet;
        this.is_manager_login = is_manager_login;
        this.manager_key = manager_key;
        this.manager_maxSize = manager_maxSize;
        this.is_online = is_online;
        this.is_sh = is_sh;
        this.is_dosign = is_dosign;
        this.sign_time = sign_time;
        this.thank_gift = thank_gift;
        this.advert = advert;
        this.follow = follow;
        this.reply = reply;
        this.clock_in = clock_in;
        this.welcome = welcome;
        this.auto_gift= auto_gift;
        this.privacy = privacy;
    }

    public boolean isIs_barrage_guard() {
        return is_barrage_guard;
    }

    public void setIs_barrage_guard(boolean is_barrage_guard) {
        this.is_barrage_guard = is_barrage_guard;
    }

    public boolean isIs_barrage_vip() {
        return is_barrage_vip;
    }

    public void setIs_barrage_vip(boolean is_barrage_vip) {
        this.is_barrage_vip = is_barrage_vip;
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

    public boolean isIs_barrage_anchor_shield() {
        return is_barrage_anchor_shield;
    }

    public void setIs_barrage_anchor_shield(boolean is_barrage_anchor_shield) {
        this.is_barrage_anchor_shield = is_barrage_anchor_shield;
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

    public boolean isIs_welcome_all() {
        return is_welcome_all;
    }

    public void setIs_welcome_all(boolean is_welcome_all) {
        this.is_welcome_all = is_welcome_all;
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

    public boolean isIs_manager_login() {
        return is_manager_login;
    }

    public void setIs_manager_login(boolean is_manager_login) {
        this.is_manager_login = is_manager_login;
    }

    public String getManager_key() {
        return manager_key;
    }

    public void setManager_key(String manager_key) {
        this.manager_key = manager_key;
    }

    public int getManager_maxSize() {
        return manager_maxSize;
    }

    public void setManager_maxSize(int manager_maxSize) {
        this.manager_maxSize = manager_maxSize;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public boolean isIs_sh() {
        return is_sh;
    }

    public void setIs_sh(boolean is_sh) {
        this.is_sh = is_sh;
    }


    public boolean isIs_dosign() {
        return is_dosign;
    }

    public void setIs_dosign(boolean is_dosign) {
        this.is_dosign = is_dosign;
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

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public AutoReplySetConf getReply() {
        return reply;
    }

    public void setReply(AutoReplySetConf reply) {
        this.reply = reply;
    }

    public boolean isIs_cmd() {
        return is_cmd;
    }

    public void setIs_cmd(boolean is_cmd) {
        this.is_cmd = is_cmd;
    }

    public boolean isIs_auto() {
        return is_auto;
    }

    public void setIs_auto(boolean is_auto) {
        this.is_auto = is_auto;
    }

    public boolean isWin_auto_openSet() {
        return win_auto_openSet;
    }

    public void setWin_auto_openSet(boolean win_auto_openSet) {
        this.win_auto_openSet = win_auto_openSet;
    }

    public ClockInSetConf getClock_in() {
        return clock_in;
    }

    public void setClock_in(ClockInSetConf clock_in) {
        this.clock_in = clock_in;
    }

    public ThankWelcomeSetConf getWelcome() {
        return welcome;
    }

    public void setWelcome(ThankWelcomeSetConf welcome) {
        this.welcome = welcome;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public AutoSendGiftConf getAuto_gift() {
        return auto_gift;
    }

    public void setAuto_gift(AutoSendGiftConf auto_gift) {
        this.auto_gift = auto_gift;
    }

    public PrivacySetConf getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacySetConf privacy) {
        this.privacy = privacy;
    }

    public String toJson() {
        return FastJsonUtils.toJson(this);
       // return FastJsonUtils.toJson(new CenterSetConf(is_barrage_guard, is_barrage_vip, is_barrage_manager, is_barrage_medal, is_barrage_ul,is_barrage_anchor_shield, is_block, is_gift, is_welcome, is_welcome_all, is_follow, is_log, is_cmd, roomid, is_auto,win_auto_openSet,is_manager_login,manager_key,manager_maxSize, is_online, is_sh, is_dosign,sign_time ,thank_gift, advert, follow, reply, clock_in, welcome,auto_gift,privacy));
    }
}
