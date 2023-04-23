package xyz.acproject.danmuji.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.file.ProFileTools;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.impl.SetServiceImpl;
import xyz.acproject.danmuji.tools.BASE64Encoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jane
 * @ClassName DanmujiConfig
 * @Description TODO
 * @date 2021/6/15 16:43
 * @Copyright:2021
 */
@Configuration
public class DanmujiInitConfig {
    private Logger LOGGER = LogManager.getLogger(DanmujiInitConfig.class);
    private final String cookies = "ySZL4SBB";
    private SetServiceImpl checkService;

    public void init(){
        Map<String, String> profileMap = new ConcurrentHashMap<>();
        String cookieString = null;
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            profileMap.putAll(ProFileTools.read("DanmujiProfile"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        // 读取本地cookie
        try {
            cookieString = !StringUtils.isEmpty(profileMap.get(cookies))
                    ? new String(base64Encoder.decode(profileMap.get(cookies)))
                    : null;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error("获取本地cookie失败,请重新登录" + e);
            cookieString = null;
        }
        if (!StringUtils.isEmpty(cookieString)) {
            if (StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                PublicDataConf.USERCOOKIE = cookieString;
            }
        }
        HttpUserData.httpGetUserInfo();
        if (PublicDataConf.USER == null) {
            PublicDataConf.USERCOOKIE = null;
        } else {
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                profileMap.put(cookies, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
            }
        }
        // 读取本地set
        try {
            PublicDataConf.centerSetConf = !StringUtils.isEmpty(profileMap.get("set")) ? JSONObject
                    .parseObject(new String(base64Encoder.decode(profileMap.get("set"))), CenterSetConf.class) : null;
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("读取配置文件失败,尝试重新读取" + e);
            PublicDataConf.centerSetConf = null;
        }
        //初始化配置文件开始
        if (PublicDataConf.centerSetConf == null) {
            PublicDataConf.centerSetConf = new CenterSetConf(new ThankGiftSetConf(), new AdvertSetConf(),
                    new ThankFollowSetConf(), new AutoReplySetConf(),new ClockInSetConf(),new ThankWelcomeSetConf(),new AutoSendGiftConf(),new PrivacySetConf(),new BlackListSetConf());
        } else {
            if (PublicDataConf.centerSetConf.getRoomid() != null && PublicDataConf.centerSetConf.getRoomid() > 0)
                PublicDataConf.ROOMID_SAFE = PublicDataConf.centerSetConf.getRoomid();
            if (PublicDataConf.ROOMID_SAFE != null && PublicDataConf.ROOMID_SAFE > 0)
                PublicDataConf.centerSetConf.setRoomid(PublicDataConf.ROOMID_SAFE);
        }
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
        if(PublicDataConf.centerSetConf.getClock_in() ==null){
            PublicDataConf.centerSetConf.setClock_in(new ClockInSetConf(false,"签到"));
        }
        if(PublicDataConf.centerSetConf.getWelcome()==null){
            PublicDataConf.centerSetConf.setWelcome(new ThankWelcomeSetConf());
        }
        if(PublicDataConf.centerSetConf.getAuto_gift()==null){
            PublicDataConf.centerSetConf.setAuto_gift(new AutoSendGiftConf());
        }
        if(PublicDataConf.centerSetConf.getPrivacy()==null){
            PublicDataConf.centerSetConf.setPrivacy(new PrivacySetConf());
        }
        if(PublicDataConf.centerSetConf.getBlack()==null){
            PublicDataConf.centerSetConf.setBlack(new BlackListSetConf());
        }
        //初始化配置文件结束
        profileMap.put("set", base64Encoder.encode(PublicDataConf.centerSetConf.toJson().getBytes()));
        ProFileTools.write(profileMap, "DanmujiProfile");
        try {
            PublicDataConf.centerSetConf = JSONObject
                    .parseObject(new String(base64Encoder.decode(profileMap.get("set"))), CenterSetConf.class);
            LOGGER.info("读取配置文件成功");
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("读取配置文件失败" + e);
        }
        // 分离cookie  改
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE) && PublicDataConf.COOKIE == null) {
            String key = null;
            String value = null;
            int controlNum = 0;
            String cookie = PublicDataConf.USERCOOKIE;
            PublicDataConf.COOKIE = new UserCookie();
            cookie = cookie.trim();
            String[] a = cookie.split(";");
            for (String string : a) {
                if (string.contains("=")) {
                    String[] maps = string.split("=");
                    key = maps[0];
                    value = maps.length >= 2 ? maps[1] : "";
                    if (key.equals("DedeUserID")) {
                        PublicDataConf.COOKIE.setDedeUserID(value);
                        controlNum++;
                    } else if (key.equals("bili_jct")) {
                        PublicDataConf.COOKIE.setBili_jct(value);
                        controlNum++;
                    } else if (key.equals("DedeUserID__ckMd5")) {
                        PublicDataConf.COOKIE.setDedeUserID__ckMd5(value);
                        controlNum++;
                    } else if (key.equals("sid")) {
                        PublicDataConf.COOKIE.setSid(value);
                        controlNum++;
                    } else if (key.equals("SESSDATA")) {
                        PublicDataConf.COOKIE.setSESSDATA(value);
                        controlNum++;
                    } else {
//						LOGGER.info("获取cookie失败，字段为" + key);
                    }
                }
            }
            if (controlNum >= 2) {
                LOGGER.info("用户cookie装载成功");
                controlNum = 0;
            } else {
                LOGGER.info("用户cookie装载失败");
                PublicDataConf.COOKIE = null;
            }
            checkService.holdSet(PublicDataConf.centerSetConf);
        }
        base64Encoder = null;
        profileMap.clear();
    }

    @Autowired
    public void setCheckService(SetServiceImpl checkService) {
        this.checkService = checkService;
    }
}
