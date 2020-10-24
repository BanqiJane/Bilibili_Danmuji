package xyz.acproject.danmuji.component.task;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.http.HttpUserData;

@Component("dosignTask")
public class DoSignTask {
	private static Logger LOGGER = LogManager.getLogger(DoSignTask.class);
	public void dosign() {
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			HttpUserData.httpGetDoSign();
		}else {
			LOGGER.error("定时任务抛出： 未登录 签到失败");
		}
	}
	
}
