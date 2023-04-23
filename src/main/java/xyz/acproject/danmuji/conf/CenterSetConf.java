package xyz.acproject.danmuji.conf;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterSetConf implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1162255349476806991L;
    //是否开启弹幕
    @JSONField(name = "is_barrage")
    private boolean is_barrage = true;
    //弹幕显示舰长和老爷图标
    @JSONField(name = "is_barrage_guard")
    private boolean is_barrage_guard = true;
    //弹幕显示舰长和老爷图标
    @JSONField(name = "is_barrage_vip")
    private boolean is_barrage_vip = true;
    //弹幕显示房管图标
    @JSONField(name = "is_barrage_manager")
    private boolean is_barrage_manager = true;
    //弹幕显示勋章图标
    @JSONField(name = "is_barrage_medal")
    private boolean is_barrage_medal = false;
    //弹幕显示用户等级图标
    @JSONField(name = "is_barrage_ul")
    private boolean is_barrage_ul = false;
    //是否屏蔽非当前房间勋章弹幕
    @JSONField(name = "is_barrage_anchor_shield")
    private boolean is_barrage_anchor_shield = false;
    //信息是否显示房管禁言消息
    @JSONField(name = "is_block")
    private boolean is_block = true;
    //信息是否显示礼物消息
    @JSONField(name = "is_gift")
    private boolean is_gift = true;
    //信息是否显示免费礼物消息
    @JSONField(name = "is_gift_free")
    private boolean is_gift_free = true;
    //信息是否显示欢迎老爷舰长进入直播间消息
    @JSONField(name = "is_welcome_ye")
    private boolean is_welcome_ye = true;
    //信息是否显示全部欢迎
    @JSONField(name = "is_welcome_all")
    private boolean is_welcome_all = false;
    //是否开启关注显示
    @JSONField(name = "is_follow_dm")
    private boolean is_follow_dm = true;
    //是否开启日志线程
    @JSONField(name = "is_log")
    private boolean is_log = false;
    //是否控制台打印
    @JSONField(name = "is_cmd")
    private boolean is_cmd = true;
    //房间号
    private Long roomid = 0l;
    //是否自动连接
    @JSONField(name = "is_auto")
    private boolean is_auto = false;
    //window是否自动打开设置页面 默认open
    @JSONField(name = "win_auto_openSet")
    private boolean win_auto_openSet = true;

    @JSONField(name = "auto_save_set")
    private boolean auto_save_set = false;

    //是否开启登录
    @JSONField(name = "is_manager_login")
    private boolean is_manager_login = false;
    //123
    private String manager_key = "202cb962ac59075b964b07152d234b70";
    //每次登录最大尝试次数
    private int manager_maxSize = 10;

    //-------------------------以下设置需要用户登录后----------------------------------------
    //是否开启用户在线心跳线程
    @JSONField(name = "is_online")
    private boolean is_online = false;
    //是否开启用户小心心线程 前提是is_online为true
    @JSONField(name = "is_sh")
    private boolean is_sh = false;
    //是否开启用户自动签到
    @JSONField(name = "is_dosign")
    private boolean is_dosign = false;
    //自定义的签到时间
    private String sign_time = "00:30:00";
    //是否开启礼物感谢线程对象体 black
    @JSONField(name = "thank_gift")
    private ThankGiftSetConf thank_gift;
    //是否开启广告公告线程对象体
    @JSONField(name = "advert")
    private AdvertSetConf advert;
    //是否开启感谢关注线程对象体 black
    @JSONField(name = "follow")
    private ThankFollowSetConf follow;
    //是否开启自动回复线程对象体 black
    @JSONField(name = "reply")
    private AutoReplySetConf reply;
    //开启自动打卡设置对象体
    @JSONField(name = "clock_in")
    private ClockInSetConf clock_in;
    //是否开启欢迎进入直播间线程对象体 black
    @JSONField(name = "welcome")
    private ThankWelcomeSetConf welcome;
    //是否开启自动送礼对象体
    @JSONField(name = "auto_gift")
    private AutoSendGiftConf auto_gift;
    //是否开启隐私模式 不再调用服务器
    @JSONField(name = "privacy")
    private PrivacySetConf privacy;
    //全局黑名单
    @JSONField(name="black")
    private BlackListSetConf black;


    @JSONField(name = "edition",serialize = false)
    private String edition = "";



    public CenterSetConf(ThankGiftSetConf thank_gift, AdvertSetConf advert,
                         ThankFollowSetConf follow, AutoReplySetConf reply, ClockInSetConf clock_in, ThankWelcomeSetConf welcome, AutoSendGiftConf auto_gift, PrivacySetConf privacy,BlackListSetConf black) {
        super();
        this.thank_gift = thank_gift;
        this.advert = advert;
        this.follow = follow;
        this.reply = reply;
        this.clock_in = clock_in;
        this.welcome = welcome;
        this.auto_gift= auto_gift;
        this.privacy = privacy;
        this.black= black;
    }

    public String toJson() {
        return FastJsonUtils.toJson(this);
       // return FastJsonUtils.toJson(new CenterSetConf(is_barrage_guard, is_barrage_vip, is_barrage_manager, is_barrage_medal, is_barrage_ul,is_barrage_anchor_shield, is_block, is_gift, is_welcome, is_welcome_all, is_follow, is_log, is_cmd, roomid, is_auto,win_auto_openSet,is_manager_login,manager_key,manager_maxSize, is_online, is_sh, is_dosign,sign_time ,thank_gift, advert, follow, reply, clock_in, welcome,auto_gift,privacy));
    }
}
