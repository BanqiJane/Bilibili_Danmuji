package xyz.acproject.danmuji.thread;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.AutoReplySet;
import xyz.acproject.danmuji.entity.apex.ApexMessage;
import xyz.acproject.danmuji.entity.apex.PredatorResult;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.entity.other.Weather;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.ApiService;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

/**
 * @author BanqiJane
 * @ClassName AutoReplyThread
 * @Description TODO
 * @date 2020年8月10日 下午12:16:13
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class AutoReplyThread extends Thread {
    public volatile boolean FLAG = false;
    private short time = 3;
    private HashSet<AutoReplySet> autoReplySets;

    private ApiService apiService = SpringUtils.getBean(ApiService.class);

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        super.run();
        int kSize = 0;
        int kNum = 0;
        String replyString = null;
        boolean is_shield = false;
        boolean is_send = false;
        String hourString = null;
        String hourReplace = null;
        String keywords[] = null;
        short hour = 1;
        while (!FLAG) {
            if (FLAG) {
                return;
            }
            if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
                return;
            }
            if (null != PublicDataConf.replys && !PublicDataConf.replys.isEmpty()
                    && !StringUtils.isEmpty(PublicDataConf.replys.get(0).getBarrage())) {
                AutoReply autoReply = PublicDataConf.replys.get(0);
                for (AutoReplySet autoReplySet : getAutoReplySets()) {
                    if (null != autoReplySet.getShields() && !autoReplySet.getShields().isEmpty()
                            && autoReplySet.getShields().size() > 0) {
                        kSize = autoReplySet.getKeywords().size();
                        kNum = 0;
                        is_shield = false;
                        for (String shield : autoReplySet.getShields()) {
                            if (autoReply.getBarrage().contains(shield)) {
                                is_shield = true;
                                break;
                            }
                        }
                        if (!is_shield) {
                            for (String keyword : autoReplySet.getKeywords()) {
                                if (StringUtils.indexOf(keyword, "||") != -1) {
                                    keywords = StringUtils.split(keyword, "||");
                                    for (String k : keywords) {
                                        if (autoReply.getBarrage().contains(k)) {
                                            kNum++;
                                            break;
                                        }
                                    }
                                } else {
                                    if (autoReply.getBarrage().contains(keyword)) {
                                        kNum++;
                                    }
                                }
                            }
                            if (kNum == kSize) {
                                if (!StringUtils.isEmpty(autoReplySet.getReply())) {
                                    is_send =   handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
                                            is_send);
                                    break;
                                }
                            }
                        }
                    } else {
                        kSize = autoReplySet.getKeywords().size();
                        kNum = 0;
                        is_shield = false;
                        // 精确匹配
                        if (autoReplySet.getKeywords().size() < 2 && autoReplySet.is_accurate()) {
                            for (String keyword : autoReplySet.getKeywords()) {
                                if (StringUtils.indexOf(keyword, "||") != -1) {
                                    keywords = StringUtils.split(keyword, "||");
                                    for (String k : keywords) {
                                        if (autoReply.getBarrage().equals(k)) {
                                            // do something
                                            is_send = handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
                                                    is_send);
                                            break;
                                        }
                                    }
                                } else {
                                    if (autoReply.getBarrage().equals(keyword)) {
                                        // do something
                                        is_send = handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
                                                is_send);
                                    }
                                }
                            }
                        } else {
                            for (String keyword : autoReplySet.getKeywords()) {
                                if (StringUtils.indexOf(keyword, "||") != -1) {
                                    keywords = StringUtils.split(keyword, "||");
                                    for (String k : keywords) {
                                        if (autoReply.getBarrage().contains(k)) {
                                            kNum++;
                                            break;
                                        }
                                    }
                                } else {
                                    if (autoReply.getBarrage().contains(keyword)) {
                                        kNum++;
                                    }
                                }
                            }
                            if (kNum == kSize) {
                                if (!StringUtils.isEmpty(autoReplySet.getReply())) {
                                    is_send = handle(autoReplySet, replyString, autoReply, hourString, hour, hourReplace,
                                            is_send);
                                    break;
                                }
                            }
                        }
                    }
                }
                keywords = null;
                kSize = 0;
                kNum = 0;
                is_shield = false;
                replyString = null;
                hourString = null;
                hourReplace = null;
                hour = 1;
                PublicDataConf.replys.remove(0);
                if (is_send) {
                    try {
                        Thread.sleep(getTime() * 1000);
                    } catch (Exception e) {
                        // TODO 自动生成的 catch 块
//					e.printStackTrace();
                    }
                }
                is_send = false;
            } else {
                synchronized (PublicDataConf.autoReplyThread) {
                    try {
                        PublicDataConf.autoReplyThread.wait();
                    } catch (Exception e) {
                        // TODO 自动生成的 catch 块
//						e.printStackTrace();
                    }
                }
            }
        }
    }

    private synchronized boolean handle(AutoReplySet autoReplySet, String replyString, AutoReply autoReply,
                                     String hourString, short hour, String hourReplace, boolean is_send) {
        //拟议自动回复处理
        //1. 针对特定人?
        //2. 刷屏?
        // 替换%NAME%参数
        if (!autoReplySet.getReply().equals("%NAME%")) {
            replyString = StringUtils.replace(autoReplySet.getReply(), "%NAME%", autoReply.getName());
        } else {
            replyString = autoReply.getName();
        }
        // 替换%FANS%
        if (!replyString.equals("%FANS%")) {
            replyString = StringUtils.replace(replyString, "%FANS%", String.valueOf(PublicDataConf.FANSNUM));
        } else {
            replyString = String.valueOf(PublicDataConf.FANSNUM);
        }
        // 替换%TIME%
        if (!replyString.equals("%TIME%")) {
            replyString = StringUtils.replace(replyString, "%TIME%", JodaTimeUtils.format(new Date(),TimeZone.getTimeZone("GMT+08:00"),"yyyy-MM-dd HH:mm:ss"));
        } else {
            replyString =JodaTimeUtils.format(new Date(),TimeZone.getTimeZone("GMT+08:00"),"yyyy-MM-dd HH:mm:ss");
        }
        // 替换%LIVETIME%
        if (!replyString.equals("%LIVETIME%")) {
            if (PublicDataConf.lIVE_STATUS == 1) {
                replyString = StringUtils.replace(replyString, "%LIVETIME%",
                        CurrencyTools.getGapTime(System.currentTimeMillis()
                                - HttpRoomData.httpGetRoomInit(PublicDataConf.ROOMID).getLive_time() * 1000));
            } else {
                replyString = StringUtils.replace(replyString, "%LIVETIME%", "0");
            }
        } else {
            if (PublicDataConf.lIVE_STATUS == 1) {
                replyString = CurrencyTools.getGapTime(System.currentTimeMillis()
                        - HttpRoomData.httpGetRoomInit(PublicDataConf.ROOMID).getLive_time() * 1000);
            } else {
                replyString = "0";
            }
        }
        // 替换%HOT%
        if (!replyString.equals("%HOT%")) {
            replyString = StringUtils.replace(replyString, "%HOT%", PublicDataConf.ROOM_POPULARITY.toString());
        } else {
            replyString = PublicDataConf.ROOM_POPULARITY.toString();
        }
        // 替换%BLOCK%参数 和 {{time}}时间参数
        if (replyString.contains("%BLOCK%")) {
            replyString = StringUtils.replace(replyString, "%BLOCK%", "");
            if (replyString.contains("{{") && replyString.contains("}}")) {
                hourString = replyString.substring(replyString.indexOf("{{") + 2, replyString.indexOf("}}"));
                if (hourString.matches("[0-9]+")) {
                    if (hour <= 720 && hour > 0) {
                        hour = Short.parseShort(hourString);
                    }
                }
                hourReplace = replyString.substring(replyString.indexOf("{{"), replyString.indexOf("}}") + 2);
                if (!replyString.equals(hourReplace)) {
                    replyString = StringUtils.replace(replyString, hourReplace, "");
                } else {
                    replyString = "";
                }
            }
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                try {
                    if (HttpUserData.httpPostAddBlock(autoReply.getUid(), hour) != 0)
                        replyString = "";
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        //天气
        if (StringUtils.containsAny(replyString, "%WEATHER%", "%W_CITY%", "%W_DATE%", "%H_WENDU%", "%L_WENDU%", "%WENDU%", "%W_FX%", "%W_TYPE%", "%W_FL%", "%W_TIPS%")) {
            if (autoReply.getBarrage().contains("天气") && (autoReply.getBarrage().contains("@") || autoReply.getBarrage().contains("#"))) {
                int path1 = autoReply.getBarrage().indexOf("@") >= 0 ? autoReply.getBarrage().indexOf("@") : autoReply.getBarrage().indexOf("#");
                int path2 = autoReply.getBarrage().indexOf("天气");
                String city = autoReply.getBarrage().substring(path1 + 1, path2);
                StringBuilder weatherSB = new StringBuilder();
                Weather weather = null;
                if (StringUtils.isEmpty(city)) {
                    city = "北京";
                }
                Short day = 0;
                if (city.endsWith("昨天")) {
                    city = city.substring(0, city.indexOf("昨天"));
                    day = -1;
                } else if (city.endsWith("明天")) {
                    city = city.substring(0, city.indexOf("明天"));
                    day = 1;
                } else if (city.endsWith("后天")) {
                    city = city.substring(0, city.indexOf("后天"));
                    day = 2;
                } else if (city.endsWith("后两天")) {
                    city = city.substring(0, city.indexOf("后两天"));
                    day = 3;
                } else if (city.endsWith("后三天")) {
                    city = city.substring(0, city.indexOf("后三天"));
                    day = 4;
                } else if (city.endsWith("今天")) {
                    city = city.substring(0, city.indexOf("今天"));
                    day = 0;
                } else {
                    day = 0;
                }
                weather = apiService.getWeather(city, day);
//                weather = HttpOtherData.httpPostWeather(city, day);
                if (null != weather) {
                    if (replyString.contains("%WEATHER%") && !StringUtils.containsAny(replyString, "%W_CITY%", "%W_DATE%", "%H_WENDU%", "%L_WENDU%", "%WENDU%", "%W_FX%", "%W_TYPE%", "%W_FL%", "%W_TIPS%")) {
                        if (day == 0) {
                            weatherSB.append(weather.getCity()).append(":").append(weather.getDate())
                                    .append(",").append(weather.getType()).append(" ").append(weather.getFx())
                                    .append(weather.getFl()).append(",").append("气温").append(weather.getWendu()).append("℃")
                                    .append(" ").append("最低").append(weather.getLow()).append(" ").append("最高").append(weather.getHigh())
                                    .append(",").append(weather.getGanmao());
                        } else {
                            weatherSB.append(weather.getCity()).append(":").append(weather.getDate())
                                    .append(",").append(weather.getType()).append(" ").append(weather.getFx())
                                    .append(weather.getFl()).append(",").append("气温").append(":").append("最低")
                                    .append(weather.getLow()).append(" ").append("最高").append(weather.getHigh())
                                    .append(",").append(weather.getGanmao());
                        }
                        if (!replyString.equals("%WEATHER%")) {
                            replyString = StringUtils.replace(replyString, "%WEATHER%", weatherSB.toString());
                        } else {
                            replyString = weatherSB.toString();
                        }
                    } else if (!replyString.contains("%WEATHER%") && StringUtils.containsAny(replyString, "%W_CITY%", "%W_DATE%", "%H_WENDU%", "%L_WENDU%", "%WENDU%", "%W_FX%", "%W_TYPE%", "%W_FL%", "%W_TIPS%")) {
                        //城市
                        if (replyString.contains("%W_CITY%")) {
                            replyString = StringUtils.replace(replyString, "%W_CITY%", weather.getCity());
                        }
                        //时间  格式 多少日星期几
                        if (replyString.contains("%W_DATE%")) {
                            replyString = StringUtils.replace(replyString, "%W_DATE%", weather.getDate());
                        }
                        //最高温度 包含°C符号
                        if (replyString.contains("%H_WENDU%")) {
                            replyString = StringUtils.replace(replyString, "%H_WENDU%", weather.getHigh());
                        }
                        //最低温度 包含°C符号
                        if (replyString.contains("%L_WENDU%")) {
                            replyString = StringUtils.replace(replyString, "%L_WENDU%", weather.getLow());
                        }
                        // 风向
                        if (replyString.contains("%W_FX%")) {
                            replyString = StringUtils.replace(replyString, "%W_FX%", weather.getFx());
                        }
                        //风力
                        if (replyString.contains("%W_FL%")) {
                            replyString = StringUtils.replace(replyString, "%W_FL%", weather.getFl());
                        }

                        //天气类型 例如晴天 多云
                        if (replyString.contains("%W_TYPE%")) {
                            replyString = StringUtils.replace(replyString, "%W_TYPE%", weather.getType());
                        }
                        //分类处理当天没有的
                        if (day == 0) {
                            //感冒小提示 只有当天有 未来和过去没有
                            if (replyString.contains("%W_TIPS%")) {
                                replyString = StringUtils.replace(replyString, "%W_TIPS%", weather.getGanmao());
                            }
                            //温度 包含°C符号 只有当天有 未来和过去没有
                            if (replyString.contains("%WENDU%")) {
                                replyString = StringUtils.replace(replyString, "%WENDU%", weather.getLow());
                            }
                        } else {
                            if (replyString.contains("%WENDU%")) {
                                replyString = StringUtils.replace(replyString, "%WENDU%", "");
                            }
                            //感冒小提示 只有当天有 未来和过去没有
                            if (replyString.contains("%W_TIPS%")) {
                                replyString = StringUtils.replace(replyString, "%W_TIPS%","");
                            }
                        }
                    }
                    replyString = StringUtils.replace(replyString,"℃","度");
                } else {
                    replyString = "";
                }
                weatherSB.delete(0, weatherSB.length());
            }
        }
        //apex 排位
        if (StringUtils.containsAny(replyString,"%PC_RP_DFEN%","%PC_RP_MTOTAL%","%PC_AP_DFEN%","%PC_AP_MTOTAL%","%PS4_RP_DFEN%","%PS4_RP_MTOTAL%","%PS4_AP_DFEN%","%PS4_AP_MTOTAL%")) {
            PredatorResult predatorResult=null;
            //pc大逃杀猎杀低分
            if (replyString.contains("%PC_RP_DFEN%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("","0");
                if(predatorResult!=null&&predatorResult.getVal()!=null){
                    replyString = StringUtils.replace(replyString, "%PC_RP_DFEN%", String.valueOf(predatorResult.getVal()));
                }else{
                    replyString = "";
                }
            }
            //pc大逃杀猎杀大师总数
            if (replyString.contains("%PC_RP_MTOTAL%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("","0");
                if(predatorResult!=null&&predatorResult.getTotalMastersAndPreds()!=null) {
                    replyString = StringUtils.replace(replyString, "%PC_RP_MTOTAL%", String.valueOf(predatorResult.getTotalMastersAndPreds()));
                }else{
                    replyString = "";
                }
            }
            //pc竞技场猎杀大师低分
            if (replyString.contains("%PC_AP_DFEN%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("","1");
                if(predatorResult!=null&&predatorResult.getVal()!=null) {
                    replyString = StringUtils.replace(replyString, "%PC_AP_DFEN%", String.valueOf(predatorResult.getVal()));
                }else{
                    replyString = "";
                }
            }
            //pc竞技场猎杀大师总数
            if (replyString.contains("%PC_AP_MTOTAL%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("","1");
                if(predatorResult!=null&&predatorResult.getTotalMastersAndPreds()!=null) {
                    replyString = StringUtils.replace(replyString, "%PC_AP_MTOTAL%", String.valueOf(predatorResult.getTotalMastersAndPreds()));
                }else{
                    replyString = "";
                }
            }
            //ps4大逃杀猎杀低分
            if (replyString.contains("%PS4_RP_DFEN%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("ps4","2");
                if(predatorResult!=null&&predatorResult.getVal()!=null) {
                    replyString = StringUtils.replace(replyString, "%PS4_RP_DFEN%", String.valueOf(predatorResult.getVal()));
                }else{
                    replyString = "";
                }
            }
            //ps4大逃杀猎杀大师总数
            if (replyString.contains("%PS4_RP_MTOTAL%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("ps4","2");
                if(predatorResult!=null&&predatorResult.getTotalMastersAndPreds()!=null) {
                    replyString = StringUtils.replace(replyString, "%PS4_RP_MTOTAL%", String.valueOf(predatorResult.getTotalMastersAndPreds()));
                }else{
                    replyString = "";
                }
            }
            //ps4竞技场猎杀大师低分
            if (replyString.contains("%PS4_AP_DFEN%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("ps4","3");
                if(predatorResult!=null&&predatorResult.getVal()!=null){
                    replyString = StringUtils.replace(replyString, "%PS4_AP_DFEN%", String.valueOf(predatorResult.getVal()));
                }else{
                    replyString = "";
                }
            }
            //ps4竞技场猎杀大师总数
            if (replyString.contains("%PS4_AP_MTOTAL%")) {
                predatorResult = HttpOtherData.httpGetApexPredator("ps4","3");
                if(predatorResult!=null&&predatorResult.getTotalMastersAndPreds()!=null) {
                    replyString = StringUtils.replace(replyString, "%PS4_AP_MTOTAL%", String.valueOf(predatorResult.getTotalMastersAndPreds()));
                }else{
                    replyString = "";
                }
            }
        }
        //apex 综合
        if (StringUtils.containsAny(replyString,"%MAKER_DAY1%","%MAKER_DAY2%","%MAKER_WEEK1%","%MAKER_WEEK2%","%PASS_END%","%SHOP_REFRESH%","%PW_RP_NOWMAP%","%PW_RP_OTHERMAP%","%PW_RP_ENDTIME%","%PW_AP_NOWMAP%","%PW_AP_NEXMAP%","%PW_AP_ENDTIME%","%PP_RP_NOWMAP%","%PP_RP_NEXMAP%","%PP_RP_ENDTIME%","%PP_AP_NOWMAP%","%PP_AP_NEXMAP%","%PP_AP_ENDTIME%")) {
            ApexMessage apexMessage = HttpOtherData.httpGetApexMessage();
            if(apexMessage!=null){
                //如轮换
                if (replyString.contains("%MAKER_DAY1%")&&StringUtils.isNotBlank(apexMessage.getMaker_day1())) {
                    replyString = StringUtils.replace(replyString, "%MAKER_DAY1%", apexMessage.getMaker_day1());
                }else{
                    replyString = StringUtils.replace(replyString, "%MAKER_DAY1%","");
                }
                if (replyString.contains("%MAKER_DAY2%")&&StringUtils.isNotBlank(apexMessage.getMaker_day2())) {
                    replyString = StringUtils.replace(replyString, "%MAKER_DAY2%", apexMessage.getMaker_day2());
                }else{
                    replyString = StringUtils.replace(replyString, "%MAKER_DAY2%","");
                }
                //周轮换
                if (replyString.contains("%MAKER_WEEK1%")&&StringUtils.isNotBlank(apexMessage.getMaker_week1())) {
                    replyString = StringUtils.replace(replyString, "%MAKER_WEEK1%", apexMessage.getMaker_week1());
                }else{
                    replyString = StringUtils.replace(replyString, "%MAKER_WEEK1%","");
                }
                if (replyString.contains("%MAKER_WEEK2%")&&StringUtils.isNotBlank(apexMessage.getMaker_week2())) {
                    replyString = StringUtils.replace(replyString, "%MAKER_WEEK2%", apexMessage.getMaker_week2());
                }else{
                    replyString = StringUtils.replace(replyString, "%MAKER_WEEK2%","");
                }
                //通行证时间
                if (replyString.contains("%PASS_END%")&&apexMessage.getPass_endDownTime()!=null) {
                    replyString = StringUtils.replace(replyString, "%PASS_END%",JodaTimeUtils.format(apexMessage.getPass_endDownTime(),"yyyy年MM月dd日HH时mm分ss秒"));
                }else{
                    replyString = StringUtils.replace(replyString, "%PASS_END%","");
                }
                //商店刷新
                if (replyString.contains("%SHOP_REFRESH%")&&apexMessage.getShop_refreshTime()!=null) {
                    replyString = StringUtils.replace(replyString, "%SHOP_REFRESH%",JodaTimeUtils.format(apexMessage.getShop_refreshTime(),"yyyy年MM月dd日HH时mm分ss秒"));
                }else{
                    replyString = StringUtils.replace(replyString, "%SHOP_REFRESH%","");
                }
                if(apexMessage.getPw_battle()!=null) {
                    //大逃杀当前地图
                    if (replyString.contains("%PW_RP_NOWMAP%")&&StringUtils.isNotBlank(apexMessage.getPw_battle().getNow_name())) {
                        replyString = StringUtils.replace(replyString, "%PW_RP_NOWMAP%", apexMessage.getPw_battle().getNow_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_RP_NOWMAP%","");
                    }
                    //大逃杀排位其他地图 当上半赛季返回上半赛季 下半赛季返回上半赛季
                    if (replyString.contains("%PW_RP_OTHERMAP%")&&StringUtils.isNotBlank(apexMessage.getPw_battle().getPre_name())) {
                        replyString = StringUtils.replace(replyString, "%PW_RP_OTHERMAP%", apexMessage.getPw_battle().getPre_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_RP_OTHERMAP%","");
                    }
                    //大逃杀结束时间
                    if (replyString.contains("%PW_RP_ENDTIME%")&&StringUtils.isNotBlank(apexMessage.getPw_battle().getRemainder_time())) {
                        replyString = StringUtils.replace(replyString, "%PW_RP_ENDTIME%", apexMessage.getPw_battle().getRemainder_time());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_RP_ENDTIME%","");
                    }
                }else{
                    replyString = "";
                }
                if(apexMessage.getPw_arena()!=null) {
                    //竞技场当前地图
                    if (replyString.contains("%PW_AP_NOWMAP%")&&StringUtils.isNotBlank(apexMessage.getPw_arena().getNow_name())) {
                        replyString = StringUtils.replace(replyString, "%PW_AP_NOWMAP%", apexMessage.getPw_arena().getNow_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_AP_NOWMAP%","");
                    }
                    //竞技场下一地图
                    if (replyString.contains("%PW_AP_NEXMAP%")&&StringUtils.isNotBlank(apexMessage.getPw_arena().getNext_name())) {
                        replyString = StringUtils.replace(replyString, "%PW_AP_NEXMAP%", apexMessage.getPw_arena().getNext_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_AP_NEXMAP%","");
                    }
                    //竞技场结束时间
                    if (replyString.contains("%PW_AP_ENDTIME%")&&StringUtils.isNotBlank(apexMessage.getPw_arena().getRemainder_time())) {
                        replyString = StringUtils.replace(replyString, "%PW_AP_ENDTIME%", apexMessage.getPw_arena().getRemainder_time());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PW_AP_ENDTIME%","");
                    }
                }else{
                    replyString = "";
                }
                if(apexMessage.getPp_battle()!=null) {
                    //大逃杀当前地图
                    if (replyString.contains("%PP_RP_NOWMAP%")&&StringUtils.isNotBlank(apexMessage.getPp_battle().getNow_name())) {
                        replyString = StringUtils.replace(replyString, "%PP_RP_NOWMAP%", apexMessage.getPp_battle().getNow_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_RP_NOWMAP%","");
                    }
                    //大逃杀下一地图
                    if (replyString.contains("%PP_RP_NEXMAP%")&&StringUtils.isNotBlank(apexMessage.getPp_battle().getNext_name())) {
                        replyString = StringUtils.replace(replyString, "%PP_RP_NEXMAP%", apexMessage.getPp_battle().getNext_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_RP_NEXMAP%","");
                    }
                    //大逃杀结束时间
                    if (replyString.contains("%PP_RP_ENDTIME%")&&StringUtils.isNotBlank(apexMessage.getPp_battle().getRemainder_time())) {
                        replyString = StringUtils.replace(replyString, "%PP_RP_ENDTIME%", apexMessage.getPp_battle().getRemainder_time());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_RP_ENDTIME%","");
                    }
                }else{
                    replyString = "";
                }
                if(apexMessage.getPp_arena()!=null) {
                    //竞技场当前地图
                    if (replyString.contains("%PP_AP_NOWMAP%")&&StringUtils.isNotBlank(apexMessage.getPp_arena().getNow_name())) {
                        replyString = StringUtils.replace(replyString, "%PP_AP_NOWMAP%", apexMessage.getPp_arena().getNow_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_AP_NOWMAP%","");
                    }
                    //竞技场下一地图
                    if (replyString.contains("%PP_AP_NEXMAP%")&&StringUtils.isNotBlank(apexMessage.getPp_arena().getNext_name())) {
                        replyString = StringUtils.replace(replyString, "%PP_AP_NEXMAP%", apexMessage.getPp_arena().getNext_name());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_AP_NEXMAP%","");
                    }
                    //竞技场结束时间
                    if (replyString.contains("%PP_AP_ENDTIME%")&&StringUtils.isNotBlank(apexMessage.getPp_arena().getRemainder_time())) {
                        replyString = StringUtils.replace(replyString, "%PP_AP_ENDTIME%", apexMessage.getPp_arena().getRemainder_time());
                    }else{
                        replyString = StringUtils.replace(replyString, "%PP_AP_ENDTIME%","");
                    }
                }else{
                    replyString = "";
                }
            }else{
                replyString = "";
            }
        }
        if (!StringUtils.isEmpty(replyString)) {
            if (PublicDataConf.sendBarrageThread != null && !PublicDataConf.sendBarrageThread.FLAG) {
                PublicDataConf.barrageString.add(replyString);
                is_send = true;
                synchronized (PublicDataConf.sendBarrageThread) {
                    PublicDataConf.sendBarrageThread.notify();
                }
            }
        }
        return is_send;
    }

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public HashSet<AutoReplySet> getAutoReplySets() {
        return autoReplySets;
    }

    public void setAutoReplySets(HashSet<AutoReplySet> autoReplySets) {
        this.autoReplySets = autoReplySets;
    }

}
