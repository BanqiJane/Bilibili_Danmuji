package xyz.acproject.danmuji.component.task;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.user_data.UserMedal;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.util.Date;
import java.util.List;

@Component("dosignTask")
public class DoSignTask {

    private SetService setService;
    private static Logger LOGGER = LogManager.getLogger(DoSignTask.class);

    public void dosign() {
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            boolean flag = CurrencyTools.signNow();
            if (flag) {
                setService.changeSet(PublicDataConf.centerSetConf,false);
            }
        } else {
            LOGGER.error("定时任务抛出： 未登录 签到失败");
        }
    }

    public void clockin() {
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            List<UserMedal> userMedals = CurrencyTools.getAllUserMedals();
            if (CollectionUtils.isEmpty(userMedals)) return;
            int max = CurrencyTools.clockIn(userMedals);
            if (max == userMedals.size()) {
                Date date = new Date();
                int nowDay = JodaTimeUtils.formatToInt(date, "yyyyMMdd");
                if (PublicDataConf.centerSetConf.getPrivacy().is_open()) {
                    PublicDataConf.centerSetConf.getPrivacy().setClockInDay(nowDay);
                    setService.changeSet(PublicDataConf.centerSetConf,false);
                } else {
                    HttpOtherData.httpPOSTSetClockInRecord();
                }
            }
        } else {
            LOGGER.error("定时任务抛出： 未登录 打卡失败");
        }
    }

    public void autosendgift() {
        if (StringUtils.isNotBlank(PublicDataConf.USERCOOKIE)) {
            CurrencyTools.autoSendGift();
        } else {
            LOGGER.error("定时任务抛出： 未登录 自动送礼失败");
        }
    }

    @Autowired
    public void setSetService(SetService setService) {
        this.setService = setService;
    }
}
