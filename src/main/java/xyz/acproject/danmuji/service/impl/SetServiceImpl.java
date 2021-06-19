package xyz.acproject.danmuji.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.acproject.danmuji.component.ServerAddressComponent;
import xyz.acproject.danmuji.component.TaskRegisterComponent;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.entity.user_data.UserMedal;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.file.ProFileTools;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.BASE64Encoder;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;
import xyz.acproject.danmuji.utils.SchedulingRunnableUtil;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * @author BanqiJane
 * @ClassName SetServiceImpl
 * @Description TODO
 * @date 2020年8月10日 下午12:17:03
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@SuppressWarnings("all")
@Service
public class SetServiceImpl implements SetService {
    private Logger LOGGER = LogManager.getLogger(SetServiceImpl.class);
    private final String cookies = "ySZL4SBB";
    private ClientService clientService;
    private ThreadComponent threadComponent;
    private ServerAddressComponent serverAddressComponent;
    private TaskRegisterComponent taskRegisterComponent;


    public void init() {
        // 公告和检查更新
        System.out.println();
        System.out.println();
        System.out.println(
                "参考本地浏览器进入设置页面地址: 1、http://127.0.0.1:" + serverAddressComponent.getPort() + ";2、http://localhost:"
                        + serverAddressComponent.getPort() + ";3、" + serverAddressComponent.getAddress());
        System.out.println("参考局域网浏览器进入设置页面地址: 1、" + serverAddressComponent.getAddress());
        System.out.println("参考远程(无代理)浏览器进入设置页面地址: 1、" + serverAddressComponent.getRemoteAddress());
        System.out.println();
        System.out.println("最新公告：" + HttpOtherData.httpGetNewAnnounce());
        String edition = HttpOtherData.httpGetNewEdition();
        if (!StringUtils.isEmpty(edition)) {
            if (!edition.equals(PublicDataConf.EDITION)) {
                System.out.println("查询最新版本：" + edition
                        + "目前脚本有可用更新哦，请到github官网查看更新https://github.com/BanqiJane/Bilibili_Danmuji");
            } else {
                System.out.println("查询最新版本：目前使用的版本为最新版本，暂无可用更新");
            }
        } else {
            System.out.println("查询最新版本失败,打印目前版本：" + PublicDataConf.EDITION);
        }
        System.out.println();
        // 自动连接
        if (PublicDataConf.centerSetConf.isIs_auto() && PublicDataConf.centerSetConf.getRoomid() > 0) {
            try {
                clientService.startConnService(PublicDataConf.centerSetConf.getRoomid());
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        // window用默认浏览器打开网页
        try {
            Runtime.getRuntime()
                    .exec("rundll32 url.dll,FileProtocolHandler " + serverAddressComponent.getAddress());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(
                    "自动打开浏览器错误:当前系统缺少rundll32 url.dll组件或者不是window系统，无法自动启动默认浏览器打开配置页面，请手动打开浏览器地址栏输入http://127.0.0.1:23333进行配置");
        }
    }

    // 保存配置文件
    public void changeSet(CenterSetConf centerSetConf) {
        if (centerSetConf.toJson().equals(PublicDataConf.centerSetConf.toJson())) {
            LOGGER.debug("保存配置文件成功");
            return;
        }
        if (PublicDataConf.ROOMID_SAFE != null && PublicDataConf.ROOMID_SAFE > 0) {
            centerSetConf.setRoomid(PublicDataConf.ROOMID_SAFE);
        }
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        if (PublicDataConf.USER != null) {
            hashtable.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
        }
        hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
        ProFileTools.write(hashtable, "DanmujiProfile");
        try {
            PublicDataConf.centerSetConf = JSONObject.parseObject(
                    new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
                    CenterSetConf.class);
            holdSet(centerSetConf);
            LOGGER.debug("保存配置文件成功");
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("保存配置文件失败:" + e);
        }
        base64Encoder = null;
        hashtable.clear();
    }

    public void connectSet(CenterSetConf centerSetConf) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        if (PublicDataConf.USER != null) {
            hashtable.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
        }
        hashtable.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
        ProFileTools.write(hashtable, "DanmujiProfile");
        try {
            PublicDataConf.centerSetConf = JSONObject.parseObject(
                    new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
                    CenterSetConf.class);
            if (PublicDataConf.centerSetConf.getAdvert() == null) {
                PublicDataConf.centerSetConf.setAdvert(new AdvertSetConf());
            }
            if (PublicDataConf.centerSetConf.getFollow() == null) {
                PublicDataConf.centerSetConf.setFollow(new ThankFollowSetConf());
            }
            if (PublicDataConf.centerSetConf.getThank_gift() == null) {
                PublicDataConf.centerSetConf.setThank_gift(new ThankGiftSetConf());
            }
            if (PublicDataConf.centerSetConf.getReply() == null) {
                PublicDataConf.centerSetConf.setReply(new AutoReplySetConf());
            }
            if (PublicDataConf.centerSetConf.getClock_in() == null) {
                PublicDataConf.centerSetConf.setClock_in(new ClockInSetConf(false, "签到"));
            }
            if (PublicDataConf.centerSetConf.getWelcome() == null) {
                PublicDataConf.centerSetConf.setWelcome(new ThankWelcomeSetConf());
            }
            if (PublicDataConf.ROOMID != null) {
                holdSet(centerSetConf);
            }
            LOGGER.debug("读取配置文件历史房间成功");
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("读取配置文件历史房间失败:" + e);
        }
        base64Encoder = null;
        hashtable.clear();
    }


    /**
     * 保存配置并执行
     */
    public void holdSet(CenterSetConf centerSetConf) {
        SchedulingRunnableUtil task = new SchedulingRunnableUtil("dosignTask", "dosign");
        SchedulingRunnableUtil dakatask = new SchedulingRunnableUtil("dosignTask", "clockin");
        //每日签到
        if (PublicDataConf.centerSetConf.isIs_dosign()) {
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                if (!PublicDataConf.is_sign) {
                    HttpUserData.httpGetDoSign();
                    if (!taskRegisterComponent.hasTask(task)) {
                        taskRegisterComponent.addTask(task, "0 30 0 * * ?");
                    }
                    PublicDataConf.is_sign = true;
                }
            }
        } else {
            try {
                taskRegisterComponent.removeTask(task);
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                LOGGER.error("清理定时任务错误：" + e);
            }
        }
        if (centerSetConf.getClock_in().isIs_open()) {
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                //这里开启一个匿名线程用于打卡
                new Thread(() -> {
                    List<UserMedal> userMedals = CurrencyTools.getAllUserMedals();
                    int max = CurrencyTools.clockIn(userMedals);
                    if (max == userMedals.size()) {
                        HttpOtherData.httpPOSTSetClockInRecord();
                    }
                }).start();
            }
            if (!taskRegisterComponent.hasTask(dakatask)) {
                taskRegisterComponent.addTask(dakatask, "0 35 0 * * ?");
            }
        } else {
            try {
                taskRegisterComponent.removeTask(dakatask);
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                LOGGER.error("清理定时任务错误：" + e);
            }
        }
        if (PublicDataConf.ROOMID == null || PublicDataConf.ROOMID <= 0) {
            return;
        }
        if (PublicDataConf.webSocketProxy == null) {
            return;
        }
        if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) return;
        // parsemessagethread start
        threadComponent.startParseMessageThread(
                ParseSetStatusTools.getMessageConcurrentHashMap(centerSetConf, PublicDataConf.lIVE_STATUS),
                centerSetConf);
        // logthread
        if (centerSetConf.isIs_log()) {
            threadComponent.startLogThread();
        } else {
            threadComponent.closeLogThread();
        }
        // need login
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            // advertthread
            if (centerSetConf.getAdvert().isIs_live_open()) {
                if (PublicDataConf.lIVE_STATUS != 1) {
                    threadComponent.closeAdvertThread();
                } else {
                    if (centerSetConf.getAdvert().isIs_open()) {
                        threadComponent.startAdvertThread(centerSetConf);
                    } else {
                        threadComponent.setAdvertThread(centerSetConf);
                        threadComponent.closeAdvertThread();
                    }
                }
            } else {
                if (centerSetConf.getAdvert().isIs_open()) {
                    threadComponent.startAdvertThread(centerSetConf);
                } else {
                    threadComponent.setAdvertThread(centerSetConf);
                    threadComponent.closeAdvertThread();
                }
            }
            // autoreplythread
            if (centerSetConf.getReply().isIs_live_open()) {
                if (PublicDataConf.lIVE_STATUS != 1) {
                    threadComponent.closeAutoReplyThread();
                } else {
                    if (centerSetConf.getReply().isIs_open()) {
                        threadComponent.startAutoReplyThread(centerSetConf);
                    } else {
                        threadComponent.setAutoReplyThread(centerSetConf);
                        threadComponent.closeAutoReplyThread();
                    }
                }
            } else {
                if (centerSetConf.getReply().isIs_open()) {
                    threadComponent.startAutoReplyThread(centerSetConf);
                } else {
                    threadComponent.setAutoReplyThread(centerSetConf);
                    threadComponent.closeAutoReplyThread();
                }
            }
            // useronlinethread && smallHeartThread
            if (centerSetConf.isIs_online()) {
                threadComponent.startUserOnlineThread();
                if (centerSetConf.isIs_sh() && PublicDataConf.lIVE_STATUS == 1) {
                    threadComponent.startSmallHeartThread();
                } else {
                    threadComponent.closeSmallHeartThread();
                }
            } else {
                threadComponent.closeSmallHeartThread();
                threadComponent.closeUserOnlineThread();
            }
            // sendbarragethread
            if (PublicDataConf.advertThread == null
                    && !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_followThank)
                    && !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_welcomeThank)
                    && !PublicDataConf.parseMessageThread.getMessageControlMap().get(ShieldMessage.is_giftThank)
                    && PublicDataConf.autoReplyThread == null) {
                threadComponent.closeSendBarrageThread();
                PublicDataConf.replys.clear();
                PublicDataConf.thankGiftConcurrentHashMap.clear();
                PublicDataConf.barrageString.clear();
                PublicDataConf.interacts.clear();
                PublicDataConf.interactWelcome.clear();
            } else {
                threadComponent.startSendBarrageThread();
            }
        } else {

            PublicDataConf.COOKIE = null;
            PublicDataConf.USER = null;
            PublicDataConf.USERCOOKIE = null;
            PublicDataConf.USERBARRAGEMESSAGE = null;
            threadComponent.closeAdvertThread();
            threadComponent.closeUserOnlineThread();
            threadComponent.closeGiftShieldThread();
            threadComponent.closeSendBarrageThread();
            threadComponent.closeFollowShieldThread();
            threadComponent.closeWelcomeShieldThread();
            threadComponent.closeSmallHeartThread();
        }
        if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
            threadComponent.closeHeartByteThread();
            threadComponent.closeParseMessageThread();
            threadComponent.closeUserOnlineThread();
            threadComponent.closeFollowShieldThread();
            threadComponent.closeAdvertThread();
            threadComponent.closeSendBarrageThread();
            threadComponent.closeLogThread();
            threadComponent.closeGiftShieldThread();
            threadComponent.closeAutoReplyThread();
            threadComponent.closeSmallHeartThread();
            threadComponent.closeWelcomeShieldThread();
            PublicDataConf.SHIELDGIFTNAME = null;
            PublicDataConf.replys.clear();
            PublicDataConf.resultStrs.clear();
            PublicDataConf.thankGiftConcurrentHashMap.clear();
            PublicDataConf.barrageString.clear();
            PublicDataConf.logString.clear();
            PublicDataConf.interacts.clear();
            PublicDataConf.interactWelcome.clear();
            PublicDataConf.ISSHIELDFOLLOW = false;
            PublicDataConf.ISSHIELDWELCOME = false;
        }
    }

    public void quit() {
        PublicDataConf.COOKIE = null;
        PublicDataConf.USER = null;
        PublicDataConf.USERCOOKIE = null;
        PublicDataConf.USERBARRAGEMESSAGE = null;
        threadComponent.closeAdvertThread();
        threadComponent.closeUserOnlineThread();
        threadComponent.closeGiftShieldThread();
        threadComponent.closeFollowShieldThread();
        threadComponent.closeAutoReplyThread();
        threadComponent.closeSendBarrageThread();
        threadComponent.closeSmallHeartThread();
        threadComponent.closeWelcomeShieldThread();
        // remove task all shutdown !!!!!!
        try {
            taskRegisterComponent.destroy();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error("清理定时任务错误：" + e);
        }
        PublicDataConf.replys.clear();
        PublicDataConf.thankGiftConcurrentHashMap.clear();
        PublicDataConf.barrageString.clear();
        PublicDataConf.interacts.clear();
        PublicDataConf.interactWelcome.clear();
        holdSet(PublicDataConf.centerSetConf);
        LOGGER.debug("用户退出成功");
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setThreadComponent(ThreadComponent threadComponent) {
        this.threadComponent = threadComponent;
    }

    @Autowired
    public void setServerAddressComponent(ServerAddressComponent serverAddressComponent) {
        this.serverAddressComponent = serverAddressComponent;
    }

    @Autowired
    public void setTaskRegisterComponent(TaskRegisterComponent taskRegisterComponent) {
        this.taskRegisterComponent = taskRegisterComponent;
    }
}
