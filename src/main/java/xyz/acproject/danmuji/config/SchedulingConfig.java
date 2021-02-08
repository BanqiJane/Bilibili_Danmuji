package xyz.acproject.danmuji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @ClassName SchedulingConfig
 * @Description TODO
 * @author BanqiJane
 * @date 2020年9月5日 下午3:40:48
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Configuration
public class SchedulingConfig {
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		//线程数
		taskScheduler.setPoolSize(5);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setThreadNamePrefix("定时任务-");
		return taskScheduler;
	}
}
