package xyz.acproject.danmuji.thread.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.component.black.BlackParseComponent;
import xyz.acproject.danmuji.conf.CacheConf;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.controller.DanmuWebsocket;
import xyz.acproject.danmuji.entity.Welcome.WelcomeGuard;
import xyz.acproject.danmuji.entity.Welcome.WelcomeVip;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.entity.base.WsPackage;
import xyz.acproject.danmuji.entity.danmu_data.*;
import xyz.acproject.danmuji.entity.high_level_danmu.Hbarrage;
import xyz.acproject.danmuji.entity.interactWordV2.INTERACTWORDV2;
import xyz.acproject.danmuji.entity.room_data.LotteryInfoWeb;
import xyz.acproject.danmuji.entity.superchat.MedalInfo;
import xyz.acproject.danmuji.entity.superchat.SuperChat;
import xyz.acproject.danmuji.enums.ListPeopleShieldStatus;
import xyz.acproject.danmuji.enums.ShieldGift;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.ParseIndentityTools;
import xyz.acproject.danmuji.tools.ParseSetStatusTools;
import xyz.acproject.danmuji.tools.ShieldGiftTools;
import xyz.acproject.danmuji.tools.file.GuardFileTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BanqiJane
 * @ClassName ParseMessageThread
 * @Description TODO
 * @date 2020年8月10日 下午12:16:51
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ParseMessageThread extends Thread {
    private final static Logger LOGGER = LogManager.getLogger(ParseMessageThread.class);
    public volatile boolean FLAG = false;
    private DanmuWebsocket danmuWebsocket = SpringUtils.getBean(DanmuWebsocket.class);
    private SetService setService = SpringUtils.getBean(SetService.class);

    private BlackParseComponent blackParseComponent = SpringUtils.getBean(BlackParseComponent.class);
    private ThreadComponent threadComponent = SpringUtils.getBean(ThreadComponent.class);
    private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
    private CenterSetConf centerSetConf;


    @Override
    public void run() {
        // TODO 自动生成的方法存根
        super.run();
        JSONObject jsonObject = null;
        JSONArray array = null;
        Barrage barrage = null;
        Gift gift = null;
        Interact interact = null;
//		Fans fans = null;
//		Rannk rannk = null;
        Guard guard = null;
        SuperChat superChat = null;
        BlockMessage blockMessage = null;
        WelcomeGuard welcomeGuard = null;
        WelcomeVip welcomeVip = null;
        RedPackage redPackage = null;
//		GiftFile giftFile = null;
        String message = "";
        String cmd = "";
        short msg_type = 0;
        //high_level danmu
        Hbarrage hbarrage = null;
        StringBuilder stringBuilder = new StringBuilder(200);
        while (!FLAG) {
            try {
                if (FLAG) {
                    LOGGER.info("数据解析线程手动中止");
                    return;
                }
                if (null != PublicDataConf.resultStrs && !PublicDataConf.resultStrs.isEmpty()
                        && StringUtils.isNotBlank(PublicDataConf.resultStrs.get(0))) {
                    message = PublicDataConf.resultStrs.get(0);
                    try {
                        jsonObject = JSONObject.parseObject(message);
                    } catch (Exception e) {
                        // TODO: handle exception
                        LOGGER.info("抛出解析异常:" + e);
                        //					LOGGER.info(message);
                        synchronized (PublicDataConf.parseMessageThread) {
                            try {
                                PublicDataConf.parseMessageThread.wait();
                            } catch (InterruptedException e1) {
                                // TODO 自动生成的 catch 块
                                LOGGER.info("处理弹幕包信息线程关闭:" + e1);
                                //e.printStackTrace();
                            }
                        }
                    }
                    cmd = jsonObject.getString("cmd");
                    if (StringUtils.isBlank(cmd)) {
                        synchronized (PublicDataConf.parseMessageThread) {
                            try {
                                PublicDataConf.parseMessageThread.wait();
                            } catch (InterruptedException e1) {
                                // TODO 自动生成的 catch 块
                                LOGGER.info("处理弹幕包信息线程关闭:" + e1);
                                //							e.printStackTrace();
                            }
                        }
                    }
                    cmd = parseCmd(cmd);
                    switch (cmd) {
                        // 弹幕
                        case "DANMU_MSG":
                            array = jsonObject.getJSONArray("info");
                            try {
                                //[[0,7,100,16777215,1712992079355,0,0,"f4dbdf21",0,null,null,null,0,"{}","{}",{"mode":0,"extra":"{\"mode\":0,\"send_from_me\":false,\"color\":16777215,\"dm_type\":0,\"font_size\":100,\"player_mode\":7,\"content\":\"[1.0,0.0,\\\"0.8-0.5\\\",10.0,\\\"我\\\",0.0,0.0,0.0,0.0,10000,0,true,\\\"黑体\\\",1]\"}","show_player_type":0},null,null],"[1.0,0.0,\"0.8-0.5\",10.0,\"我\",0.0,0.0,0.0,0.0,10000,0,true,\"黑体\",1]",[0,"***",1,0,0,10000,1,""],null,[],[],0,0,null,{"ct":"B44D80B0","ts":1712992079},0,0,null,null,0,0,[0]]
                                //[[0, 1, 25, 16777215, 1715735563613, 976713860, 0, 6e0a38fd, 0, 0, 0, , 0, {}, {}, {mode=0, extra={"send_from_me":false,"mode":0,"color":16777215,"dm_type":0,"font_size":25,"player_mode":1,"show_player_type":0,"content":"好看嘟","user_hash":"1846163709","emoticon_unique":"","bulge_display":0,"recommend_score":4,"main_state_dm_color":"","objective_state_dm_color":"","direction":0,"pk_direction":0,"quartet_direction":0,"anniversary_crowd":0,"yeah_space_type":"","yeah_space_url":"","jump_to_url":"","space_type":"","space_url":"","animation":{},"emots":null,"is_audited":false,"id_str":"5a78879192fe8d0b0b566fa89466440c1","icon":null,"show_reply":true,"reply_mid":0,"reply_uname":"","reply_uname_color":"","reply_is_mystery":false,"hit_combo":0}, show_player_type=0, user={wealth=null, uid=103031126, guard=null, medal=null, uhead_frame=null, guard_leader={is_guard_leader=false}, title={old_title_css_id=, title_css_id=}, base={origin_info={face=https://i2.hdslb.com/bfs/face/33dbd8d6e36f0ab184ef1adde4c4f618d0a1dedb.jpg, name=Qm-小柴}, risk_ctrl_info=null, face=https://i2.hdslb.com/bfs/face/33dbd8d6e36f0ab184ef1adde4c4f618d0a1dedb.jpg, is_mystery=false, name=Qm-小柴, name_color_str=, name_color=0, official_info={role=0, title=, type=-1, desc=}}}}, {activity_source=0, activity_identity=, not_show=0}, 0], 好看嘟, [103031126, Qm-小柴, 1, 0, 0, 10000, 1, ], [], [4, 0, 9868950, >50000, 0], [, ], 0, 0, null, {ct=ABE155CD, ts=1715735563}, 0, 0, null, null, 0, 364, [11], null]
                                barrage = Barrage.getBarrage(((JSONArray) array.get(2)).getLong(0),
                                        ((JSONArray) array.get(2)).getString(1), array.getString(1),
                                        ((JSONArray) array.get(0)).getShort(9), ((JSONArray) array.get(0)).getShort(12),
                                        ((JSONArray) array.get(0)).getLong(4),
                                        ((JSONArray) array.get(2)).getShort(2), ((JSONArray) array.get(2)).getShort(3),
                                        ((JSONArray) array.get(2)).getShort(4), ((JSONArray) array.get(2)).getInteger(5),
                                        ((JSONArray) array.get(2)).getShort(6),
                                        ((JSONArray) array.get(3)).size() <= 0 ? 0 : ((JSONArray) array.get(3)).getShort(0),
                                        ((JSONArray) array.get(3)).size() <= 0 ? "" : ((JSONArray) array.get(3)).getString(1),
                                        ((JSONArray) array.get(3)).size() <= 0 ? "" : ((JSONArray) array.get(3)).getString(2),
                                        ((JSONArray) array.get(3)).size() <= 0 ? 0L : ((JSONArray) array.get(3)).getLong(3),
                                        ((JSONArray) array.get(4)).getShort(0), ((JSONArray) array.get(4)).getString(3),
                                        ((JSONArray) array.get(5)).getString(0), ((JSONArray) array.get(5)).getString(1),
                                        array.getShort(7),
                                        JSONObject.parseObject(((JSONArray) array.get(0)).getString(13)).getString("emoticon_unique"),
                                        JSONObject.parseObject(((JSONArray) array.get(0)).getString(13)).getString("url"));
                            } catch (Exception e) {
                                // TODO: handle exception
                                LOGGER.error("弹幕体解析抛出解析异常体:{}" ,message);
                                e.printStackTrace();
                                break;
                            }
                            //是否开启弹幕
                            boolean is_barrage = getCenterSetConf().is_barrage();
                            // 勋章弹幕
                            boolean is_xunzhang = true;
                            //是不是表情弹幕
                            boolean is_emoticon = barrage.getMsg_emoticon() != null && barrage.getMsg_emoticon() == 1;
                            if (getCenterSetConf().is_barrage_anchor_shield() && PublicDataConf.ROOMID != null) {
                                // 房管
                                if (barrage.getMedal_room() != (long) PublicDataConf.ROOMID) is_xunzhang = false;
                            }
                            //{"cmd":"DANMU_MSG","dm_v2":"CiIwMTc0ZjQzZjhiZjgyMjE3MjJlMjA2MWNmYjY0YWQzMjU3EAEYGSDeg+MCKghmMTYyNGY0MzID6LWeOLCOu6SUMUiWlLPq+P////8BYgBoAXJnCgPotZ4SYAoMb2ZmaWNpYWxfMTQ3EklodHRwOi8vaTAuaGRzbGIuY29tL2Jmcy9saXZlL2JiZDkwNDU1NzBkMGMwMjJhOTg0YzYzN2U0MDZjYjBlMWYyMDhhYTkucG5nIAEwPDiWAYoBAJoBEAoIRTYwNkY4NzQQ4+W0pQaiAaEBCO2DhBMSD+iAs+S4nOeri+S5oOS5oCJLaHR0cHM6Ly9pMS5oZHNsYi5jb20vYmZzL2ZhY2UvNTU1MGQ0NTQyY2NhNjkxNzYzZjdhMTNhZTUzMDIzNDE3NTNiMDJiYy53ZWJwOJBOQAFaIAgUEgbliLrlhL8gpLqeBjCkup4GOKS6ngZApLqeBlABYg8IHxDx0YEFGgY+NTAwMDBqAHIAegCqARUIlqUKEgzpgI3pgaXmlaPkuroYhQg=","info":[[0,1,25,5816798,1689072355120,-1924347370,0,"f1624f43",0,0,0,"",1,{"bulge_display":0,"emoticon_unique":"official_147","height":60,"in_player_area":1,"is_dynamic":0,"url":"http://i0.hdslb.com/bfs/live/bbd9045570d0c022a984c637e406cb0e1f208aa9.png","width":150},"{}",{"extra":"{\"send_from_me\":false,\"mode\":0,\"color\":5816798,\"dm_type\":1,\"font_size\":25,\"player_mode\":1,\"show_player_type\":0,\"content\":\"赞\",\"user_hash\":\"4049751875\",\"emoticon_unique\":\"official_147\",\"bulge_display\":0,\"recommend_score\":0,\"main_state_dm_color\":\"\",\"objective_state_dm_color\":\"\",\"direction\":0,\"pk_direction\":0,\"quartet_direction\":0,\"anniversary_crowd\":0,\"yeah_space_type\":\"\",\"yeah_space_url\":\"\",\"jump_to_url\":\"\",\"space_type\":\"\",\"space_url\":\"\",\"animation\":{},\"emots\":null,\"is_audited\":false,\"id_str\":\"0174f43f8bf8221722e2061cfb64ad3257\",\"icon\":null}","mode":0,"show_player_type":0},{"activity_identity":"","activity_source":0,"not_show":0},0],"赞",[39911917,"耳东立习习",0,0,0,10000,1,""],[20,"刺儿","逍遥散人",1017,13081892,"",0,13081892,13081892,13081892,0,1,168598],[31,0,10512625,"\u003e50000",0],["",""],0,0,null,{"ct":"E606F874","ts":1689072355},0,0,null,null,0,56,[0]],"is_report":false,"msg_id":"271830315699712","send_time":1689072355181}
                            // 过滤礼物自动弹幕 & 可能非主播勋章弹幕
                            if (barrage.getMsg_type() == 0 && is_xunzhang) {
                                //是否开启弹幕
                                if (is_barrage) {
                                    hbarrage = Hbarrage.copyHbarrage(barrage);
                                    if (barrage.getUid().equals(PublicDataConf.AUID)) {
                                        hbarrage.setManager((short) 2);
                                    }
                                    // 判断类型输出
                                    stringBuilder.append(JodaTimeUtils.formatDateTime(barrage.getTimestamp()));
                                    if (is_emoticon) {
                                        stringBuilder.append(":收到表情:");
                                    } else {
                                        stringBuilder.append(":收到弹幕:");
                                    }
                                    if (getCenterSetConf().is_barrage_vip()) {
                                        // 老爷
                                        stringBuilder.append(ParseIndentityTools.parseVip(barrage));
                                    } else {
                                        hbarrage.setVip((short) 0);
                                        hbarrage.setSvip((short) 0);
                                    }
                                    if (getCenterSetConf().is_barrage_guard()) {
                                        // 舰长
                                        stringBuilder.append(ParseIndentityTools.parseGuard(barrage.getUguard()));
                                    } else {
                                        hbarrage.setUguard((short) 0);
                                    }
                                    if (getCenterSetConf().is_barrage_manager()) {
                                        // 房管
                                        stringBuilder
                                                .append(ParseIndentityTools.parseManager(barrage.getUid(), barrage.getManager()));
                                    } else {
                                        hbarrage.setManager((short) 0);
                                    }
                                    if (getCenterSetConf().is_barrage_medal()) {
                                        // 勋章+勋章等级
                                        if (StringUtils.isNotBlank(barrage.getMedal_name())) {
                                            stringBuilder.append("[").append(barrage.getMedal_name()).append(" ")
                                                    .append(barrage.getMedal_level()).append("]");
                                        }
                                    } else {
                                        hbarrage.setMedal_level(null);
                                        hbarrage.setMedal_name(null);
                                        hbarrage.setMedal_room(null);
                                        hbarrage.setMedal_anchor(null);
                                    }
                                    if (getCenterSetConf().is_barrage_ul()) {
                                        // ul等级
                                        stringBuilder.append("[").append("UL").append(barrage.getUlevel()).append("]");
                                    } else {
                                        hbarrage.setUlevel(null);
                                    }


                                    stringBuilder.append(barrage.getUname());
                                    stringBuilder.append(" 它说:");
                                    stringBuilder.append(barrage.getMsg());
                                    //控制台打印
                                    if (getCenterSetConf().is_cmd()) {
                                        System.out.println(stringBuilder.toString());
                                    }
                                    //高级显示处理
                                    try {
                                        danmuWebsocket.sendMessage(WsPackage.toJson("danmu", (short) 0, hbarrage));
                                    } catch (Exception e) {
                                        // TODO 自动生成的 catch 块
                                        e.printStackTrace();
                                    }
                                    //日志处理
                                    if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                        PublicDataConf.logString.add(stringBuilder.toString());
                                        synchronized (PublicDataConf.logThread) {
                                            PublicDataConf.logThread.notify();
                                        }
                                    }
                                } else {
                                    //弹幕关闭
                                }
                                //自动回复姬处理
                                if (PublicDataConf.autoReplyThread != null && !PublicDataConf.autoReplyThread.FLAG) {
                                    if (!PublicDataConf.autoReplyThread.getState().toString().equals("TIMED_WAITING")) {
                                        if (parseAutoReplySetting(barrage)) {
                                            PublicDataConf.replys.add(
                                                    AutoReply.getAutoReply(barrage.getUid(), barrage.getUname(), barrage.getMsg()));
                                            synchronized (PublicDataConf.autoReplyThread) {
                                                PublicDataConf.autoReplyThread.notify();
                                            }
                                        }
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                                //						LOGGER.info("弹幕信息：" + message);
                            } else {
                                //				    LOGGER.info("test中貌似为礼物弹幕：" + message);
                            }
                            break;

                        // 送普通礼物
                        case "SEND_GIFT":
                            jsonObject = JSONObject.parseObject(jsonObject.getString("data"));
                            short gift_type = ParseIndentityTools.parseCoin_type(jsonObject.getString("coin_type"));
                            gift = Gift.getGift(jsonObject.getInteger("giftId"), jsonObject.getShort("giftType"),
                                    jsonObject.getString("giftName"), jsonObject.getInteger("num"),
                                    jsonObject.getString("uname"), jsonObject.getString("face"),
                                    jsonObject.getShort("guard_level"), jsonObject.getLong("uid"),
                                    jsonObject.getLong("timestamp"), jsonObject.getString("action"),
                                    jsonObject.getInteger("price"),
                                    gift_type,
                                    jsonObject.getLong("total_coin"), jsonObject.getObject("medal_info", MedalInfo.class));
                            if (getCenterSetConf().is_gift()) {
                                if (getCenterSetConf().is_gift_free() || (!getCenterSetConf().is_gift_free() && gift_type == 1)) {
                                    stringBuilder.append(JodaTimeUtils.formatDateTime(gift.getTimestamp() * 1000));
                                    stringBuilder.append(":收到道具:");
                                    stringBuilder.append(gift.getUname());
                                    stringBuilder.append(" ");
                                    stringBuilder.append(gift.getAction());
                                    stringBuilder.append("的:");
                                    stringBuilder.append(gift.getGiftName());
                                    stringBuilder.append(" x ");
                                    stringBuilder.append(gift.getNum());
                                    //控制台打印
                                    if (getCenterSetConf().is_cmd()) {
                                        System.out.println(stringBuilder.toString());
                                    }
                                    try {
                                        danmuWebsocket.sendMessage(WsPackage.toJson("gift", (short) 0, gift));
                                    } catch (Exception e) {
                                        // TODO 自动生成的 catch 块
                                        e.printStackTrace();
                                    }
                                    if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                        PublicDataConf.logString.add(stringBuilder.toString());
                                        synchronized (PublicDataConf.logThread) {
                                            PublicDataConf.logThread.notify();
                                        }
                                    }
                                    stringBuilder.delete(0, stringBuilder.length());
                                }
                            } else {
                                //礼物关闭
                            }
                            // 感谢礼物处理
                            if (gift != null && getCenterSetConf().getThank_gift().is_giftThank()) {
                                try {
                                    parseGiftSetting(gift);
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                            }
//                            LOGGER.info("让我看看是谁送礼物:::"+jsonObject);
                            break;

                        // 部分金瓜子礼物连击
                        case "COMBO_SEND":
                            //					LOGGER.info("部分金瓜子礼物连击:::" + message);
                            break;

                        // 部分金瓜子礼物连击
                        case "COMBO_END":
                            //					LOGGER.info("部分金瓜子礼物连击:::" + message);
                            break;

                        // 上舰
                        case "GUARD_BUY":
                            if (getCenterSetConf().is_gift()) {
                                guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
                                stringBuilder.append(JodaTimeUtils.formatDateTime(guard.getStart_time() * 1000));
                                stringBuilder.append(":有人上船:");
                                stringBuilder.append(guard.getUsername());
                                stringBuilder.append("在本房间开通了");
                                stringBuilder.append(guard.getNum());
                                stringBuilder.append("个月");
                                stringBuilder.append(guard.getGift_name());
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                gift = new Gift();
                                gift.setGiftName(guard.getGift_name());
                                gift.setNum(guard.getNum());
                                gift.setPrice(guard.getPrice());
                                gift.setTotal_coin((long) guard.getNum() * guard.getPrice());
                                gift.setTimestamp(guard.getStart_time());
                                gift.setAction("赠送");
                                gift.setCoin_type((short) 1);
                                gift.setUname(guard.getUsername());
                                gift.setUid(guard.getUid());
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("gift", (short) 0, gift));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            if (getCenterSetConf().getThank_gift().is_giftThank()) {
                                if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG) {
                                    guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
                                    gift = new Gift();
                                    gift.setGiftName(guard.getGift_name());
                                    gift.setNum(guard.getNum());
                                    gift.setPrice(guard.getPrice());
                                    gift.setTotal_coin((long) guard.getNum() * guard.getPrice());
                                    gift.setTimestamp(guard.getStart_time());
                                    gift.setAction("赠送");
                                    gift.setCoin_type((short) 1);
                                    gift.setUname(guard.getUsername());
                                    gift.setUid(guard.getUid());
                                    if (gift != null && getCenterSetConf().getThank_gift().is_giftThank()) {
                                        try {
                                            parseGiftSetting(gift);
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            //开启舰长存放本地
                            if (getCenterSetConf().getThank_gift().is_guard_local()) {
                                guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
                                Map<Long, String> guardMap_local = GuardFileTools.read();
                                if (guardMap_local == null) {
                                    guardMap_local = new ConcurrentHashMap<Long, String>();
                                }
                                //写入
                                if (!guardMap_local.containsKey(guard.getUid())) {
                                    GuardFileTools.write(guard.getUid() + "," + guard.getUsername());

                                }
                            }
                            // 发送上舰私聊
                            if (getCenterSetConf().getThank_gift().is_guard_report()) {
                                guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
                                short guard_level = guard.getGuard_level();
                                String report = StringUtils.replace(getCenterSetConf().getThank_gift().getReport(), "\n", "\\n");
                                //替换名称
                                report = StringUtils.replace(report, "%uName%", guard.getUsername());
                                //替换舰队级别
                                if (guard_level == 1) {
                                    report = StringUtils.replace(report, "%guardLevel%", "总督");
                                } else if (guard_level == 2) {
                                    report = StringUtils.replace(report, "%guardLevel%", "提督");
                                } else if (guard_level == 3) {
                                    report = StringUtils.replace(report, "%guardLevel%", "舰长");
                                } else {
                                    report = StringUtils.replace(report, "%guardLevel%", "上船");
                                }
                                //礼品码
                                if (getCenterSetConf().getThank_gift().is_gift_code()
                                        && !CollectionUtils.isEmpty(getCenterSetConf().getThank_gift().getCodeStrings())) {
                                    report = StringUtils.replace(report, "%giftCode%", this.sendCode(guard_level));
                                } else {
                                    report = StringUtils.replace(report, "%giftCode%", "");
                                }
                                try {
                                    if (!PublicDataConf.centerSetConf.isTest_mode()) {
                                        if (StringUtils.isNotBlank(getCenterSetConf().getThank_gift().getReport_barrage().trim())) {
                                            if (HttpUserData.httpPostSendMsg(guard.getUid(), report) == 0) {
                                                PublicDataConf.barrageString.add(getCenterSetConf().getThank_gift().getReport_barrage());
                                                synchronized (PublicDataConf.sendBarrageThread) {
                                                    PublicDataConf.sendBarrageThread.notify();
                                                }
                                            }
                                        } else {
                                            HttpUserData.httpPostSendMsg(guard.getUid(), report);
                                        }
                                    } else {
                                        LOGGER.info("私信姬：发送的弹幕:{}", getCenterSetConf().getThank_gift().getReport_barrage());
                                        LOGGER.info("私信姬：发送的私聊:{}", report);
                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    LOGGER.error("发送舰长私信失败，原因：" + e);
                                }
                            }
//                            LOGGER.info("有人上舰长啦:::" + message);
                            break;

                        // 上舰消息推送
                        case "GUARD_LOTTERY_START":
                            //					LOGGER.info("上舰消息推送:::" + message);
                            break;

                        // 上舰抽奖消息推送
                        case "USER_TOAST_MSG":
                            //					LOGGER.info("上舰抽奖消息推送:::" + message);
                            break;

                        // 醒目留言
                        case "SUPER_CHAT_MESSAGE":
                            if (getCenterSetConf().is_gift()) {
                                superChat = JSONObject.parseObject(jsonObject.getString("data"), SuperChat.class);
                                stringBuilder.append(JodaTimeUtils.formatDateTime(superChat.getStart_time() * 1000));
                                stringBuilder.append(":收到留言:");
                                stringBuilder.append(superChat.getUser_info().getUname());
                                stringBuilder.append(" 他用了");
                                //适配6.11破站更新金瓜子为电池  叔叔真有你的
                                stringBuilder.append(superChat.getPrice() * 10);
                                stringBuilder.append("电池留言了");
                                stringBuilder.append(ParseIndentityTools.parseTime(superChat.getTime()));
                                stringBuilder.append("秒说: ");
                                stringBuilder.append(superChat.getMessage());
                                superChat.setTime(ParseIndentityTools.parseTime(superChat.getTime()));
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("superchat", (short) 0, superChat));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }

                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            if (getCenterSetConf().getThank_gift().is_giftThank()) {
                                if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG) {
                                    superChat = JSONObject.parseObject(jsonObject.getString("data"), SuperChat.class);
                                    gift = new Gift();
                                    stringBuilder.append(ParseIndentityTools.parseTime(superChat.getTime()));
                                    stringBuilder.append("秒");
                                    stringBuilder.append(superChat.getGift().getGift_name());
                                    gift.setGiftName(stringBuilder.toString());
                                    gift.setNum(superChat.getGift().getNum());
                                    //适配6.11破站更新金瓜子为电池  叔叔真有你的
                                    gift.setPrice(superChat.getPrice() * 10);
                                    gift.setTotal_coin((long) superChat.getPrice() * 10l);
                                    gift.setTimestamp(superChat.getStart_time() * 1000);
                                    gift.setAction("赠送");
                                    gift.setCoin_type((short) 1);
                                    gift.setUname(superChat.getUser_info().getUname());
                                    gift.setUid(superChat.getUid());
                                    gift.setMedal_info(superChat.getMedal_info());
                                    if (gift != null && getCenterSetConf().getThank_gift().is_giftThank()) {
                                        try {
                                            parseGiftSetting(gift);
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            //					LOGGER.info("收到醒目留言:::" + message);
                            break;

                        // 醒目留言日文翻译
                        case "SUPER_CHAT_MESSAGE_JPN":
                            //					LOGGER.info("醒目留言日文翻译消息推送:::" + message);
                            break;

                        // 删除醒目留言
                        case "SUPER_CHAT_MESSAGE_DELETE":
                            //					LOGGER.info("该条醒目留言已被删除:::" + message);
                            break;

                        // 欢迎老爷进来本直播间
                        case "WELCOME":
                            // 区分年月费老爷
                            /*
                             * if(welcomVip.getSvip()==1) {
                             * System.out.println(JodaTimeUtils.getCurrentTimeString()+":欢迎年费老爷:"+welcomeVip
                             * .getUname()+" 进入直播间"); }else {
                             * System.out.println(JodaTimeUtils.getCurrentTimeString()+":欢迎月费老爷:"+welcomeVip
                             * .getUname()+" 进入直播间"); }
                             */
                            if (getCenterSetConf().is_welcome_ye()) {
                                welcomeVip = JSONObject.parseObject(jsonObject.getString("data"), WelcomeVip.class);
                                stringBuilder.append(JodaTimeUtils.getCurrentDateTimeString());
                                stringBuilder.append(":欢迎老爷:");
                                stringBuilder.append(welcomeVip.getUname());
                                stringBuilder.append(" 进入直播间");
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("welcomeVip", (short) 0, welcomeVip));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }

                            //					LOGGER.info("让我看看哪个老爷大户进来了:::" + message);
                            break;

                        // 欢迎舰长进入直播间
                        case "WELCOME_GUARD":

                            if (getCenterSetConf().is_welcome_ye()) {
                                welcomeGuard = JSONObject.parseObject(jsonObject.getString("data"), WelcomeGuard.class);
                                stringBuilder.append(JodaTimeUtils.getCurrentDateTimeString());
                                switch (welcomeGuard.getGuard_level()) {
                                    case 3:
                                        stringBuilder.append(":欢迎舰长:");
                                        break;
                                    case 2:
                                        stringBuilder.append(":欢迎提督:");
                                        break;
                                    case 1:
                                        stringBuilder.append(":欢迎总督:");
                                        break;
                                }
                                stringBuilder.append(welcomeGuard.getUsername());
                                stringBuilder.append(" 进入直播间");
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("welcomeGuard", (short) 0, welcomeGuard));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            //					LOGGER.info("舰长大大进来直播间了:::" + message);
                            break;

                        // 舰长进入直播间消息
                        case "ENTRY_EFFECT":
                            //					LOGGER.info("舰长大大进入直播间消息推送:::" + message);
                            break;

                        // 节奏风暴推送 action 为start和end
                        case "SPECIAL_GIFT":
                            //					LOGGER.info("节奏风暴推送:::" + message);
                            break;

                        // 禁言消息
                        case "ROOM_BLOCK_MSG":
                            if (getCenterSetConf().is_block()) {
                                blockMessage = JSONObject.parseObject(jsonObject.getString("data"), BlockMessage.class);
                                stringBuilder.append(JodaTimeUtils.getCurrentDateTimeString());
                                stringBuilder.append(":禁言消息:");
                                stringBuilder.append(blockMessage.getUname());
                                if (blockMessage.getOperator() == 2) {
                                    stringBuilder.append("已被主播禁言");
                                } else if (blockMessage.getOperator() == 1) {
                                    stringBuilder.append("已被房管禁言");
                                } else {
                                    stringBuilder.append("已被管理员禁言");
                                }
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("block", (short) 0, blockMessage));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            //					LOGGER.info("谁这么惨被禁言了:::" + message);
                            break;

                        // 本主播在本分区小时榜排名更新推送 不会更新页面的排行显示信息
                        case "ACTIVITY_BANNER_UPDATE_V2":
                            //					LOGGER.info("小时榜消息更新推送:::" + message);
                            break;

                        // 本房间分区修改
                        case "ROOM_CHANGE":
                            //					LOGGER.info("房间分区已更新:::" + message);
                            break;

                        // 本房间分区排行榜更新 更新页面的排行显示信息
                        case "ROOM_RANK":
                            //					rannk = JSONObject.parseObject(jsonObject.getString("data"), Rannk.class);
                            //					stringBuilder.append(JodaTimeUtils.format(rannk.getTimestamp() * 1000)).append(":榜单更新:")
                            //							.append(rannk.getRank_desc());
                            //
                            //					System.out.println(stringBuilder.toString());
                            //					stringBuilder.delete(0, stringBuilder.length());
                            //					LOGGER.info("小时榜信息更新推送:::" + message);
                            break;

                        // 推测为获取本小时榜榜单第一名主播的信息 推测激活条件为本直播间获得第一
                        case "new_anchor_reward":
                            //					LOGGER.info("获取本小时榜榜单第一名主播的信息:::" + message);
                            break;

                        // 小时榜榜单信息推送 推测激活条件为本直播间获得第一
                        case "HOUR_RANK_AWARDS":
                            //					LOGGER.info("恭喜xxxx直播间获得:::" + message);
                            break;

                        // 直播间粉丝数更新 经常
                        case "ROOM_REAL_TIME_MESSAGE_UPDATE":
                            //					fans = JSONObject.parseObject(jsonObject.getString("data"), Fans.class);
                            //					stringBuilder.append(JodaTimeUtils.getCurrentTimeString()).append(":消息推送:").append("房间号")
                            //							.append(fans.getRoomid()).append("的粉丝数:").append(fans.getFans());
                            PublicDataConf.FANSNUM = JSONObject.parseObject(jsonObject.getString("data")).getLong("fans");
                            //					System.out.println(stringBuilder.toString());
                            //					stringBuilder.delete(0, stringBuilder.length());
                            //					LOGGER.info("直播间粉丝数更新消息推送:::" + message);
                            break;

                        // 直播间许愿瓶消息推送更新
                        case "WISH_BOTTLE":
                            //					LOGGER.info("直播间许愿瓶消息推送更新:::" + message);
                            break;

                        // 广播小电视类抽奖信息推送,包括本房间的舰长礼物包括,本直播间所在小时榜榜单主播信息的推送 需要unicode转义 免费辣条再见！！！！
                        case "NOTICE_MSG":
                            //					message = ByteUtils.unicodeToString(message);
                            //					LOGGER.info("小电视类抽奖信息推送:::" + message);
                            break;

                        // 本房间开启活动抽奖(33图,小电视图,任意门等) 也指本房间内赠送的小电视 摩天大楼类抽奖
                        case "RAFFLE_START":
                            //					LOGGER.info("本房间开启了活动抽奖:::" + message);
                            break;

                        // 本房间活动中奖用户信息推送 也指抽奖结束
                        case "RAFFLE_END":
                            //					LOGGER.info("看看谁是幸运儿:::" + message);
                            break;

                        // 本房间主播开启了天选时刻
                        case "ANCHOR_LOT_START":
//                            LOGGER.info("本房间主播开启了天选时刻:::" + message);
                            if (StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
                                try {
                                    //屏蔽礼物
                                    if (PublicDataConf.centerSetConf.getThank_gift().hasTxShield()
                                            || PublicDataConf.centerSetConf.getWelcome().hasTxShield()
                                            || PublicDataConf.centerSetConf.getFollow().hasTxShield()) {
                                        //检查天选
                                        String giftName = ((JSONObject) jsonObject.get("data")).getString("award_name");
                                        int time = ((JSONObject) jsonObject.get("data")).getInteger("time");
                                        CurrencyTools.handleLotteryInfoWebByTx(PublicDataConf.ROOMID, giftName, time);
                                    }
//                                    if (getCenterSetConf().getThank_gift().is_tx_shield()) {
//                                        if (PublicDataConf.parsethankGiftThread != null
//                                                && !PublicDataConf.parsethankGiftThread.TFLAG
//                                                && !PublicDataConf.giftShieldThread.getState().toString().equals("RUNNABLE")) {
//                                            String giftName = ((JSONObject) jsonObject.get("data")).getString("gift_name");
//                                            int time = ((JSONObject) jsonObject.get("data")).getInteger("time");
//                                            if (StringUtils.isNotBlank(giftName)) {
//                                                if (PublicDataConf.giftShieldThread.getState().toString().equals("TERMINATED")
//                                                        || PublicDataConf.giftShieldThread.getState().toString()
//                                                        .equals("NEW")) {
//                                                    PublicDataConf.giftShieldThread = new GiftShieldThread();
//                                                    PublicDataConf.giftShieldThread.FLAG = false;
//                                                    PublicDataConf.giftShieldThread.setGiftName(giftName);
//                                                    PublicDataConf.giftShieldThread
//                                                            .setTime(ParseIndentityTools.parseTime(time));
//                                                    PublicDataConf.giftShieldThread.start();
//                                                } else {
//                                                    PublicDataConf.giftShieldThread.setTime(time);
//                                                    PublicDataConf.giftShieldThread.setGiftName(giftName);
//                                                }
//                                            }
//                                        }
//                                    }
//                                    //开启天选 屏蔽关注
//                                    if (getCenterSetConf().getFollow().is_tx_shield()) {
//                                        if (PublicDataConf.parsethankFollowThread != null
//                                                && !PublicDataConf.parsethankFollowThread.FLAG
//                                                && !PublicDataConf.followShieldThread.getState().toString()
//                                                .equals("RUNNABLE")) {
//                                            int time = ((JSONObject) jsonObject.get("data")).getInteger("time");
//                                            if (PublicDataConf.followShieldThread.getState().toString().equals("TERMINATED")
//                                                    || PublicDataConf.followShieldThread.getState().toString().equals("NEW")) {
//                                                PublicDataConf.followShieldThread = new FollowShieldThread();
//                                                PublicDataConf.followShieldThread.FLAG = false;
//                                                PublicDataConf.followShieldThread.setTime(time);
//                                                PublicDataConf.followShieldThread.start();
//                                            } else {
//                                                PublicDataConf.followShieldThread.setTime(time);
//                                            }
//                                        }
//                                    }
//                                    //开启天选 屏蔽欢迎
//                                    if (getCenterSetConf().getWelcome().is_tx_shield()) {
//                                        if (PublicDataConf.parseThankWelcomeThread != null
//                                                && !PublicDataConf.parseThankWelcomeThread.FLAG
//                                                && !PublicDataConf.welcomeShieldThread.getState().toString()
//                                                .equals("RUNNABLE")) {
//                                            int time = ((JSONObject) jsonObject.get("data")).getInteger("time");
//                                            if (PublicDataConf.welcomeShieldThread.getState().toString().equals("TERMINATED")
//                                                    || PublicDataConf.welcomeShieldThread.getState().toString().equals("NEW")) {
//                                                PublicDataConf.welcomeShieldThread = new WelcomeShieldThread();
//                                                PublicDataConf.welcomeShieldThread.FLAG = false;
//                                                PublicDataConf.welcomeShieldThread.setTime(time);
//                                                PublicDataConf.welcomeShieldThread.start();
//                                            } else {
//                                                PublicDataConf.welcomeShieldThread.setTime(time);
//                                            }
//                                        }
//                                    }

                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
                            //					LOGGER.info("本房间主播开启了天选时刻:::" + message);
                            break;

                        // 本房间天选时刻结束
                        case "ANCHOR_LOT_END":
                            //					LOGGER.info("本房间天选时刻结束:::" + message);
                            break;

                        // 本房间天选时刻获奖信息推送
                        case "ANCHOR_LOT_AWARD":
                            //					LOGGER.info("本房间天选时刻中奖用户是:::" + message);
                            break;

                        // 获得推荐位推荐消息
                        case "ANCHOR_NORMAL_NOTIFY":
                            //					LOGGER.info("本房间获得推荐位:::" + message);
                            break;
                        // 周星消息推送
                        case "WEEK_STAR_CLOCK":
                            //			        LOGGER.info("周星消息推送:::" + message);
                            break;

                        // 推测本主播周星信息更新
                        case "ROOM_BOX_MASTER":
                            //					LOGGER.info("周星信息更新:::" + message);
                            break;

                        // 周星消息推送关闭
                        case "ROOM_SKIN_MSG":
                            //					LOGGER.info("周星消息推送关闭:::" + message);
                            break;

                        // 中型礼物多数量赠送消息推送 例如b克拉 亿元
                        case "SYS_GIFT":
                            //					LOGGER.info("中型礼物多数量赠送消息推送:::" + message);
                            break;

                        // lol活动礼物？？？
                        case "ACTIVITY_MATCH_GIFT":
                            //					LOGGER.info("lol专属房间礼物赠送消息推送:::" + message);
                            break;

                        //----------------------------------------pk信息多为要uicode解码-------------------------------------------------
                        // 推测为房间pk信息推送
                        case "PK_BATTLE_ENTRANCE":
                            //			        LOGGER.info("房间pk活动信息推送:::" + message);
                            break;

                        // 活动pk准备
                        case "PK_BATTLE_PRE":
                            //			        LOGGER.info("房间活动pk准备:::" + message);
                            break;

                        // 活动pk开始
                        case "PK_BATTLE_START":
                            //			        LOGGER.info("房间活动pk开始:::" + message);
                            break;

                        // 活动pk中
                        case "PK_BATTLE_PROCESS":
                            //			        LOGGER.info("房间活动pk中:::" + message);
                            break;

                        // 活动pk详细信息
                        case "PK_BATTLE_CRIT":
                            //			        LOGGER.info("房间活动pk详细信息推送:::" + message);
                            break;

                        // 活动pk类型推送
                        case "PK_BATTLE_PRO_TYPE":
                            //			        LOGGER.info("房间活动pk类型推送:::" + message);
                            break;

                        // 房间活动pk结束
                        case "PK_BATTLE_END":
                            //			        LOGGER.info("房间pk活动结束::" + message);
                            break;

                        // 活动pk结果用户 推送
                        case "PK_BATTLE_SETTLE_USER":
                            //			        LOGGER.info("活动pk结果用户 推送::" + message);
                            break;

                        // 活动pk礼物开始 1辣条
                        case "PK_LOTTERY_START":
                            //			        LOGGER.info("活动pk礼物开始 推送::" + message);
                            break;

                        // 活动pk结果房间
                        case "PK_BATTLE_SETTLE":
                            //			        LOGGER.info("活动pk结果房间推送::" + message);
                            break;

                        // pk开始
                        case "PK_START":
                            //					LOGGER.info("房间pk开始:::" + message);
                            break;

                        // pk准备中
                        case "PK_PRE":
                            //					LOGGER.info("房间pk准备中:::" + message);
                            break;

                        // pk载入中
                        case "PK_MATCH":
                            //					LOGGER.info("房间pk载入中:::" + message);
                            break;

                        // pk再来一次触发
                        case "PK_CLICK_AGAIN":
                            //					LOGGER.info("房间pk再来一次:::" + message);
                            break;
                        // pk结束
                        case "PK_MIC_END":
                            //					LOGGER.info("房间pk结束:::" + message);
                            break;

                        // pk礼物信息推送 激活条件推测为pk胜利 可获得一个辣条
                        case "PK_PROCESS":
                            //					LOGGER.info("房间pk礼物推送:::" + message);
                            break;

                        // pk结果信息推送
                        case "PK_SETTLE":
                            //					LOGGER.info("房间pk结果信息推送:::" + message);
                            break;

                        // pk结束信息推送
                        case "PK_END":
                            //					LOGGER.info("房间pk结束信息推送:::" + message);
                            break;

                        // 系统信息推送
                        case "SYS_MSG":
                            //					LOGGER.info("系统信息推送:::" + message);
                            break;

                        // 总督登场消息
                        case "GUARD_MSG":
                            //					LOGGER.info("总督帅气登场:::" + message);
                            break;

                        // 热门房间？？？？广告房间？？ 不知道这是什么 推测本直播间激活 目前常见于打广告的官方直播间 例如手游 碧蓝航线 啥的。。
                        case "HOT_ROOM_NOTIFY":
                            //					LOGGER.info("热门房间推送消息:::" + message);
                            break;

                        // 小时榜面板消息推送
                        case "PANEL":
                            //					LOGGER.info("热小时榜面板消息推送:::" + message);
                            break;

                        // 星之耀宝箱使用 n
                        case "ROOM_BOX_USER":
                            //					LOGGER.info("星之耀宝箱使用:::" + message);
                            break;

                        // 语音加入？？？？ 暂不知道
                        case "VOICE_JOIN_ROOM_COUNT_INFO":
                            //					LOGGER.info("语音加入:::" + message);
                            break;

                        // 语音加入list？？？？ 暂不知道
                        case "VOICE_JOIN_LIST":
                            //					LOGGER.info("语音加入list:::" + message);
                            break;

                        // lol活动
                        case "LOL_ACTIVITY":
                            //					LOGGER.info("lol活动:::" + message);
                            break;

                        // 队伍礼物排名 目前只在6号lol房间抓取过
                        case "MATCH_TEAM_GIFT_RANK":
                            //					LOGGER.info("队伍礼物排名:::" + message);
                            break;

                        // 6.13端午节活动粽子新增活动更新命令 激活条件有人赠送活动礼物
                        case "ROOM_BANNER":
                            //					LOGGER.info("收到活动礼物赠送，更新信息:::" + message);
                            break;

                        // 设定房管消息 新房管的诞生
                        case "room_admin_entrance":
                            //					LOGGER.info("有人被设为了房管:::" + message);
                            break;

                        // 房管列表更新消息 激活条件为新房管的诞生
                        case "ROOM_ADMINS":
                            //					LOGGER.info("房管列表更新推送:::" + message);
                            break;

                        // 房间护盾 推测推送消息为破站官方屏蔽的关键字 触发条件未知
                        case "ROOM_SHIELD":
                            //					LOGGER.info("房间护盾触发消息:::" + message);
                            break;

                        // 主播开启房间全局禁言
                        case "ROOM_SILENT_ON":
                            //					LOGGER.info("主播开启房间全局禁言:::" + message);
                            break;

                        // 主播关闭房间全局禁言
                        case "ROOM_SILENT_OFF":
                            //					LOGGER.info("主播关闭房间全局禁言:::" + message);
                            break;

                        // 主播状态检测 直译 不知道什么情况 statue 1 ，2 ，3 ，4
                        case "ANCHOR_LOT_CHECKSTATUS":
                            //					LOGGER.info("主播房间状态检测:::" + message);
                            break;

                        // 房间警告消息 目前已知触发条件为 房间分区不正确
                        case "WARNING":
                            //					LOGGER.info("房间警告消息:::" + message);
                            break;
                        // 直播开启
                        case "LIVE":
                            PublicDataConf.lIVE_STATUS = 1;
                            //					room_id = jsonObject.getLong("roomid");
                            //					if (room_id == PublicDataConf.ROOMID) {
                            // 仅在直播有效 广告线程 改为配置文件
                            setService.holdSet(getCenterSetConf());
                            PublicDataConf.IS_ROOM_POPULARITY = true;
                            //					LOGGER.info("直播开启:::" + message);
                            break;

                        // 直播超管被切断
                        case "CUT_OFF":
                            //					LOGGER.info("很不幸，本房间直播被切断:::" + message);
                            break;

                        // 本房间已被封禁
                        case "ROOM_LOCK":
                            //					LOGGER.info("很不幸，本房间已被封禁:::" + message);
                            break;

                        // 直播准备中(或者是关闭直播)
                        case "PREPARING":
                            PublicDataConf.lIVE_STATUS = 0;
                            setService.holdSet(getCenterSetConf());
                            PublicDataConf.IS_ROOM_POPULARITY = false;
                            //					LOGGER.info("直播准备中(或者是关闭直播):::" + message);
                            break;

                        // 勋章亲密度达到上每日上限通知
                        case "LITTLE_TIPS":
                            //					LOGGER.info("勋章亲密度达到上每日上限:::" + message);
                            break;

                        // msg_type 1 为进入直播间 2 为关注 3为分享直播间
                        case "INTERACT_WORD":
                            // 关注
                            //控制台打印处理
                            if (getCenterSetConf().is_follow_dm()) {
                                msg_type = JSONObject.parseObject(jsonObject.getString("data")).getShort("msg_type");
                                if (msg_type == 2) {
                                    interact = JSONObject.parseObject(jsonObject.getString("data"), Interact.class);
                                    stringBuilder.append(JodaTimeUtils.formatDateTime(System.currentTimeMillis())).append(":新的关注:")
                                            .append(interact.getUname()).append(" 关注了直播间");
                                    //控制台打印
                                    if (getCenterSetConf().is_cmd()) {
                                        System.out.println(stringBuilder.toString());
                                    }
                                    //日志
                                    if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                        PublicDataConf.logString.add(stringBuilder.toString());
                                        synchronized (PublicDataConf.logThread) {
                                            PublicDataConf.logThread.notify();
                                        }
                                    }
                                    //前端弹幕发送
                                    try {
                                        danmuWebsocket.sendMessage(WsPackage.toJson("follow", (short) 0, interact));
                                    } catch (Exception e) {
                                        // TODO 自动生成的 catch 块
                                        e.printStackTrace();
                                    }
                                    stringBuilder.delete(0, stringBuilder.length());
                                }
                            }
                            //关注感谢
                            if (getCenterSetConf().getFollow().is_followThank()) {
                                //天选屏蔽&&红包屏蔽
                                if (!getCenterSetConf().getFollow()
                                        .boolTxAndRdShield(
                                                CacheConf.existTx(PublicDataConf.ROOMID), CacheConf.existRedPackageCache(PublicDataConf.ROOMID))) {
                                    msg_type = JSONObject.parseObject(jsonObject.getString("data")).getShort("msg_type");
                                    if (msg_type == 2) {
                                        interact = JSONObject.parseObject(jsonObject.getString("data"), Interact.class);
                                        try {
                                            parseFollowSetting(interact);
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            //欢迎进入直播间
                            if (getCenterSetConf().is_welcome_all()) {
                                msg_type = JSONObject.parseObject(jsonObject.getString("data")).getShort("msg_type");
                                if (msg_type == 1) {
                                    interact = JSONObject.parseObject(jsonObject.getString("data"), Interact.class);
                                    stringBuilder.append(JodaTimeUtils.formatDateTime(System.currentTimeMillis())).append(":新的访客:")
                                            .append(interact.getUname()).append(" 进入了直播间");
                                    //控制台打印
                                    if (getCenterSetConf().is_cmd()) {
                                        System.out.println(stringBuilder.toString());
                                    }
                                    //日志
                                    if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                        PublicDataConf.logString.add(stringBuilder.toString());
                                        synchronized (PublicDataConf.logThread) {
                                            PublicDataConf.logThread.notify();
                                        }
                                    }
                                    //前端显示
                                    try {
                                        danmuWebsocket.sendMessage(WsPackage.toJson("welcome", (short) 0, interact));
                                    } catch (Exception e) {
                                        // TODO 自动生成的 catch 块
                                        e.printStackTrace();
                                    }
                                    stringBuilder.delete(0, stringBuilder.length());
                                }
                            }
                            //欢迎感谢
                            if (getCenterSetConf().getWelcome().is_welcomeThank()) {
                                //天选屏蔽&&红包屏蔽
                                if (!getCenterSetConf().getWelcome()
                                        .boolTxAndRdShield(
                                                CacheConf.existTx(PublicDataConf.ROOMID), CacheConf.existRedPackageCache(PublicDataConf.ROOMID))) {
                                    msg_type = JSONObject.parseObject(jsonObject.getString("data")).getShort("msg_type");
                                    if (msg_type == 1) {
                                        interact = JSONObject.parseObject(jsonObject.getString("data"), Interact.class);
                                        try {
                                            parseWelcomeSetting(interact);
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            //打印测试用
//                            msg_type = JSONObject.parseObject(jsonObject.getString("data")).getShort("msg_type");
//                            if (msg_type != 3 && msg_type != 2 && msg_type != 1) {
//                                LOGGER.info("直播间信息:::" + message);
//                            }
                            break;
                        case "INTERACT_WORD_V2":
//                            LOGGER.info("INTERACT_WORD_V2:" + message);
                            try {
                                String pbBase64 = jsonObject.getJSONObject("data").getString("pb");
                                INTERACTWORDV2.InteractWordV2 interactWordV2 = INTERACTWORDV2.InteractWordV2.parseFrom(Base64.getDecoder().decode(pbBase64));
//                                LOGGER.info("INTERACT_WORD_V2_PARSE:" + JsonFormat.printer().print(interactWordV2));
                                msg_type =(short) interactWordV2.getMsgType();
                                interact = new Interact();
                                interact.setUid(interactWordV2.getUid());
                                interact.setUname(interactWordV2.getUname());
                                interact.setUname_color("");
                                interact.setIdentities(interactWordV2.getIdentitiesList().stream().map(Long::intValue)
                                        .toArray(Integer[]::new));
                                interact.setMsg_type((short)0);
                                interact.setRoomid(interactWordV2.getRoomid());
                                interact.setTimestamp(interactWordV2.getTimestamp());
                                interact.setScore(interactWordV2.getScore());
                                if(interactWordV2.hasFansMedal()) {
//                                    LOGGER.info("INTERACT_WORD_V2_PARSE_FANS:" + JsonFormat.printer().print(interactWordV2));
                                    MedalInfo medalInfo = new MedalInfo();
                                    medalInfo.setIcon_id(interactWordV2.getFansMedal().getIconId());
                                    medalInfo.setTarget_id(interactWordV2.getFansMedal().getTargetId());
                                    medalInfo.setSpecial(interactWordV2.getFansMedal().getSpecial());
                                    medalInfo.setAnchor_uname("");
                                    medalInfo.setAnchor_roomid(String.valueOf(interactWordV2.getFansMedal().getAnchorRoomid()));
                                    medalInfo.setMedal_level((short) interactWordV2.getFansMedal().getMedalLevel());
                                    medalInfo.setMedal_name(interactWordV2.getFansMedal().getMedalName());
                                    medalInfo.setMedal_color(String.valueOf(interactWordV2.getFansMedal().getMedalColor()));
                                    medalInfo.setIs_lighted((int) interactWordV2.getFansMedal().getIsLighted());
                                    medalInfo.setGuard_level((short) interactWordV2.getFansMedal().getGuardLevel());
                                    interact.setFans_medal(medalInfo);
                                }

                                // 关注
                                //控制台打印处理
                                if (getCenterSetConf().is_follow_dm()) {
                                    if (msg_type == 2) {
                                        stringBuilder.append(JodaTimeUtils.formatDateTime(System.currentTimeMillis())).append(":新的关注:")
                                                .append(interact.getUname()).append(" 关注了直播间");
                                        //控制台打印
                                        if (getCenterSetConf().is_cmd()) {
                                            System.out.println(stringBuilder.toString());
                                        }
                                        //日志
                                        if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                            PublicDataConf.logString.add(stringBuilder.toString());
                                            synchronized (PublicDataConf.logThread) {
                                                PublicDataConf.logThread.notify();
                                            }
                                        }
                                        //前端弹幕发送
                                        try {
                                            danmuWebsocket.sendMessage(WsPackage.toJson("follow", (short) 0, interact));
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                        stringBuilder.delete(0, stringBuilder.length());
                                    }
                                }
                                //关注感谢
                                if (getCenterSetConf().getFollow().is_followThank()) {
                                    //天选屏蔽&&红包屏蔽
                                    if (!getCenterSetConf().getFollow()
                                            .boolTxAndRdShield(
                                                    CacheConf.existTx(PublicDataConf.ROOMID), CacheConf.existRedPackageCache(PublicDataConf.ROOMID))) {
                                        if (msg_type == 2) {
                                            try {
                                                parseFollowSetting(interact);
                                            } catch (Exception e) {
                                                // TODO 自动生成的 catch 块
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                //欢迎进入直播间
                                if (getCenterSetConf().is_welcome_all()) {
                                    if (msg_type == 1) {
                                        stringBuilder.append(JodaTimeUtils.formatDateTime(System.currentTimeMillis())).append(":新的访客:")
                                                .append(interact.getUname()).append(" 进入了直播间");
                                        //控制台打印
                                        if (getCenterSetConf().is_cmd()) {
                                            System.out.println(stringBuilder.toString());
                                        }
                                        //日志
                                        if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                            PublicDataConf.logString.add(stringBuilder.toString());
                                            synchronized (PublicDataConf.logThread) {
                                                PublicDataConf.logThread.notify();
                                            }
                                        }
                                        //前端显示
                                        try {
                                            danmuWebsocket.sendMessage(WsPackage.toJson("welcome", (short) 0, interact));
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                        stringBuilder.delete(0, stringBuilder.length());
                                    }
                                }
                                //欢迎感谢
                                if (getCenterSetConf().getWelcome().is_welcomeThank()) {
                                    //天选屏蔽&&红包屏蔽
                                    if (!getCenterSetConf().getWelcome()
                                            .boolTxAndRdShield(
                                                    CacheConf.existTx(PublicDataConf.ROOMID), CacheConf.existRedPackageCache(PublicDataConf.ROOMID))) {
                                        if (msg_type == 1) {
                                            try {
                                                parseWelcomeSetting(interact);
                                            } catch (Exception e) {
                                                // TODO 自动生成的 catch 块
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        // 礼物bag bot
                        case "GIFT_BAG_DOT":
                            //					LOGGER.info("礼物bag" + message);
                            break;
                        case "ONLINERANK":
                            //					LOGGER.info("新在线排名更新信息推送:::" + message);
                            break;
                        case "ONLINE_RANK_COUNT":
                            //					LOGGER.info("在线排名人数更新信息推送:::" + message);
                            break;
                        case "ONLINE_RANK_V2":
                            //					LOGGER.info("在线排名v2版本信息推送(即高能榜:::" + message);
                            break;
                        case "ONLINE_RANK_TOP3":
                            //					LOGGER.info("在线排名前三信息推送(即高能榜:::" + message);
                            break;
                        case "HOT_RANK_CHANGED":
                            //					LOGGER.info("热门榜推送:::" + message);
                            break;
                        case "HOT_RANK_CHANGED_V2":
                            //					LOGGER.info("热门榜v2版本changed推送:::" + message);
                            break;
                        case "HOT_RANK_SETTLEMENT_V2":
                            //					LOGGER.info("热门榜v2版本set推送:::" + message);
                            break;
                        case "WIDGET_BANNER":
                            //					LOGGER.info("直播横幅广告推送:::" + message);
                            break;
                        case "MESSAGEBOX_USER_MEDAL_CHANGE":
                            //					LOGGER.info("本人勋章升级推送:::" + message);
                            break;
                        case "LIVE_INTERACTIVE_GAME":
                            //					LOGGER.info("互动游戏？？？推送:::" + message);
                            break;
                        case "WATCHED_CHANGE":
                            //{"cmd":"WATCHED_CHANGE","data":{"num":184547,"text_small":"18.4万","text_large":"18.4万人看过"}}
                            PublicDataConf.ROOM_WATCHER = JSONObject.parseObject(jsonObject.getString("data")).getLong("num");
//                            LOGGER.info("多少人观看过:::" + message);
                            break;
                        case "STOP_LIVE_ROOM_LIST":
                            //					LOGGER.info("直播间关闭集合推送:::" + message);
                            break;
                        case "DANMU_AGGREGATION":
                            //					LOGGER.info("天选时刻条件是表情推送:::" + message);
                            break;
                        case "COMMON_NOTICE_DANMAKU":
                            //					LOGGER.info("警告信息推送（例如任务快完成之类的）:::" + message);
                            break;
                        case "POPULARITY_RED_POCKET_NEW":
                            //{"cmd":"POPULARITY_RED_POCKET_NEW",
                            // "data":{"lot_id":8677977,"start_time":1674572461,"current_time":1674572461,
                            // "wait_num":0,"uname":"直播小电视","uid":1407831746,"action":"送出",
                            // "num":1,"gift_name":"红包","gift_id":13000,"price":5000,"name_color":"",
                            // "medal_info":{"target_id":0,"special":"","icon_id":0,"anchor_uname":"",
                            // "anchor_roomid":0,"medal_level":0,"medal_name":"","medal_color":0,"medal_color_start":0,
                            // "medal_color_end":0,"medal_color_border":0,"is_lighted":0,"guard_level":0}}}
                            if (getCenterSetConf().is_gift()) {
                                redPackage = JSONObject.parseObject(jsonObject.getString("data"), RedPackage.class);
                                stringBuilder.append(JodaTimeUtils.formatDateTime(redPackage.getStart_time() * 1000));
                                stringBuilder.append(":收到红包:");
                                stringBuilder.append(redPackage.getUname());
                                stringBuilder.append(" ");
                                stringBuilder.append(redPackage.getAction());
                                stringBuilder.append("的:");
                                stringBuilder.append(redPackage.getGift_name());
                                stringBuilder.append(" x ");
                                stringBuilder.append(redPackage.getNum());
                                //控制台打印
                                if (getCenterSetConf().is_cmd()) {
                                    System.out.println(stringBuilder.toString());
                                }
                                gift = new Gift();
                                gift.setGiftName(redPackage.getGift_name());
                                gift.setNum(redPackage.getNum());
                                gift.setPrice(redPackage.getPrice());
                                gift.setTotal_coin((long) redPackage.getNum() * redPackage.getPrice());
                                gift.setTimestamp(redPackage.getStart_time());
                                gift.setAction(redPackage.getAction());
                                gift.setCoin_type((short) 1);
                                gift.setUname(redPackage.getUname());
                                gift.setUid(redPackage.getUid());
                                gift.setMedal_info(redPackage.getMedal_info());
                                try {
                                    danmuWebsocket.sendMessage(WsPackage.toJson("gift", (short) 0, gift));
                                } catch (Exception e) {
                                    // TODO 自动生成的 catch 块
                                    e.printStackTrace();
                                }
                                if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
                                    PublicDataConf.logString.add(stringBuilder.toString());
                                    synchronized (PublicDataConf.logThread) {
                                        PublicDataConf.logThread.notify();
                                    }
                                }
                                stringBuilder.delete(0, stringBuilder.length());
                            }
                            if (getCenterSetConf().getThank_gift().is_giftThank()) {
                                if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG) {
                                    redPackage = JSONObject.parseObject(jsonObject.getString("data"), RedPackage.class);
                                    gift = new Gift();
                                    gift.setGiftName(redPackage.getGift_name());
                                    gift.setNum(redPackage.getNum());
                                    gift.setPrice(redPackage.getPrice());
                                    gift.setTotal_coin((long) redPackage.getNum() * redPackage.getPrice());
                                    gift.setTimestamp(redPackage.getStart_time());
                                    gift.setAction(redPackage.getAction());
                                    gift.setCoin_type((short) 1);
                                    gift.setUname(redPackage.getUname());
                                    gift.setUid(redPackage.getUid());
                                    gift.setMedal_info(redPackage.getMedal_info());
                                    if (gift != null && getCenterSetConf().getThank_gift().is_giftThank()) {
                                        try {
                                            parseGiftSetting(gift);
                                        } catch (Exception e) {
                                            // TODO 自动生成的 catch 块
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            //					LOGGER.info("红包赠送:::" + message);
                            break;
                        case "POPULARITY_RED_POCKET_WINNER_LIST":
                            //					LOGGER.info("红包抽奖结果推送:::" + message);
                            break;
                        case "LIKE_INFO_V3_UPDATE":
//                            					LOGGER.info("点赞信息v3推送:::" + message);
                            //{"cmd":"LIKE_INFO_V3_UPDATE","data":{"click_count":371578}}
                            PublicDataConf.ROOM_LIKE = JSONObject.parseObject(jsonObject.getString("data")).getLong("click_count");
                            break;
                        case "LIKE_INFO_V3_CLICK":
                            //					LOGGER.info("点赞信息v3推送:::" + message);
                            break;
                        case "CORE_USER_ATTENTION":
                            //					LOGGER.info("中心用户推送:::" + message);
                            break;
                        case "HOT_RANK_SETTLEMENT":
                            //					LOGGER.info("热榜排名推送:::" + message);
                            break;
                        case "MESSAGEBOX_USER_GAIN_MEDAL":
                            //					LOGGER.info("粉丝勋章消息盒子推送:::" + message);
                            break;
                        case "POPULARITY_RED_POCKET_START":
//                        {"cmd":"POPULARITY_RED_POCKET_START", "data":{"lot_id":15279655,
//                                "sender_uid":300700903,"sender_name":"肉嘟嘟喽","sender_face":"https://i1.hdslb.com/bfs/face/3af2996893e1c77561fd9422e42bfb7a06292b23.jpg",
//                                "join_requirement":1,"danmu":"中奖喷雾！中奖喷雾！","current_time":1698408158,"start_time":1698408158,"end_time":1698408338,
//                                "last_time":180,"remove_time":1698408353,"replace_time":1698408348,"lot_status":1,
//                                "h5_url":"https://live.bilibili.com/p/html/live-app-red-envelope/popularity.html?is_live_half_webview=1\u0026hybrid_half_ui=1,5,100p,100p,000000,0,50,0,0,1;2,5,100p,100p,000000,0,50,0,0,1;3,5,100p,100p,000000,0,50,0,0,1;4,5,100p,100p,000000,0,50,0,0,1;5,5,100p,100p,000000,0,50,0,0,1;6,5,100p,100p,000000,0,50,0,0,1;7,5,100p,100p,000000,0,50,0,0,1;8,5,100p,100p,000000,0,50,0,0,1\u0026hybrid_rotate_d=1\u0026hybrid_biz=popularityRedPacket\u0026lotteryId=15279655",
//                                "user_status":2,"awards":[{"gift_id":31212,"gift_name":"打call","gift_pic":"https://s1.hdslb.com/bfs/live/461be640f60788c1d159ec8d6c5d5cf1ef3d1830.png",
//                                "num":2},{"gift_id":31214,"gift_name":"牛哇","gift_pic":"https://s1.hdslb.com/bfs/live/91ac8e35dd93a7196325f1e2052356e71d135afb.png","num":3},{"gift_id":31216,
//                                "gift_name":"小花花","gift_pic":"https://s1.hdslb.com/bfs/live/5126973892625f3a43a8290be6b625b5e54261a5.png","num":3}],"lot_config_id":3,"total_price":1600,"wait_num":7}}
//                        LOGGER.info("红包详细信息推送:::" + message);
                            if ((PublicDataConf.centerSetConf.getThank_gift().hasRdShield())
                                    || (PublicDataConf.centerSetConf.getWelcome().hasRdShield())
                                    || (PublicDataConf.centerSetConf.getFollow().hasRdShield())) {
                                LotteryInfoWeb.PopularityRedPocket popularityRedPocket = jsonObject.getJSONObject("data").toJavaObject(LotteryInfoWeb.PopularityRedPocket.class);
                                LotteryInfoWeb lotteryInfoWeb = new LotteryInfoWeb();
                                lotteryInfoWeb.setPopularity_red_pocket(Arrays.asList(popularityRedPocket));
                                CurrencyTools.handleLotteryInfoWebByRedPackage(PublicDataConf.ROOMID, lotteryInfoWeb);
                            }
                            break;
                        case "LITTLE_MESSAGE_BOX":
                            //					LOGGER.info("小消息box推送:::" + message);
                            break;
                        case "ANCHOR_HELPER_DANMU":
                            //					LOGGER.info("直播小助手信息推送:::" + message);
                            break;
                        case "ENTRY_EFFECT_MUST_RECEIVE":
                            //					LOGGER.info("直播小助手信息推送:::" + message);
                            break;
                        case "GIFT_STAR_PROCESS":
                            //					LOGGER.info("礼物开始进度条信息推送:::" + message);
                            break;
                        case "GUARD_HONOR_THOUSAND":
                            //					LOGGER.info("千舰推送:::" + message);
                            break;
                        case "FULL_SCREEN_SPECIAL_EFFECT":
                            //					LOGGER.info("FULL_SCREEN_SPECIAL_EFFECT:::" + message);
                            break;
                        case "CARD_MSG":
                            //					LOGGER.info("CARD_MSG:::" + message);
                            break;
                        case "USER_PANEL_RED_ALARM":
//                            					LOGGER.info("USER_PANEL_RED_ALARM:::" + message);
                            break;
                        case "TRADING_SCORE":
                            //					LOGGER.info("TRADING_SCORE:::" + message);
                            break;
                        case "USER_TASK_PROGRESS":
                            //					LOGGER.info("USER_TASK_PROGRESS:::" + message);
                            break;
                        case "POPULAR_RANK_CHANGED":
                            //					LOGGER.info("POPULAR_RANK_CHANGED:::" + message);
                            break;
                        case "AREA_RANK_CHANGED":
                            //					LOGGER.info("AREA_RANK_CHANGED:::" + message);
                            break;
                        case "PLAY_TAG":
                            //					LOGGER.info("PLAY_TAG:::" + message);
                            break;
                        case "PK_BATTLE_PROCESS_NEW":
                            //					LOGGER.info("PK_BATTLE_PROCESS_NEW:::" + message);
                            break;
                        case "PK_BATTLE_SETTLE_NEW":
                            //					LOGGER.info("PK_BATTLE_SETTLE_NEW:::" + message);
                            break;
                        case "PK_BATTLE_PUNISH_END":
                            //					LOGGER.info("PK_BATTLE_PUNISH_END:::" + message);
                            break;
                        case "PK_BATTLE_PRE_NEW":
                            //					LOGGER.info("PK_BATTLE_PRE_NEW:::" + message);
                            break;
                        case "PK_BATTLE_START_NEW":
                            //					LOGGER.info("PK_BATTLE_START_NEW:::" + message);
                            break;
                        case "INTERACTIVE_USER":
                            //					LOGGER.info("INTERACTIVE_USER:::" + message);
                            break;
                        case "WIDGET_GIFT_STAR_PROCESS":
                            //					LOGGER.info("WIDGET_GIFT_STAR_PROCESS:::" + message);
                            break;
                        case "LIVE_MULTI_VIEW_NEW_INFO":
                            //					LOGGER.info("LIVE_MULTI_VIEW_NEW_INFO:::" + message);
                            break;
                        case "PANEL_INTERACTIVE_NOTIFY_CHANGE":
                            //					LOGGER.info("PANEL_INTERACTIVE_NOTIFY_CHANGE:::" + message);
                            break;
                        case "MULTI_VOICE_OPERATIN":
                            //					LOGGER.info("MULTI_VOICE_OPERATIN:::" + message);
                            break;
                        case "DM_INTERACTION":
                            //					LOGGER.info("DM_INTERACTION:::" + message);
                            break;
                        case "MULTI_VOICE_PK_HAT_STATUS":
                            //					LOGGER.info("MULTI_VOICE_PK_HAT_STATUS:::" + message);
                            break;
                        case "ONLINE_RANK_V3":
                            //					LOGGER.info("ONLINE_RANK_V3:::" + message);
                            break;
                        case "RANK_CHANGED":
                            //					LOGGER.info("RANK_CHANGED:::" + message);
                            break;
                        case "LIKE_INFO_V3_NOTICE":
                            //					LOGGER.info("LIKE_INFO_V3_NOTICE:::" + message);
                            break;
                        default:
//                            LOGGER.info("其他未处理消息:" + message);
                            break;
                    }
                    PublicDataConf.resultStrs.remove(0);
                } else {
                    synchronized (PublicDataConf.parseMessageThread) {
                        try {
                            PublicDataConf.parseMessageThread.wait();
                        } catch (InterruptedException e) {
                            // TODO 自动生成的 catch 块
                            //						LOGGER.info("处理弹幕包信息线程关闭:" + e);
                            //						e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        }
    }

    //获取发送礼物code
    public String sendCode(short guardLevel) {
        String code = CurrencyTools.sendGiftCode(guardLevel);
        CenterSetConf centerSetConf = CurrencyTools.codeRemove(code);
        setService.changeSet(centerSetConf, true);
        return code;
    }

    public boolean parseAutoReplySetting(Barrage barrage) {
        //判断是否开启自己
        if (!getCenterSetConf().getReply().is_open_self()) {
            if (PublicDataConf.USER.getUid().equals(barrage.getUid())) {
                return false;
            }
        }
        ListPeopleShieldStatus listPeopleShieldStatus = ParseSetStatusTools.getListPeopleShieldStatus(getCenterSetConf().getReply().getList_people_shield_status());
        //先人员
        switch (listPeopleShieldStatus) {
//			case ALL:
//				break;
            case MEDAL:
                if (PublicDataConf.MEDALINFOANCHOR != null) {
                    if (StringUtils.isBlank(PublicDataConf.MEDALINFOANCHOR.getMedal_name())) {
                        break;
                    }
                    //舰长的这里是空的
                    if (barrage.getMedal_name() == null) {
                        break;
                    }
                    if (!PublicDataConf.MEDALINFOANCHOR.getMedal_name().equals(barrage.getMedal_name())) {
                        //    LOGGER.info("自动回复姬人员屏蔽[勋章模式]:{}", barrage.getMedal_name());
                        return false;
                    }
                }
                break;
            case GUARD:
                if (barrage.getUguard() != null && barrage.getUguard() <= 0) {
                    //     LOGGER.info("自动回复姬人员屏蔽[舰长模式]:{}", ParseIndentityTools.parseGuard(barrage.getUguard()));
                    return false;
                }
            default:
                break;
        }
        //黑名单
        return blackParseComponent.autoReplay_parse(AutoReply.getAutoReply(barrage.getUid(), barrage.getUname(), barrage.getMsg()));
    }


    public void DelayGiftTimeSetting() {
        synchronized (PublicDataConf.parsethankGiftThread) {
            if (PublicDataConf.parsethankGiftThread != null) {
                threadComponent.startParseThankGiftThread(getCenterSetConf().getThank_gift(), getThankGiftRuleSets());
//				if (PublicDataConf.parsethankGiftThread.getState().toString().equals("TERMINATED")
//						|| PublicDataConf.parsethankGiftThread.getState().toString().equals("NEW")) {
//					PublicDataConf.parsethankGiftThread = new ParseThankGiftThread();
//					PublicDataConf.parsethankGiftThread
//							.setDelaytime((long) (1000 * getThankGiftSetConf().getDelaytime()));
//					PublicDataConf.parsethankGiftThread.start();
//					PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankGiftThread.setThankGiftString(getThankGiftSetConf().getThank());
//					PublicDataConf.parsethankGiftThread.setThankGiftStatus(
//							ParseSetStatusTools.getThankGiftStatus(getThankGiftSetConf().getThank_status()));
//					PublicDataConf.parsethankGiftThread
//							.setThankGiftRuleSets(getThankGiftRuleSets());
//					PublicDataConf.parsethankGiftThread.setNum(getThankGiftSetConf().getNum());
//					PublicDataConf.parsethankGiftThread.setIs_num(getThankGiftSetConf().is_num());
//				} else {
//					PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankGiftThread.setThankGiftString(getThankGiftSetConf().getThank());
//					PublicDataConf.parsethankGiftThread.setThankGiftStatus(
//							ParseSetStatusTools.getThankGiftStatus(getThankGiftSetConf().getThank_status()));
//					PublicDataConf.parsethankGiftThread
//							.setThankGiftRuleSets(getThankGiftRuleSets());
//					PublicDataConf.parsethankGiftThread.setNum(getThankGiftSetConf().getNum());
//					PublicDataConf.parsethankGiftThread.setIs_num(getThankGiftSetConf().is_num());
//				}
            }
        }
    }

    public synchronized void parseGiftSetting(Gift gift) throws Exception {
        //屏蔽自己
        if (!getCenterSetConf().getThank_gift().is_open_self()) {
            if (PublicDataConf.USER.getUid().equals(gift.getUid())) {
                return;
            }
        }
        //天选礼物屏蔽
//        if (StringUtils.isNotBlank(PublicDataConf.SHIELDGIFTNAME)) {
//            if (gift.getGiftName().equals(PublicDataConf.SHIELDGIFTNAME)) {
//                gift = null;
//            }
        if (getCenterSetConf().getThank_gift().boolTxShield(CacheConf.existTx(PublicDataConf.ROOMID))) {
            if (StringUtils.equals(gift.getGiftName(), CacheConf.getTx(PublicDataConf.ROOMID))) {
                gift = null;
            }
        }
        //礼物屏蔽过滤
        if (blackParseComponent.gift_parse(gift)) {
            if (ParseSetStatusTools.getGiftShieldStatus(
                    getCenterSetConf().getThank_gift().getShield_status()) != ShieldGift.CUSTOM_RULE) {
                gift = ShieldGiftTools.shieldGift(gift,
                        ParseSetStatusTools.getListGiftShieldStatus(
                                getCenterSetConf().getThank_gift().getList_gift_shield_status()),
                        ParseSetStatusTools.getListPeopleShieldStatus(
                                getCenterSetConf().getThank_gift().getList_people_shield_status()),
                        ParseSetStatusTools
                                .getGiftShieldStatus(getCenterSetConf().getThank_gift().getShield_status()),
                        getCenterSetConf().getThank_gift().getGiftStrings(), null);
            }
        } else {
            gift = null;
        }
        Vector<Gift> gifts = null;
        if (gift != null && StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            if (PublicDataConf.sendBarrageThread != null && PublicDataConf.parsethankGiftThread != null) {
                if (!PublicDataConf.sendBarrageThread.FLAG && !PublicDataConf.parsethankGiftThread.TFLAG) {
                    if (PublicDataConf.thankGiftConcurrentHashMap.size() > 0) {
                        gifts = PublicDataConf.thankGiftConcurrentHashMap.get(gift.getUname());
                        if (gifts != null) {
                            int flagNum = 0;
                            for (Gift giftChild : gifts) {
                                int num1 = giftChild.getNum();
                                int num2 = gift.getNum();
                                long total_coin1 = giftChild.getTotal_coin();
                                long total_coin2 = gift.getTotal_coin();
                                if (giftChild.getGiftName().equals(gift.getGiftName())) {
                                    giftChild.setNum(num1 + num2);
                                    giftChild.setTotal_coin(total_coin1 + total_coin2);
                                    DelayGiftTimeSetting();
                                    flagNum++;
                                }
                            }
                            if (flagNum == 0) {
                                gifts.add(gift);
                                DelayGiftTimeSetting();
                            }
                        } else {
                            gifts = new Vector<Gift>();
                            gifts.add(gift);
                            PublicDataConf.thankGiftConcurrentHashMap.put(gift.getUname(), gifts);
                            DelayGiftTimeSetting();
                        }
                    } else {
                        gifts = new Vector<Gift>();
                        gifts.add(gift);
                        PublicDataConf.thankGiftConcurrentHashMap.put(gift.getUname(), gifts);
                        DelayGiftTimeSetting();
                    }
                }
            }
        }
    }

    public void DelayFollowTimeSetting() {
        synchronized (PublicDataConf.parsethankFollowThread) {
            if (PublicDataConf.parsethankFollowThread != null) {
                threadComponent.startParseThankFollowThread(getCenterSetConf().getFollow());
//				if (PublicDataConf.parsethankFollowThread.getState().toString().equals("TERMINATED")
//						|| PublicDataConf.parsethankFollowThread.getState().toString().equals("NEW")) {
//					PublicDataConf.parsethankFollowThread = new ParseThankFollowThread();
//					PublicDataConf.parsethankFollowThread
//							.setDelaytime((long) (1000 * getThankFollowSetConf().getDelaytime()));
//					PublicDataConf.parsethankFollowThread.start();
//					PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankFollowThread.setThankFollowString(getThankFollowSetConf().getFollows());
//					PublicDataConf.parsethankFollowThread.setNum(getThankFollowSetConf().getNum());
//				} else {
//					PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankFollowThread.setThankFollowString(getThankFollowSetConf().getFollows());
//					PublicDataConf.parsethankFollowThread.setNum(getThankFollowSetConf().getNum());
//				}
            }
        }
    }

    public synchronized void parseFollowSetting(Interact interact) throws Exception {
        //黑名单处理
        if (!blackParseComponent.interact_parse(interact)) {
            interact = null;
        }
        if (interact != null && StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            if (PublicDataConf.sendBarrageThread != null && PublicDataConf.parsethankFollowThread != null) {
                if (!PublicDataConf.sendBarrageThread.FLAG && !PublicDataConf.parsethankFollowThread.FLAG) {
                    PublicDataConf.interacts.add(interact);
                    DelayFollowTimeSetting();
                }
            }
        }
    }

    public void DelayWelcomeTimeSetting() {
        synchronized (PublicDataConf.parseThankWelcomeThread) {
            if (PublicDataConf.parseThankWelcomeThread != null) {
                threadComponent.startParseThankWelcomeThread(getCenterSetConf().getWelcome());
//				if (PublicDataConf.parsethankFollowThread.getState().toString().equals("TERMINATED")
//						|| PublicDataConf.parsethankFollowThread.getState().toString().equals("NEW")) {
//					PublicDataConf.parsethankFollowThread = new ParseThankFollowThread();
//					PublicDataConf.parsethankFollowThread
//							.setDelaytime((long) (1000 * getThankFollowSetConf().getDelaytime()));
//					PublicDataConf.parsethankFollowThread.start();
//					PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankFollowThread.setThankFollowString(getThankFollowSetConf().getFollows());
//					PublicDataConf.parsethankFollowThread.setNum(getThankFollowSetConf().getNum());
//				} else {
//					PublicDataConf.parsethankFollowThread.setTimestamp(System.currentTimeMillis());
//					PublicDataConf.parsethankFollowThread.setThankFollowString(getThankFollowSetConf().getFollows());
//					PublicDataConf.parsethankFollowThread.setNum(getThankFollowSetConf().getNum());
//				}
            }
        }
    }

    public synchronized void parseWelcomeSetting(Interact interact) throws Exception {
        if (!blackParseComponent.interact_parse(interact)) {
            interact = null;
        }
        //屏蔽自己
        if (!getCenterSetConf().getWelcome().is_open_self()) {
            if (PublicDataConf.USER.getUid().equals(interact.getUid())) {
                interact = null;
            }
        }
//        LOGGER.info("欢迎信息2：{}" ,interact);
        if (interact != null && StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            //屏蔽设定
            ListPeopleShieldStatus listPeopleShieldStatus = ParseSetStatusTools.getListPeopleShieldStatus(getCenterSetConf().getWelcome().getList_people_shield_status());
            //先人员
            switch (listPeopleShieldStatus) {
//			case ALL:
//				break;
                case MEDAL:
                    if (PublicDataConf.MEDALINFOANCHOR != null) {
                        if (StringUtils.isBlank(PublicDataConf.MEDALINFOANCHOR.getMedal_name())) {
                            return;
                        }
                        //舰长的这里是空的
                        if (interact.getFans_medal() == null) {
                            return;
                        }
                        if (!PublicDataConf.MEDALINFOANCHOR.getMedal_name().equals(interact.getFans_medal().getMedal_name())) {
//                           LOGGER.info("欢迎姬人员屏蔽[勋章模式]:{}", interact.getFans_medal().getMedal_name());
                            return;
                        }
                    }
                    break;
                case GUARD:
                    if (StringUtils.isBlank(PublicDataConf.MEDALINFOANCHOR.getMedal_name())) {
                        return;
                    }
                    if (interact.getFans_medal() == null) {
                        return;
                    }
                    if (!PublicDataConf.MEDALINFOANCHOR.getMedal_name().equals(interact.getFans_medal().getMedal_name())) {
                        return;
                    }
                    if(interact.getFans_medal().getGuard_level() <= 0){
                    //   LOGGER.info("欢迎姬人员屏蔽[舰长模式]:{}", ParseIndentityTools.parseGuard(interact.getFans_medal().getGuard_level()));
                        return;
                    }
                default:
                    break;
            }
            if (PublicDataConf.sendBarrageThread != null && PublicDataConf.parseThankWelcomeThread != null) {
                if (!PublicDataConf.sendBarrageThread.FLAG && !PublicDataConf.parseThankWelcomeThread.FLAG) {
                    PublicDataConf.interactWelcome.add(interact);
                    DelayWelcomeTimeSetting();
                }
            }
        }
    }

    public CenterSetConf getCenterSetConf() {
        if (centerSetConf == null) return PublicDataConf.centerSetConf;
        return centerSetConf;
    }

    public void setCenterSetConf(CenterSetConf centerSetConf) {
        this.centerSetConf = centerSetConf;
    }

    public HashSet<ThankGiftRuleSet> getThankGiftRuleSets() {
        return thankGiftRuleSets;
    }

    public void setThankGiftRuleSets(HashSet<ThankGiftRuleSet> thankGiftRuleSets) {
        this.thankGiftRuleSets = thankGiftRuleSets;
    }


    public static String parseCmd(String cmd) {
        if (cmd.startsWith("DANMU_MSG")) {
            return "DANMU_MSG";
        }
        if (cmd.startsWith("SEND_GIFT")) {
            return "SEND_GIFT";
        }
        if (cmd.startsWith("GUARD_BUY")) {
            return "GUARD_BUY";
        }
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE")) {
//			return "SUPER_CHAT_MESSAGE";
//		}
//		if(cmd.startsWith("WELCOME")) {
//			return "WELCOME";
//		}
//		if(cmd.startsWith("WELCOME_GUARD")) {
//			return "WELCOME_GUARD";
//		}
//		if(cmd.startsWith("ROOM_RANK")) {
//			return "ROOM_RANK";
//		}
//		if(cmd.startsWith("ROOM_REAL_TIME_MESSAGE_UPDATE")) {
//			return "ROOM_REAL_TIME_MESSAGE_UPDATE";
//		}
//		if(cmd.startsWith("WARNING")) {
//			return "WARNING";
//		}
//		if(cmd.startsWith("LIVE")) {
//			return "LIVE";
//		}
//		if(cmd.startsWith("PREPARING")) {
//			return "PREPARING";
//		}

//		if(cmd.startsWith("SPECIAL_GIFT")) {
//			return "SPECIAL_GIFT";
//		}
//		if(cmd.startsWith("USER_TOAST_MSG")) {
//			return "USER_TOAST_MSG";
//		}
//		if(cmd.startsWith("NOTICE_MSG")) {
//			return "NOTICE_MSG";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_START")) {
//			return "ANCHOR_LOT_START";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_END")) {
//			return "ANCHOR_LOT_END";
//		}

//		
//		if(cmd.startsWith("COMBO_SEND")) {
//			return "COMBO_SEND";
//		}
//		if(cmd.startsWith("GUARD_LOTTERY_START")) {
//			return "GUARD_LOTTERY_START";
//		}
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE_JPN")) {
//			return "SUPER_CHAT_MESSAGE_JPN";
//		}
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE_DELETE")) {
//			return "SUPER_CHAT_MESSAGE_DELETE";
//		}
//		if(cmd.startsWith("ENTRY_EFFECT")) {
//			return "ENTRY_EFFECT";
//		}
//		if(cmd.startsWith("ACTIVITY_BANNER_UPDATE_V2")) {
//			return "ACTIVITY_BANNER_UPDATE_V2";
//		}
//		if(cmd.startsWith("ROOM_CHANGE")) {
//			return "ROOM_CHANGE";
//		}
//		if(cmd.startsWith("new_anchor_reward")) {
//			return "new_anchor_reward";
//		}
//		if(cmd.startsWith("HOUR_RANK_AWARDS")) {
//			return "HOUR_RANK_AWARDS";
//		}
//		if(cmd.startsWith("WISH_BOTTLE")) {
//			return "WISH_BOTTLE";
//		}
//		if(cmd.startsWith("RAFFLE_START")) {
//			return "RAFFLE_START";
//		}
//		if(cmd.startsWith("RAFFLE_END")) {
//			return "RAFFLE_END";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_AWARD")) {
//			return "ANCHOR_LOT_AWARD";
//		}
//		if(cmd.startsWith("ANCHOR_NORMAL_NOTIFY")) {
//			return "ANCHOR_NORMAL_NOTIFY";
//		}
//		if(cmd.startsWith("WEEK_STAR_CLOCK")) {
//			return "WEEK_STAR_CLOCK";
//		}
//		if(cmd.startsWith("ROOM_BOX_MASTER")) {
//			return "ROOM_BOX_MASTER";
//		}
//		if(cmd.startsWith("ROOM_SKIN_MSG")) {
//			return "ROOM_SKIN_MSG";
//		}
//		if(cmd.startsWith("SYS_GIFT")) {
//			return "SYS_GIFT";
//		}
//		if(cmd.startsWith("ACTIVITY_MATCH_GIFT")) {
//			return "ACTIVITY_MATCH_GIFT";
//		}
//		if(cmd.startsWith("PK_BATTLE_ENTRANCE")) {
//			return "PK_BATTLE_ENTRANCE";
//		}
//		if(cmd.startsWith("PK_START")) {
//			return "PK_START";
//		}	
//		if(cmd.startsWith("PK_PRE")) {
//			return "PK_PRE";
//		}
//		if(cmd.startsWith("PK_MATCH")) {
//			return "PK_MATCH";
//		}
//		if(cmd.startsWith("PK_CLICK_AGAIN")) {
//			return "PK_CLICK_AGAIN";
//		}
//		if(cmd.startsWith("PK_MIC_END")) {
//			return "PK_MIC_END";
//		}
//		if(cmd.startsWith("PK_PROCESS")) {
//			return "PK_PROCESS";
//		}
//		if(cmd.startsWith("PK_SETTLE")) {
//			return "PK_SETTLE";
//		}
//		if(cmd.startsWith("PK_END")) {
//			return "PK_END";
//		}
//		if(cmd.startsWith("SYS_MSG")) {
//			return "SYS_MSG";
//		}
//		if(cmd.startsWith("GUARD_MSG")) {
//			return "GUARD_MSG";
//		}
//		if(cmd.startsWith("HOT_ROOM_NOTIFY")) {
//			return "HOT_ROOM_NOTIFY";
//		}
//		if(cmd.startsWith("room_admin_entrance")) {
//			return "room_admin_entrance";
//		}
//		if(cmd.startsWith("ROOM_ADMINS")) {
//			return "ROOM_ADMINS";
//		}
//		if(cmd.startsWith("ROOM_SHIELD")) {
//			return "ROOM_SHIELD";
//		}
//		if(cmd.startsWith("ROOM_SILENT_ON")) {
//			return "ROOM_SILENT_ON";
//		}
//		if(cmd.startsWith("ROOM_SILENT_OFF")) {
//			return "ROOM_SILENT_OFF";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_CHECKSTATUS")) {
//			return "ANCHOR_LOT_CHECKSTATUS";
//		}
//		if(cmd.startsWith("VOICE_JOIN_ROOM_COUNT_INFO")) {
//			return "VOICE_JOIN_ROOM_COUNT_INFO";
//		}
//		if(cmd.startsWith("VOICE_JOIN_LIST")) {
//			return "VOICE_JOIN_LIST";
//		}
//		if(cmd.startsWith("")) {
//			return "";
//		}

        return cmd;
    }
}
