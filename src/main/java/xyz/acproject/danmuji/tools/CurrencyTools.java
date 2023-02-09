package xyz.acproject.danmuji.tools;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.BarrageHeadHandle;
import xyz.acproject.danmuji.entity.room_data.RoomInit;
import xyz.acproject.danmuji.entity.server_data.HostServer;
import xyz.acproject.danmuji.entity.user_data.AutoSendGift;
import xyz.acproject.danmuji.entity.user_data.UserBag;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.entity.user_data.UserMedal;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.utils.ByteUtils;
import xyz.acproject.danmuji.utils.FastJsonUtils;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author BanqiJane
 * @ClassName CurrencyTools
 * @Description TODO
 * @date 2020年8月10日 下午12:31:10
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class CurrencyTools {
    private static Logger LOGGER = LogManager.getLogger(CurrencyTools.class);

    /**
     * 获取随机破站弹幕服务器地址 20201218优化获取
     *
     * @param hostServers
     * @return
     */
    public static String GetWsUrl(List<HostServer> hostServers) {
        StringBuilder stringBuilder = new StringBuilder();
        String wsUrl = null;
        int control = 0;
        if (hostServers.size() > 0) {
            while (!(PublicDataConf.URL).equals(wsUrl)) {
                if (control > 5) {
                    break;
                }
                HostServer hostServer = hostServers.get((int) (Math.random() * hostServers.size()));
                stringBuilder.append("wss://");
                stringBuilder.append(hostServer.getHost());
                stringBuilder.append(":");
                stringBuilder.append(hostServer.getWss_port());
                stringBuilder.append("/sub");
                wsUrl = stringBuilder.toString();
                stringBuilder.delete(0, stringBuilder.length());
                control++;
            }
        }
        LOGGER.info("获取破站弹幕服务器websocket地址：" + wsUrl);
        return wsUrl;
    }

    /**
     * @param time
     * @return
     */
    public static String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (time - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        if (second < 10) {
            diffTime = diffTime + ":0" + second;
        } else {
            diffTime = diffTime + ":" + second;
        }
        return diffTime;
    }

    /**
     * 获取心跳包byte[]
     *
     * @return
     */
    public static byte[] heartBytes() {
        return ByteUtils.byteMerger(
                HandleWebsocketPackage.BEhandle(BarrageHeadHandle.getBarrageHeadHandle(
                        "[object Object]".getBytes().length + 16, PublicDataConf.packageHeadLength,
                        PublicDataConf.packageVersion, PublicDataConf.heartPackageType, PublicDataConf.packageOther)),
                "[object Object]".getBytes());
    }

    /**
     * 生成uuid 8-4-4-4-12
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return 返回MD5
     */
    public static String deviceHash() {
        String hashString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()+-";
        char[] hashChars = hashString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append(System.currentTimeMillis()).append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)]);
        return DigestUtils.md5Hex(stringBuilder.toString());
    }

    /**
     * 过滤房间号
     *
     * @return
     */
    public static long parseRoomId() {
        if (PublicDataConf.SHORTROOMID != null && PublicDataConf.SHORTROOMID > 0) {
            return PublicDataConf.SHORTROOMID;
        }
        return PublicDataConf.ROOMID != null ? PublicDataConf.ROOMID : 0;

    }

    /**
     * 获取天气接口用
     *
     * @return
     */
    public static String getWeatherDay() {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String weekString = "一";
        StringBuilder weatherDay = new StringBuilder();
        switch (week) {
            case 1:
                weekString = "一";
                break;
            case 2:
                weekString = "二";
                break;
            case 3:
                weekString = "三";
                break;
            case 4:
                weekString = "四";
                break;
            case 5:
                weekString = "五";
                break;
            case 6:
                weekString = "六";
                break;
            case 0:
                weekString = "天";
                break;
            default:
                weekString = "一";
                break;
        }
        return weatherDay.append(day).append("日星期").append(weekString).toString();
    }


    public static List<UserMedal> getAllUserMedals() {
        List<UserMedal> userMedals = HttpUserData.httpGetMedalList();
        return userMedals;
    }

    public static String handleEnterStr(String enterStr) {
        String enterStrs[] = null;
        if (StringUtils.indexOf(enterStr, "\n") != -1) {
            enterStrs = StringUtils.split(enterStr, "\n");
        }
        if (enterStrs != null && enterStrs.length > 1) {
            return enterStrs[(int) Math.ceil(Math.random() * enterStrs.length) - 1];
        }
        return enterStr;
    }

    //打卡 保持其同步性
    public synchronized static int clockIn(List<UserMedal> userMedals) {
        //判定是否有签到
        Date date = new Date();
        int nowDay = JodaTimeUtils.formatToInt(date, "yyyyMMdd");
        if (PublicDataConf.centerSetConf.getPrivacy().getClockInDay() == nowDay) {
            return 0;
        }
        if (!PublicDataConf.centerSetConf.getPrivacy().is_open()) {
            Long uid = HttpOtherData.httpGetClockInRecord();
            if (uid != null && uid > 0) return 0;
        }

        //逻辑开始
        if (StringUtils.isEmpty(PublicDataConf.centerSetConf.getClock_in().getBarrage())) return 0;
        int max = 0;
        RoomInit roomInit;
        if (!CollectionUtils.isEmpty(userMedals)) {
            for (UserMedal userMedal : userMedals) {
                try {
                    LOGGER.info("第{}次打卡开始,勋章数据", max + 1, userMedal);
                    roomInit = HttpRoomData.httpGetRoomInit(userMedal.getRoomid());
                    try {
                        Thread.sleep(4050);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String barrge = handleEnterStr(PublicDataConf.centerSetConf.getClock_in().getBarrage());
                    //   short code = 0;
                    short code = HttpUserData.httpPostSendBarrage(barrge, roomInit.getRoom_id());
                    try {
                        Thread.sleep(2050);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    LOGGER.info("第{}次打卡{},直播间:{},up主:{},发送弹幕:{}", max + 1, code == 0 ? "成功" : "失败", userMedal.getRoomid(), userMedal.getTarget_name(), barrge);
                    max++;
                } catch (Exception e) {
                    LOGGER.info("第{}次打卡{},直播间:{},up主:{},发送弹幕:{}", max + 1, "异常", userMedal.getRoomid(), userMedal.getTarget_name(), "未能成功发送");
//                    e.printStackTrace();
                }
            }
        }
        return max;
    }


    public static String sendGiftCode(short guardLevel) {
        String code = "";
        //默认随机发送
        if (!CollectionUtils.isEmpty(PublicDataConf.centerSetConf.getThank_gift().getCodeStrings())) {
            synchronized (PublicDataConf.centerSetConf.getThank_gift().getCodeStrings()) {
                //分组
                HashSet<String> codeStrings = PublicDataConf.centerSetConf.getThank_gift().getCodeStrings();
                Map<String, HashSet<String>> codeMap = codeStrings.stream().map(s -> {
                    if (!StringUtils.startsWithAny(s, "舰长-", "总督-", "提督-")) {
                        return "全部-" + s;
                    }
                    return s;
                }).collect(Collectors.groupingBy(s -> s.substring(0, 2), Collectors.toCollection(HashSet::new)));
                //循环处理去除前缀
                for (Map.Entry<String, HashSet<String>> entry : codeMap.entrySet()) {
                    codeMap.put(entry.getKey(), entry.getValue().stream().map(s -> {
                        if (StringUtils.startsWithAny(s, "舰长-", "总督-", "提督-", "全部-")) {
                            return s.substring(3);
                        }
                        return s;
                    }).collect(Collectors.toCollection(HashSet::new)));
                }
                boolean onlyHasAll = false;
                if (CollectionUtils.isEmpty(codeMap.get("舰长")) && CollectionUtils.isEmpty(codeMap.get("总督")) && CollectionUtils.isEmpty(codeMap.get("提督"))) {
                    onlyHasAll = true;
                }
                //根据舰长等级获取对应的礼物码
                if (onlyHasAll) {
                    int random = (int) Math.ceil(Math.random() * PublicDataConf.centerSetConf.getThank_gift().getCodeStrings().size()) - 1;
                    int i = 0;
                    for (Iterator<String> iterator = PublicDataConf.centerSetConf.getThank_gift().getCodeStrings().iterator(); iterator.hasNext(); ) {
                        if (i == random) {
                            code = new String(iterator.next());
                            break;
                        }
                        i++;
                    }
                } else {//其他处理
                    if (guardLevel == 1) {
                        HashSet<String> codes = codeMap.get("总督");
                        if (!CollectionUtils.isEmpty(codes)) {
                            int random = (int) Math.ceil(Math.random() *codes.size()) - 1;
                            int i = 0;
                            for (Iterator<String> iterator =codes.iterator(); iterator.hasNext(); ) {
                                if (i == random) {
                                    code = new String(iterator.next());
                                    break;
                                }
                                i++;
                            }
                        }
                    } else if (guardLevel == 2) {
                        HashSet<String> codes = codeMap.get("提督");
                        if (!CollectionUtils.isEmpty(codes)) {
                            int random = (int) Math.ceil(Math.random() *codes.size()) - 1;
                            int i = 0;
                            for (Iterator<String> iterator = codes.iterator(); iterator.hasNext(); ) {
                                if (i == random) {
                                    code = new String(iterator.next());
                                    break;
                                }
                                i++;
                            }
                        }
                    } else if (guardLevel == 3) {
                        HashSet<String> codes = codeMap.get("舰长");
                        if (!CollectionUtils.isEmpty(codes)) {
                            int random = (int) Math.ceil(Math.random() * codes.size()) - 1;
                            int i = 0;
                            for (Iterator<String> iterator = codes.iterator();
                                 iterator.hasNext(); ) {
                                if (i == random) {
                                    code = new String(iterator.next());
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                    //全部处理
                    if(StringUtils.isBlank(code)) {
                        HashSet<String> codes = codeMap.get("全部");
                        if (!CollectionUtils.isEmpty(codes)) {
                            int random = (int) Math.ceil(Math.random() * codes.size()) - 1;
                            int i = 0;
                            for (Iterator<String> iterator = codes.iterator(); iterator.hasNext(); ) {
                                if (i == random) {
                                    code = new String(iterator.next());
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                }
            }
        }
        return code;
    }

    public static CenterSetConf codeRemove(String code) {
        CenterSetConf centerSetConf = PublicDataConf.centerSetConf;
        HashSet<String> codeStrings = centerSetConf.getThank_gift().getCodeStrings();
        if(StringUtils.isNotBlank(code)) {
            Iterator<String> it = codeStrings.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next.endsWith(code)) {
                    it.remove();
                    LOGGER.info("gift code移除:{}",next);
                }
            }
        }
        LOGGER.info("gift code移除:{}",codeStrings);
        centerSetConf.getThank_gift().setCodeStrings(codeStrings);
        return  centerSetConf;
    }

    public static void codeRemove(HashSet<String> codes,String code) {
        for (Iterator<String> iterator = codes.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            if (next.endsWith(code)) {
                iterator.remove();
                break;
            }
        }
    }

    //最低限度cookie
    public static boolean pariseCookie(String init_cookie) {
        String key = null;
        String value = null;
        int controlNum = 0;
        UserCookie userCookie = new UserCookie();
        init_cookie = StringUtils.remove(init_cookie, (char) 32);
        init_cookie = init_cookie.trim();
        String[] a = init_cookie.split(";");
        for (String string : a) {
            if (string.contains("=")) {
                String[] maps = string.split("=");
                key = maps[0];
                value = maps.length >= 2 ? maps[1] : "";
//                LOGGER.info("key:{},value:{}", key, value);
                if (key.equals("DedeUserID")) {
                    userCookie.setDedeUserID(value);
                    controlNum++;
                } else if (key.equals("bili_jct")) {
                    userCookie.setBili_jct(value);
                    controlNum++;
                } else if (key.equals("DedeUserID__ckMd5")) {
                    userCookie.setDedeUserID__ckMd5(value);
                    controlNum++;
                } else if (key.equals("sid")) {
                    userCookie.setSid(value);
                    controlNum++;
                } else if (key.equals("SESSDATA")) {
                    userCookie.setSESSDATA(value);
                    controlNum++;
                } else {
//						LOGGER.info("获取cookie失败，字段为" + key);
                }
            }
        }
        if (controlNum >= 2) {
            LOGGER.info("用户cookie装载成功");
            PublicDataConf.USERCOOKIE = init_cookie;
            PublicDataConf.COOKIE = userCookie;
            controlNum = 0;
            return true;
        } else {
            LOGGER.info("用户cookie装载失败");
            PublicDataConf.COOKIE = null;
        }
        //写入文件
        return false;
    }


    public synchronized static void autoSendGift() {
        //PublicDataConf.centerSetConf.getAuto_gift().is_open()
        if (CollectionUtils.isEmpty(PublicDataConf.autoSendGiftMap)) {
            PublicDataConf.autoSendGiftMap = new ConcurrentHashMap<>(5);
            PublicDataConf.autoSendGiftMap.put(1, new AutoSendGift(1, "辣条", 1, (short) 0));
            PublicDataConf.autoSendGiftMap.put(6, new AutoSendGift(6, "亿圆", 10, (short) 0));
            PublicDataConf.autoSendGiftMap.put(30607, new AutoSendGift(30607, "小心心", 50, (short) 0));
        }
        //房间集合-轮询勋章(获得对应房间勋章差值) -> 获取礼物包裹(过期排序，计算勋章亲密度)
        if (StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
            LOGGER.info("自动给送礼 -> 未登录");
            return;
        }
        if (!PublicDataConf.centerSetConf.getAuto_gift().is_open() || StringUtils.isBlank(PublicDataConf.centerSetConf.getAuto_gift().getRoom_id()))
            return;
        List<UserMedal> userMedals = HttpUserData.httpGetMedalList();
        List<UserMedal> wait_send_rooms = new LinkedList<>();
        if (CollectionUtils.isEmpty(userMedals)) {
            LOGGER.info("自动给送礼 -> 获取勋章列表失败");
            return;
        }
        //礼物包 姑且写死？
        List<UserBag> userBagList = HttpUserData.httpGetBagList(5067l);
        if (userBagList != null) {
            userBagList = userBagList.stream().filter(userBag ->
                    PublicDataConf.autoSendGiftMap.containsKey(userBag.getGift_id())
            ).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(userBagList)) {
            LOGGER.info("自动给送礼 -> 获取礼物列表失败");
            return;
        }
        String[] roomidStrs = PublicDataConf.centerSetConf.getAuto_gift().getRoom_id().split("，");
        LOGGER.info("自动给送礼pre -> 配置文件:{} ; 发送房间:{} ;", FastJsonUtils.toJson(PublicDataConf.centerSetConf.getAuto_gift()), roomidStrs);
        for (String roomidStr : roomidStrs) {
            if (StringUtils.isNumeric(roomidStr)) {
                long roomid = Long.valueOf(roomidStr);
                //先查找  如果不是短号 就去获取
                Optional<UserMedal> userMedalOptional = userMedals.stream().filter(um ->
                        roomid == um.getRoomid()
                ).findFirst();
                if (userMedalOptional.isPresent()) {
                    wait_send_rooms.add(userMedalOptional.get());
                    LOGGER.info("自动送礼ing -> 添加房间1:{}", roomid);
                } else {
                    RoomInit roomInit = HttpRoomData.httpGetRoomInit(roomid);
                    try {
                        Integer short_id = Optional.ofNullable(roomInit).map(RoomInit::getShort_id).orElse(null);
                        if (short_id != null && short_id != 0) {
                            userMedalOptional = userMedals.stream().filter(um ->
                                    short_id.intValue() == um.getRoomid()
                            ).findFirst();
                            if (userMedalOptional.isPresent()) {
                                wait_send_rooms.add(userMedalOptional.get());
                                LOGGER.info("自动送礼ing -> 添加房间2:{}", roomid);
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("自动送礼异常：{}", e);
                    }
                }
            }
        }
        //拿到房间号开始算了（姑且排除舰长的勋章？）
        userBagList = userBagList.stream()
                .map(userBag -> {
                    userBag.setFeed(PublicDataConf.autoSendGiftMap.get(userBag.getGift_id()).getFeed());
                    return userBag;
                })
                .sorted(Comparator.comparingLong(UserBag::getExpire_at).thenComparingInt(UserBag::getGift_id))
                .collect(Collectors.toList());
        long total = userBagList.stream().map(userBag -> (long) userBag.getFeed() * (long) userBag.getGift_num()).collect(Collectors.summingLong(Long::longValue));
        //未来可能添加 补足策略 和先送策略 现在就先
        // 送策略把
        LOGGER.info("自动给送礼total -> 总量:{} ; 发送房间:{} ; 待发送礼物包裹：{}", total, wait_send_rooms, userBagList);
        for (UserMedal userMedal : wait_send_rooms) {
            if (CollectionUtils.isEmpty(userBagList)) break;
            if (userMedal.getToday_feed() == userMedal.getDay_limit().intValue()) continue;
            long diff_feed = userMedal.getDay_limit() - userMedal.getToday_feed();
            if (diff_feed >= total) {
                for (Iterator<UserBag> iterator = userBagList.iterator(); iterator.hasNext(); ) {
                    UserBag userBag = iterator.next();
                    HttpUserData.httpPostSendBag(userBag, userMedal.getTarget_id(), userMedal.getRoomid());
                    diff_feed = diff_feed - total;
                    userMedal.setToday_feed(userMedal.getToday_feed() + total);
                    //remove
                    iterator.remove();
                }
                userBagList = new ArrayList<>();
            } else {
                //超出 轮询送
                userBagList = handleSendGift(userBagList, diff_feed, userMedal);
            }
        }
    }

    public static List<UserBag> handleSendGift(List<UserBag> userBagList, long diff_feed, UserMedal userMedal) {
        for (Iterator<UserBag> iterator = userBagList.iterator(); iterator.hasNext(); ) {
            UserBag userBag = iterator.next();
            long now_feed = userBag.getFeed() * userBag.getGift_num();
            if (diff_feed >= now_feed) {
                HttpUserData.httpPostSendBag(userBag, userMedal.getTarget_id(), userMedal.getRoomid());
                diff_feed = diff_feed - now_feed;
                userMedal.setToday_feed(userMedal.getToday_feed() + now_feed);
                //remove
                iterator.remove();
            } else {
                int count = 0;
                //如果是辣条
                if (userBag.getGift_num() == 1) {
                    count = (int) diff_feed;
                } else {
                    count = (int) Math.floor(diff_feed / userBag.getFeed());
                }
                if (count == 0) break;
                UserBag userBagCopy = new UserBag();
                BeanUtils.copyProperties(userBag, userBagCopy);
                userBagCopy.setGift_num(count);
                HttpUserData.httpPostSendBag(userBagCopy, userMedal.getTarget_id(), userMedal.getRoomid());
                userBag.setGift_num(userBag.getGift_num() - count);
                diff_feed = diff_feed - (count * userBag.getFeed());
                userMedal.setToday_feed(userMedal.getToday_feed() + (count * userBag.getFeed()));
            }
        }
        return userBagList;
    }

    public static boolean signNow() {
        Date date = new Date();
        int nowDay = JodaTimeUtils.formatToInt(date, "yyyyMMdd");
        if (PublicDataConf.centerSetConf.getPrivacy().getSignDay() != nowDay) {
            HttpUserData.httpGetDoSign();
            PublicDataConf.centerSetConf.getPrivacy().setSignDay(nowDay);
            return true;
        }
        return false;
    }

    public static String dateToCron(Date date) {
        return JodaTimeUtils.format(date, "ss mm HH * * ?");
    }

    public static String dateStringToCron(String dateStr) {
        return JodaTimeUtils.format(JodaTimeUtils.parse(dateStr, "HH:mm:ss"), "ss mm HH * * ?");
    }
}
