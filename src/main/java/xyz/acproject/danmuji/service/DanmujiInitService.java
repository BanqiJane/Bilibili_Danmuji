package xyz.acproject.danmuji.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import xyz.acproject.danmuji.conf.CenterSetConf;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.set.*;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.impl.SetServiceImpl;
import xyz.acproject.danmuji.tools.BASE64Encoder;
import xyz.acproject.danmuji.tools.RequestHeaderTools;
import xyz.acproject.danmuji.tools.file.ProFileTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jane
 * @ClassName DanmujiConfig
 * @Description TODO
 * @date 2021/6/15 16:43
 * @Copyright:2021
 */
@Service
@RequiredArgsConstructor
public class DanmujiInitService {
    private static final Logger LOGGER = LogManager.getLogger(DanmujiInitService.class);// 日志记录对象应是线程安全的

    // 借助lombok的RequiredArgsConstructor自动实现构造函数,此处由Spring利用构造函数完成自动装配
    private final SetServiceImpl checkService;


    /**
     * 初始化配置.如果有配置Cookie,则会以关联用户身份根据配置执行相关操作(每日签到,自动打卡
     */
    public void init() {
        Map<String, String> profileMap = new ConcurrentHashMap<>();
        String cookieString = null;
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 读取本地cookie
        try {
            profileMap.putAll(ProFileTools.read(PublicDataConf.PROFILE_NAME));
            cookieString = !StringUtils.isEmpty(profileMap.get(PublicDataConf.PROFILE_COOKIE_NAME))
                    ? new String(base64Encoder.decode(profileMap.get(PublicDataConf.PROFILE_COOKIE_NAME)))
                    : null;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error("获取本地cookie失败,请重新登录" + e);
        }
        if (StringUtils.isNotBlank(cookieString)&&StringUtils.isBlank(PublicDataConf.USERCOOKIE)) {
            PublicDataConf.USERCOOKIE = cookieString;
        }
        // 方法名未体现数据装载目的,但实际做了装载动作>_<
        // 检查登录状态
        HttpUserData.httpGetUserInfo();
        if (PublicDataConf.USER == null) {
            PublicDataConf.USERCOOKIE = null;
        } else {
            if (StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
                PublicDataConf.COOKIE = HttpUserData.httpBuvid34(new UserCookie().parse(PublicDataConf.USERCOOKIE));
                PublicDataConf.USERCOOKIE = PublicDataConf.COOKIE.getCookie();
                profileMap.put(PublicDataConf.PROFILE_COOKIE_NAME, base64Encoder.encode(PublicDataConf.USERCOOKIE.getBytes()));
            }
        }

        // 装载本地配置集
        // centerSetConf 第一次装配
        if (StringUtils.isNotBlank(profileMap.get(PublicDataConf.PROFILE_SET_NAME))) {
            PublicDataConf.centerSetConf = CenterSetConf.of(profileMap.get(PublicDataConf.PROFILE_SET_NAME));

            if (PublicDataConf.centerSetConf.getRoomid() != null && PublicDataConf.centerSetConf.getRoomid() > 0)
                PublicDataConf.ROOMID_LONG = PublicDataConf.centerSetConf.getRoomid();
            else if (PublicDataConf.ROOMID_LONG != null && PublicDataConf.ROOMID_LONG > 0)
                PublicDataConf.centerSetConf.setRoomid(PublicDataConf.ROOMID_LONG);

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
            if (PublicDataConf.centerSetConf.getAuto_gift() == null) {
                PublicDataConf.centerSetConf.setAuto_gift(new AutoSendGiftConf());
            }
            if (PublicDataConf.centerSetConf.getPrivacy() == null) {
                PublicDataConf.centerSetConf.setPrivacy(new PrivacySetConf());
            }
            if (PublicDataConf.centerSetConf.getBlack() == null) {
                PublicDataConf.centerSetConf.setBlack(new BlackListSetConf());
            }
        } else {
            // 无效的本地配置集则初始化一份
            // 此处无参初始化可以采用聚合根的思想
            PublicDataConf.centerSetConf = new CenterSetConf(new ThankGiftSetConf(), new AdvertSetConf(),
                    new ThankFollowSetConf(), new AutoReplySetConf(), new ClockInSetConf(), new ThankWelcomeSetConf(),
                    new AutoSendGiftConf(), new PrivacySetConf(), new BlackListSetConf());
        }

        //初始化配置文件结束
        profileMap.put(PublicDataConf.PROFILE_SET_NAME, base64Encoder.encode(PublicDataConf.centerSetConf.toJson().getBytes()));
        ProFileTools.write(profileMap, PublicDataConf.PROFILE_NAME);
        // 下方解析操作逻辑冗余, 可以清除
        try {
            // centerSetConf 第二次装配, 第一次与第二次转配期间profileMap的set属性装载仅依赖PublicDataConf.centerSetConf
            PublicDataConf.centerSetConf = CenterSetConf.of(profileMap.get(PublicDataConf.PROFILE_SET_NAME));
            LOGGER.info("读取配置文件成功");
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("读取配置文件失败" + e);
        }

        // 分离cookie  改
        if (StringUtils.isNotEmpty(PublicDataConf.USERCOOKIE) && PublicDataConf.COOKIE == null) {
            int controlNum = 0;
            String cookies = PublicDataConf.USERCOOKIE;
            PublicDataConf.COOKIE = new UserCookie();
            // This cookie should is `cookies`, because it collects some cookies.
            // 这里对Cookie簇的拆解属于Http基础设施, 不属于特定业务场景, 不应该在该方法中显示出现;
            // 解析Cookie簇的基础设施放入{@link RequestHeaderTools#parseCookies}中
            // 业务场景下只获取相应的Cookie来做些事情.
            Map<String, String> cookieKeyValue = RequestHeaderTools.parseCookies(cookies);

            if (cookieKeyValue.containsKey("DedeUserID")) {
                PublicDataConf.COOKIE.setDedeUserID(cookieKeyValue.get("DedeUserID"));
                controlNum++;
            }
            if (cookieKeyValue.containsKey("bili_jct")) {
                PublicDataConf.COOKIE.setBili_jct(cookieKeyValue.get("bili_jct"));
                controlNum++;
            }
            if (cookieKeyValue.containsKey("DedeUserID__ckMd5")) {
                PublicDataConf.COOKIE.setDedeUserID__ckMd5(cookieKeyValue.get("DedeUserID__ckMd5"));
                controlNum++;
            }
            if (cookieKeyValue.containsKey("sid")) {
                PublicDataConf.COOKIE.setSid(cookieKeyValue.get("sid"));
                controlNum++;
            }
            if (cookieKeyValue.containsKey("SESSDATA")) {
                PublicDataConf.COOKIE.setSESSDATA(cookieKeyValue.get("SESSDATA"));
                controlNum++;
            }

            if (controlNum >= 2) {
                LOGGER.info("用户cookie装载成功");
            } else {
                LOGGER.info("用户cookie装载失败");
                PublicDataConf.COOKIE = null;
            }
            checkService.holdSet(PublicDataConf.centerSetConf);
        }
    }
}
