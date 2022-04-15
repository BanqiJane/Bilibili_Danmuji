package xyz.acproject.danmuji.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.service.SetService;

@Component
public class TimeTaskConfig {
    private SetService setService;
    private static final Logger LOGGER = LogManager.getLogger(TimeTaskConfig.class);

    @Scheduled(cron = "0 0 01,13 * * ?")
    public void checkSet(){
        try {
            setService.holdSet(PublicDataConf.centerSetConf);
        } catch (Exception e) {
            LOGGER.error("定时,检查配置文件出错 -> {}",e);
        }
    }

    @Autowired
    public void setSetService(SetService setService) {
        this.setService = setService;
    }
}
