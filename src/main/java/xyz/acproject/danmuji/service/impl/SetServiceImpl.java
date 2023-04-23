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
import xyz.acproject.danmuji.file.ProFileTools;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.BASE64Encoder;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;
import xyz.acproject.danmuji.utils.SchedulingRunnableUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private SetService setService;
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
        PublicDataConf.ANNOUNCE = PublicDataConf.centerSetConf.getPrivacy().is_open()?"隐私模式下不会获取最新公告":HttpOtherData.httpGetNewAnnounce();
        System.out.println("最新公告：" +  PublicDataConf.ANNOUNCE);
        if(!PublicDataConf.centerSetConf.getPrivacy().is_open()) {
            String edition = HttpOtherData.httpGetNewEdition();
            if (!StringUtils.isEmpty(edition)) {
                if (!edition.equals(PublicDataConf.EDITION)) {
                    System.out.println("查询最新版本：" + edition
                            + "目前脚本有可用更新哦，请到github官网查看更新https://github.com/BanqiJane/Bilibili_Danmuji");
                } else {
                    System.out.println("查询最新版本：目前使用的版本为最新版本，暂无可用更新");
                }
            } else {
                System.out.println("查询最新版本失败,目前版本：" + PublicDataConf.EDITION);
            }
        }else{
            System.out.println("隐私模式下不会从服务器获取最新版本信息,目前版本：" + PublicDataConf.EDITION);
        }
        System.out.println();
        // 自动连接
        if (PublicDataConf.centerSetConf.is_auto() && PublicDataConf.centerSetConf.getRoomid() > 0) {
            try {
                clientService.startConnService(PublicDataConf.centerSetConf.getRoomid());
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        // window用默认浏览器打开网页
        if(PublicDataConf.centerSetConf.isWin_auto_openSet()) {
            try {
                Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:" + serverAddressComponent.getPort());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(
                        "自动打开浏览器错误:当前系统缺少rundll32 url.dll组件或者不是window系统，无法自动启动默认浏览器打开配置页面，请手动打开浏览器地址栏输入http://127.0.0.1:23333进行配置");
            }
        }
    }

    public void changeSet(CenterSetConf centerSetConf) {
        synchronized (centerSetConf) {
            Map<String, String> profileMap = new ConcurrentHashMap<>();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            if (PublicDataConf.USER != null) {
                profileMap.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
            }
            profileMap.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
            ProFileTools.write(profileMap, "DanmujiProfile");
            LOGGER.info("保存配置文件成功");
            base64Encoder = null;
            profileMap.clear();
        }
    }

    // 保存配置文件
    public void changeSet(CenterSetConf centerSetConf,boolean check) {
        synchronized (centerSetConf) {
            if (centerSetConf.toJson().equals(PublicDataConf.centerSetConf.toJson())&&check) {
                LOGGER.info("保存配置文件成功");
                return;
            }
            if (PublicDataConf.ROOMID_SAFE != null && PublicDataConf.ROOMID_SAFE > 0) {
                centerSetConf.setRoomid(PublicDataConf.ROOMID_SAFE);
            }
            Map<String, String> profileMap = new ConcurrentHashMap<>();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            if (PublicDataConf.USER != null) {
                profileMap.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
            }
            profileMap.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
            ProFileTools.write(profileMap, "DanmujiProfile");
            try {
                PublicDataConf.centerSetConf = JSONObject.parseObject(
                        new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
                        CenterSetConf.class);
                holdSet(centerSetConf);
                LOGGER.info("保存配置文件成功");
            } catch (Exception e) {
                // TODO: handle exception
                LOGGER.error("保存配置文件失败:" + e);
            }
            base64Encoder = null;
            profileMap.clear();
        }
    }

    public void connectSet(CenterSetConf centerSetConf) {
        synchronized (centerSetConf) {
            Map<String, String> profileMap = new ConcurrentHashMap<>();
            BASE64Encoder base64Encoder = new BASE64Encoder();
            if (PublicDataConf.USER != null) {
                profileMap.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
            }
            profileMap.put("set", base64Encoder.encode(centerSetConf.toJson().getBytes()));
            ProFileTools.write(profileMap, "DanmujiProfile");
            try {
                PublicDataConf.centerSetConf = JSONObject.parseObject(
                        new String(base64Encoder.decode(ProFileTools.read("DanmujiProfile").get("set"))),
                        CenterSetConf.class);
                PublicDataConf.centerSetConf = ParseSetStatusTools.initCenterChildConfig(PublicDataConf.centerSetConf);
                if (PublicDataConf.ROOMID != null) {
                    holdSet(centerSetConf);
                }
                LOGGER.info("读取配置文件历史房间成功");
            } catch (Exception e) {
                // TODO: handle exception
                LOGGER.error("读取配置文件历史房间失败:" + e);
            }
            base64Encoder = null;
            profileMap.clear();
        }
    }


    /**
     * 保存配置并执行
     */
    public void holdSet(CenterSetConf centerSetConf) {
        synchronized (centerSetConf) {
            SchedulingRunnableUtil task = new SchedulingRunnableUtil("dosignTask", "dosign");
            SchedulingRunnableUtil dakatask = new SchedulingRunnableUtil("dosignTask", "clockin");
            SchedulingRunnableUtil autoSendGiftTask = new SchedulingRunnableUtil("dosignTask", "autosendgift");
            //每日签到
            if (PublicDataConf.centerSetConf.is_dosign()) {
                //判断签到
                boolean flag = CurrencyTools.signNow();
                if (flag) {
                    changeSet(PublicDataConf.centerSetConf);
                }
//                if (PublicDataConf.is_sign) {
//                    HttpUserData.httpGetDoSign();
//                    PublicDataConf.is_sign = true;
//                }
                if (!taskRegisterComponent.hasTask(task)) {
                    taskRegisterComponent.addTask(task, CurrencyTools.dateStringToCron(centerSetConf.getSign_time()));
                }
            } else {
                try {
                    taskRegisterComponent.removeTask(task);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    LOGGER.error("清理定时任务错误：" + e);
                }
            }
            //每日打卡
            if (centerSetConf.getClock_in().is_open()) {
                //移除
                //这里开启一个匿名线程用于打卡
//                new Thread(() -> {
//                    List<UserMedal> userMedals = CurrencyTools.getAllUserMedals();
//                    int max = CurrencyTools.clockIn(userMedals);
//                    if (max == userMedals.size()) {
//                        HttpOtherData.httpPOSTSetClockInRecord();
//                    }
//                }).start();
                if (!taskRegisterComponent.hasTask(dakatask)) {
                    taskRegisterComponent.addTask(dakatask, CurrencyTools.dateStringToCron(centerSetConf.getClock_in().getTime()));
                }
            } else {
                try {
                    taskRegisterComponent.removeTask(dakatask);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    LOGGER.error("清理定时任务错误：" + e);
                }
            }
            //每日定时自动送礼
            if (centerSetConf.getAuto_gift().is_open()) {
                if (!taskRegisterComponent.hasTask(autoSendGiftTask)) {
                    taskRegisterComponent.addTask(autoSendGiftTask, CurrencyTools.dateStringToCron(centerSetConf.getAuto_gift().getTime()));
                }
            } else {
                try {
                    taskRegisterComponent.removeTask(autoSendGiftTask);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    LOGGER.error("清理定时任务错误：" + e);
                }
            }

            //need roomid set
            if (PublicDataConf.ROOMID == null || PublicDataConf.ROOMID <= 0) {
                return;
            }
            if (PublicDataConf.webSocketProxy == null) {
                return;
            }
            if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) return;
            // parsemessagethread start
            threadComponent.startParseMessageThread(
                    centerSetConf);
            // logthread
            if (centerSetConf.is_log()) {
                threadComponent.startLogThread();
            } else {
                threadComponent.closeLogThread();
            }
            // need login
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                // advertthread
                centerSetConf.getAdvert().start(threadComponent);
//            if (centerSetConf.getAdvert().is_live_open()) {
//                if (PublicDataConf.lIVE_STATUS != 1) {
//                    threadComponent.closeAdvertThread();
//                } else {
//                    if (centerSetConf.getAdvert().is_open()) {
//                        threadComponent.startAdvertThread(centerSetConf);
//                    } else {
//                        threadComponent.setAdvertThread(centerSetConf);
//                        threadComponent.closeAdvertThread();
//                    }
//                }
//            } else {
//                if (centerSetConf.getAdvert().is_open()) {
//                    threadComponent.startAdvertThread(centerSetConf);
//                } else {
//                    threadComponent.setAdvertThread(centerSetConf);
//                    threadComponent.closeAdvertThread();
//                }
//            }
                // autoreplythread
                centerSetConf.getReply().start(threadComponent);
//            if (centerSetConf.getReply().is_live_open()) {
//                if (PublicDataConf.lIVE_STATUS != 1) {
//                    threadComponent.closeAutoReplyThread();
//                } else {
//                    if (centerSetConf.getReply().is_open()) {
//                        threadComponent.startAutoReplyThread(centerSetConf);
//                    } else {
//                        threadComponent.setAutoReplyThread(centerSetConf);
//                        threadComponent.closeAutoReplyThread();
//                    }
//                }
//            } else {
//                if (centerSetConf.getReply().is_open()) {
//                    threadComponent.startAutoReplyThread(centerSetConf);
//                } else {
//                    threadComponent.setAutoReplyThread(centerSetConf);
//                    threadComponent.closeAutoReplyThread();
//                }
//            }
                // useronlinethread && smallHeartThread
                if (centerSetConf.is_online()) {
                    threadComponent.startUserOnlineThread();
                    if (centerSetConf.is_sh() && PublicDataConf.lIVE_STATUS == 1) {
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
                        && !PublicDataConf.centerSetConf.getFollow().is_followThank()
                        && !PublicDataConf.centerSetConf.getWelcome().is_welcomeThank()
                        && !PublicDataConf.centerSetConf.getThank_gift().is_giftThank()
                        && PublicDataConf.autoReplyThread == null) {
                    threadComponent.closeSendBarrageThread();
                    PublicDataConf.init_send();
                } else {
                    threadComponent.startSendBarrageThread();
                }
            } else {
                //没有登录
                PublicDataConf.init_user();
                threadComponent.closeUser(false);
            }
            if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
                threadComponent.closeAll();
                PublicDataConf.init_all();
            }
        }
    }

    public void quit() {
        PublicDataConf.init_user();
        threadComponent.closeUser(true);
        // remove task all shutdown !!!!!!
        try {
            taskRegisterComponent.destroy();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error("清理定时任务错误：" + e);
        }
        PublicDataConf.init_send();
        holdSet(PublicDataConf.centerSetConf);
        LOGGER.info("用户退出成功");
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

    @Autowired
    public void setSetService(SetService setService) {
        this.setService = setService;
    }
}
