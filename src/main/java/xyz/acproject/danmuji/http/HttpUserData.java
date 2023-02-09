package xyz.acproject.danmuji.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.login_data.LoginData;
import xyz.acproject.danmuji.entity.login_data.Qrcode;
import xyz.acproject.danmuji.entity.user_data.User;
import xyz.acproject.danmuji.entity.user_data.UserBag;
import xyz.acproject.danmuji.entity.user_data.UserManager;
import xyz.acproject.danmuji.entity.user_data.UserMedal;
import xyz.acproject.danmuji.entity.user_in_room_barrageMsg.UserBarrageMsg;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.OkHttp3Utils;
import xyz.acproject.danmuji.utils.UrlUtils;

import java.util.*;

/**
 * @author BanqiJane
 * @ClassName HttpUserData
 * @Description TODO
 * @date 2020年8月10日 下午12:29:05
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpUserData {
    private static Logger LOGGER = LogManager.getLogger(HttpUserData.class);

    /**
     * 初始化 获取用户信息+判断是否登陆状态
     */
    public static void httpGetUser() {
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            data = OkHttp3Utils.getHttp3Utils().httpGet("https://account.bilibili.com/home/USERInfo", headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            LOGGER.info("已经登录:" + jsonObject.toString());
        } else if (code == -101) {
            LOGGER.info("未登录:" + jsonObject.toString());
        } else {
            LOGGER.error("未知错误,原因未知" + jsonObject.toString());
        }
    }

    /**
     * 获取登陆二维码
     *
     * @return
     */
    public static Qrcode httpGetQrcode() {
        String data = null;
        JSONObject jsonObject = null;
        Qrcode qrcode = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("https://passport.bilibili.com/qrcode/getLoginUrl", headers, null).body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return qrcode;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            qrcode = JSONObject.parseObject(jsonObject.getString("data"), Qrcode.class);
        } else {
            LOGGER.error("获取二维码失败,未知错误,原因未知" + jsonObject.toString());
        }
        return qrcode;
    }

    /**
     * 判断扫码状态 扫码确定后 获取用户cookie
     *
     * @param logindata
     * @return
     */
    public static String httpPostCookie(LoginData logindata) {
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            return "";
        }
        String data = null;
        Response response = null;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        headers = new HashMap<>(3);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://passport.bilibili.com/login");
        params = new HashMap<>(3);
        params.put("oauthKey", logindata.getOauthKey());
        params.put("gourl", logindata.getGourl());
        try {
            response = OkHttp3Utils.getHttp3Utils().httpPostForm("https://passport.bilibili.com/qrcode/getLoginInfo",
                    headers, params);
            data = response.body().string();
            if (JSONObject.parseObject(data).getBoolean("status")) {
                Headers headers2 = response.headers();
                List<String> cookies = headers2.values("Set-Cookie");
                Set<String> cookieSet = new HashSet<>();
                for (String string : cookies) {
                    cookieSet.add(string.substring(0, string.indexOf(";")));
                }
                StringBuilder stringBuilder = new StringBuilder(100);
                Iterator<String> iterable = cookieSet.iterator();
                while (iterable.hasNext()) {
                    stringBuilder.append(iterable.next());
                    if (iterable.hasNext()) {
                        stringBuilder.append(";");
                    }
                }
                PublicDataConf.USERCOOKIE = stringBuilder.toString();
                if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                    if (PublicDataConf.ROOMID != null) {
                        httpGetUserBarrageMsg();
                    }
                    LOGGER.info("扫码登录成功");
                }
            }
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            LOGGER.error("扫码登录失败抛出异常:" + e1);
        }

        return data;
    }

    /**
     * 获取用户信息 需要cookie 初始化
     */
    public static void httpGetUserInfo() {
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(3);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            data = OkHttp3Utils.getHttp3Utils().httpGet("https://api.live.bilibili.com/User/getUserInfo", headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return;
        jsonObject = JSONObject.parseObject(data);
        if (jsonObject.getString("code").equals("REPONSE_OK")) {
            PublicDataConf.USER = new User();
            PublicDataConf.USER = JSONObject.parseObject(jsonObject.getString("data"), User.class);
            LOGGER.info("已经登录，获取信息成功");
        } else if (jsonObject.getShort("code") == -500) {
            LOGGER.info("未登录，请登录:" + jsonObject.toString());
            PublicDataConf.USERCOOKIE = null;
            PublicDataConf.USER = null;
        } else {
            LOGGER.error("未知错误,原因未知" + jsonObject.toString());
            PublicDataConf.USERCOOKIE = null;
            PublicDataConf.USER = null;
        }
    }

    /**
     * 获取用户所在房间是否是房管
     */
    public static void httpGetUserIsManager() {
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByUser?room_id="
                            + CurrencyTools.parseRoomId(), headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            LOGGER.info("获取本房间自身管理信息成功");
            if (PublicDataConf.ROOMID != null && PublicDataConf.ROOMID > 0) {
                Boolean manager = jsonObject.getJSONObject("data").getJSONObject("badge").getBoolean("is_room_admin");
                PublicDataConf.USERMANAGER = new UserManager();
                PublicDataConf.USERMANAGER.set_manager(manager != null ? manager : false);
                PublicDataConf.USERMANAGER.setRoomid(PublicDataConf.ROOMID);
                PublicDataConf.USERMANAGER.setShort_roomid(CurrencyTools.parseRoomId());
            }
        } else if (code == -101) {
            LOGGER.info("未登录，请登录:" + jsonObject.toString());
        } else if (code == -400) {
            LOGGER.info("房间号不存在或者未输入房间号:" + jsonObject.toString());
        } else {
            LOGGER.error("未知错误,原因未知" + jsonObject.toString());
        }
    }

    /**
     * 获取用户在目标房间所能发送弹幕的最大长度
     */
    public static void httpGetUserBarrageMsg() {
        if(CurrencyTools.parseRoomId()==0)return;
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByUser?room_id="
                            + CurrencyTools.parseRoomId(), headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            LOGGER.info("获取本房间可发送弹幕长度+是否是管理员 成功");
            PublicDataConf.USERBARRAGEMESSAGE = JSONObject
                    .parseObject((((JSONObject) jsonObject.get("data")).getString("property")), UserBarrageMsg.class);
            Boolean manager = jsonObject.getJSONObject("data").getJSONObject("badge").getBoolean("is_room_admin");
            PublicDataConf.USERMANAGER = new UserManager();
            PublicDataConf.USERMANAGER.set_manager(manager != null ? manager : false);
            PublicDataConf.USERMANAGER.setRoomid(PublicDataConf.ROOMID);
            PublicDataConf.USERMANAGER.setShort_roomid(CurrencyTools.parseRoomId());
        } else if (code == -101) {
            LOGGER.info("未登录，请登录:" + jsonObject.toString());
        } else if (code == -400) {
            LOGGER.info("房间号不存在或者未输入房间号:" + jsonObject.toString());
        } else {
            LOGGER.error("未知错误,原因未知" + jsonObject.toString());
        }
    }

    /**
     * 获取用户在目标房间所能发送弹幕的最大长度
     */
    public static UserBarrageMsg httpGetUserBarrageMsg(Long roomId) {
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + roomId);
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByUser?room_id="
                            + roomId, headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return null;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            LOGGER.info("获取本房间可发送弹幕长度成功");
            UserBarrageMsg barrageMsg = JSONObject
                    .parseObject((((JSONObject) jsonObject.get("data")).getString("property")), UserBarrageMsg.class);
            return barrageMsg;
        } else if (code == -101) {
            LOGGER.info("未登录，请登录:" + jsonObject.toString());
        } else if (code == -400) {
            LOGGER.info("房间号不存在或者未输入房间号:" + jsonObject.toString());
        } else {
            LOGGER.error("未知错误,原因未知" + jsonObject.toString());
        }
        return null;
    }

    /**
     * 发送弹幕
     *
     * @param msg 弹幕信息
     * @return
     */
    public static Short httpPostSendBarrage(String msg) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        if (PublicDataConf.USERBARRAGEMESSAGE == null || PublicDataConf.COOKIE == null)
            return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(10);
        params.put("color", PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getColor().toString());
        params.put("fontsize", "25");
        params.put("mode", PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getMode().toString());
        params.put("msg", UrlUtils.URLEncoderString(msg,"utf-8"));
        params.put("rnd", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        params.put("roomid", PublicDataConf.ROOMID.toString());
        params.put("bubble", PublicDataConf.USERBARRAGEMESSAGE.getBubble().toString());
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        try {
            data = OkHttp3Utils.getHttp3Utils().httpPostForm("https://api.live.bilibili.com/msg/send", headers, params)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
//		System.out.println(jsonObject.toJSONString().toString());
        if (jsonObject != null) {
            code = jsonObject.getShort("code");
            if (code == 0) {
                if (StringUtils.isEmpty(jsonObject.getString("message").trim())) {
//				LOGGER.info("发送弹幕成功");
                } else if (jsonObject.getString("message").equals("msg in 1s")
                        || jsonObject.getString("message").equals("msg repeat")) {
                    LOGGER.info("发送弹幕失败，尝试重新发送" + jsonObject.getString("message"));
                    PublicDataConf.barrageString.add(msg);
                    synchronized (PublicDataConf.sendBarrageThread) {
                        PublicDataConf.sendBarrageThread.notify();
                    }
                } else {
                    LOGGER.info(jsonObject.toString());
                    String message = jsonObject.getString("message");
                    if("f".equals(message)||"k".equals(message)) message="触发破站关键字，请检查发送弹幕是否含有破站屏蔽词或者非法词汇";
                    LOGGER.error("发送弹幕失败,原因:" + message);
                    code = -402;
                }
            } else if (code == -111) {
                LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
            } else if (code == -500) {
                LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
            } else if (code == 11000) {
                LOGGER.error("发送弹幕失败,原因:弹幕含有关键字或者弹幕颜色不存在:" + jsonObject.getString("message"));
            } else {
                LOGGER.error("发送弹幕失败,未知错误,原因未知" + jsonObject.toString());
            }
        } else {
            return code;
        }
        return code;
    }

    /**
     * 发送弹幕
     *
     * @param msg 弹幕信息
     * @return
     */
    public static Short httpPostSendBarrage(String msg, Long roomId) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        UserBarrageMsg userBarrageMsg = httpGetUserBarrageMsg(roomId);
        if (userBarrageMsg == null || PublicDataConf.COOKIE == null)
            return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + roomId);
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(10);
        params.put("color", userBarrageMsg.getDanmu().getColor().toString());
        params.put("fontsize", "25");
        params.put("mode", userBarrageMsg.getDanmu().getMode().toString());
        params.put("msg",  UrlUtils.URLEncoderString(msg,"utf-8"));
        params.put("rnd", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        params.put("roomid", roomId.toString());
        params.put("bubble", userBarrageMsg.getBubble().toString());
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        try {
            data = OkHttp3Utils.getHttp3Utils().httpPostForm("https://api.live.bilibili.com/msg/send", headers, params)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
//		System.out.println(jsonObject.toJSONString().toString());
        if (jsonObject != null) {
            code = jsonObject.getShort("code");
            if (code == 0) {
                if (StringUtils.isEmpty(jsonObject.getString("message").trim())) {
//				LOGGER.info("发送弹幕成功");
                } else if (jsonObject.getString("message").equals("msg in 1s")
                        || jsonObject.getString("message").equals("msg repeat")) {
                    LOGGER.info("发送弹幕失败，尝试重新发送" + jsonObject.getString("message"));
                } else {
                    LOGGER.info(jsonObject.toString());
                    LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
                    code = -402;
                }
            } else if (code == -111) {
                LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
            } else if (code == -500) {
                LOGGER.error("发送弹幕失败,原因:" + jsonObject.getString("message"));
            } else if (code == 11000) {
                LOGGER.error("发送弹幕失败,原因:弹幕含有关键字或者弹幕颜色不存在:" + jsonObject.getString("message"));
            } else {
                LOGGER.error("发送弹幕失败,未知错误,原因未知" + jsonObject.toString());
            }
        } else {
            return code;
        }
        return code;
    }

    /**
     * 发送私聊
     *
     * @param recId 接受人uid
     * @param msg   信息
     * @return
     */
    public static Short httpPostSendMsg(long recId, String msg) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        if (PublicDataConf.COOKIE == null)
            return code;
        if (PublicDataConf.USER.getUid() == recId)
            return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://message.bilibili.com/");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(16);
        params.put("msg[sender_uid]", PublicDataConf.USER.getUid().toString());
        params.put("msg[receiver_id]", String.valueOf(recId));
        params.put("msg[receiver_type]", "1");
        params.put("msg[msg_type]", "1");
        params.put("msg[msg_status]", "0");
        params.put("msg[content]", UrlUtils.URLEncoderString("{\"content\":\"" + msg + "\"}","utf-8"));
        params.put("msg[timestamp]", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        params.put("msg[new_face_version]", "1");
        params.put("msg[dev_id]", UUID.randomUUID().toString());
        params.put("from_firework", "0");
        params.put("build", "0");
        params.put("mobi_app", "web");
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("https://api.vc.bilibili.com/web_im/v1/web_im/send_msg", headers, params).body()
                    .string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
        code = jsonObject.getShort("code");
        if (code == 0) {
            // 发送私聊成功
        } else {
            LOGGER.error("发送私聊失败,未知错误,原因未知" + jsonObject.toString());
        }
        return code;
    }


    /**
     * 送礼
     *
     * @param userBag 用户包
     * @param ruid    ruid
     * @param roomid  roomid
     * @return {@link Short}
     */
    public static Short httpPostSendBag(UserBag userBag,long ruid,long roomid) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        if (PublicDataConf.COOKIE == null)
            return code;
//		if (PublicDataConf.USER.getUid() == recId)
//			return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(17);
        params.put("uid", String.valueOf(PublicDataConf.USER.getUid()));
        params.put("gift_id", String.valueOf(userBag.getGift_id()));
        params.put("ruid", String.valueOf(ruid));
        params.put("send_ruid", "0");
        params.put("gift_num",  String.valueOf(userBag.getGift_num()));
        params.put("bag_id", String.valueOf(userBag.getBag_id()));
        params.put("platform", "pc");
        params.put("biz_code", "Live");
        params.put("biz_id", String.valueOf(roomid));
        params.put("rnd", String.valueOf(JodaTimeUtils.getTimestamp()));
        params.put("metadata", "");
        params.put("price", "0");
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        params.put("visit_id", "");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("https://api.live.bilibili.com/xlive/revenue/v1/gift/sendBag", headers, params).body()
                    .string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
        code = jsonObject.getShort("code");
        if (code == 0) {
            // 发送私聊成功
            LOGGER.info("赠送礼物成功,赠送房间:{},赠送主播id:{},送出礼物:{},个数:{},亲密度:{}",roomid,ruid,userBag.getGift_name(),userBag.getGift_num(),userBag.getFeed()*userBag.getGift_num());
        } else {
            LOGGER.error("赠送礼物失败,未知错误,原因未知" + jsonObject.toString());
        }
//        LOGGER.info("赠送礼物成功,赠送房间:{},赠送主播:{},送出礼物:{},个数:{},亲密度:{}",roomid,ruid,userBag.getGift_name(),userBag.getGift_num(),userBag.getFeed()*userBag.getGift_num());
        return 1;
    }

    /**
     * 禁言
     *
     * @param uid  被禁言人uid
     * @param hour 禁言时间 单位小时
     * @return
     */
    public static Short httpPostAddBlock(long uid, short hour) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        if (hour < 1) {
            hour = 1;
        }
        if (hour > 720) {
            hour = 720;
        }
        if (PublicDataConf.COOKIE == null)
            return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(7);
        params.put("roomid", PublicDataConf.ROOMID.toString());
        params.put("block_uid", String.valueOf(uid));
        params.put("hour", String.valueOf(hour));
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        params.put("visit_id", "");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("https://api.live.bilibili.com/banned_service/v2/Silent/add_block_user", headers,
                            params)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
        code = jsonObject.getShort("code");
        if (code == 0) {
            // 禁言成功
//			System.out.println(jsonObject.getString("data"));
        } else {
            LOGGER.error("禁言失败,原因" + jsonObject.getString("msg"));
        }
        return code;
    }

    public static String httpGetUserFaces(long uid) {
        String data = null;
        JSONObject jsonObject = null;
        String faceUrl = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(3);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://space.bilibili.com/" + uid);
        try {
            data = OkHttp3Utils.getHttp3Utils().httpGet("https://api.bilibili.com/x/space/acc/info?mid=" + uid, headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return null;
        jsonObject = JSONObject.parseObject(data);
        short code = jsonObject.getShort("code");
        if (code == 0) {
            faceUrl = ((JSONObject) jsonObject.get("data")).getString("face");
        } else {
            LOGGER.error("获取头像错误,未知错误,原因未知" + jsonObject.toString());
        }
        return faceUrl;
    }


    /**
     * 退出 删除cookie
     */
    public static void quit() {
        Map<String, String> headers = null;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://www.bilibili.com/");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            OkHttp3Utils.getHttp3Utils().httpGet("https://passport.bilibili.com/login?act=exit", headers, null);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
        }
    }

    /**
     * 签到
     *
     * @return
     */
    public static void httpGetDoSign() {
        String data = null;
        JSONObject jsonObject = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://link.bilibili.com/p/center/index");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        try {
            data = OkHttp3Utils.getHttp3Utils().httpGet("https://api.live.bilibili.com/xlive/web-ucenter/v1/sign/DoSign", headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return;
        jsonObject = JSONObject.parseObject(data);
        int code = jsonObject.getShort("code");
        if (code == 0) {
            LOGGER.info(((JSONObject) jsonObject.get("data")).getString("specialText"));
        } else if (code == 1011040) {
            LOGGER.info(jsonObject.get("message"));
        } else {
            LOGGER.error("签到失败，原因：" + jsonObject.toString());
        }
    }

    public static List<UserMedal> httpGetMedalList() {
        String data = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        List<UserMedal> userMedals = new ArrayList<>();
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        headers = new HashMap<>(3);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(3);
        try {
            int nowPage = 1;
            while (true) {
                params.put("page", String.valueOf(nowPage));
                params.put("page_size", "10");
                data = OkHttp3Utils.getHttp3Utils()
                        .httpGet("https://api.live.bilibili.com/xlive/app-ucenter/v1/user/GetMyMedals", headers, params)
                        .body().string();
                if (data == null)
                    return null;
                jsonObject = JSONObject.parseObject(data);
                code = jsonObject.getShort("code");
                if (code == 0) {
                    int totalPage = jsonObject.getJSONObject("data").getJSONObject("page_info").getInteger("total_page");
                    if (totalPage != 0) {
                        jsonArray = jsonObject.getJSONObject("data").getJSONArray("items");
                        if (jsonArray != null) {
                            List<UserMedal> userMedalList = jsonArray.toJavaList(UserMedal.class);
                            userMedals.addAll(userMedalList);
                        }
                    }
                    if(nowPage==totalPage){
                        break;
                    }
                } else {
                    LOGGER.error("获取勋章失败，原因：" + jsonObject.toString());
                    break;
                }
                nowPage++;
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        return userMedals;
    }

    //https://api.live.bilibili.com/xlive/web-room/v1/gift/bag_list

    public static List<UserBag> httpGetBagList(Long roomid) {
        String data = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        headers = new HashMap<>(3);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(3);
        params.put("t", String.valueOf(JodaTimeUtils.getcurrMills()));
        params.put("room_id", String.valueOf(roomid));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("https://api.live.bilibili.com/xlive/web-room/v1/gift/bag_list", headers, params)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return null;
        jsonObject = JSONObject.parseObject(data);
        code = jsonObject.getShort("code");
        if (code == 0) {
            jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
            if (jsonArray != null) {
                List<UserBag> userBagList = jsonArray.toJavaList(UserBag.class);
                return userBagList;
            }
        } else {
            LOGGER.error("获取礼物包失败，原因：" + jsonObject.toString());
        }
        return null;
    }


    /**
     * 解除禁言
     *
     * @return
     * @Param bid block id
     */
    public static Short httpPostDeleteBlock(long bid) {
        JSONObject jsonObject = null;
        String data = null;
        short code = -1;
        Map<String, String> headers = null;
        Map<String, String> params = null;
        if (PublicDataConf.COOKIE == null)
            return code;
        headers = new HashMap<>(4);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        headers.put("referer", "https://live.bilibili.com/" + CurrencyTools.parseRoomId());
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            headers.put("cookie", PublicDataConf.USERCOOKIE);
        }
        params = new HashMap<>(7);
        params.put("id", String.valueOf(bid));
        params.put("roomid", PublicDataConf.ROOMID.toString());
        params.put("csrf_token", PublicDataConf.COOKIE.getBili_jct());
        params.put("csrf", PublicDataConf.COOKIE.getBili_jct());
        params.put("visit_id", "");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("https://api.live.bilibili.com/banned_service/v1/Silent/del_room_block_user", headers,
                            params)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            data = null;
        }
        if (data == null)
            return code;
        jsonObject = JSONObject.parseObject(data);
        code = jsonObject.getShort("code");
        if (code == 0) {
            // 解禁成功
//			System.out.println(jsonObject.getString("data"));
        } else {
            LOGGER.error("解除禁言失败,原因" + jsonObject.getString("msg"));
        }
        return code;
    }
}
