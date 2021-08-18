package xyz.acproject.danmuji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.acproject.danmuji.service.impl.SetServiceImpl;

/**
 * @ClassName BiliBiliDanmujiApplication
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:31:52
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@EnableScheduling
@SpringBootApplication
public class BiliBiliDanmujiApplication implements CommandLineRunner{
	private SetServiceImpl checkService;
	
	public static void main(String[] args) {
		SpringApplication.run(BiliBiliDanmujiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO 自动生成的方法存根
		checkService.init();
	}

	@Autowired
	public void setCheckService(SetServiceImpl checkService) {
		this.checkService = checkService;
	}
}
