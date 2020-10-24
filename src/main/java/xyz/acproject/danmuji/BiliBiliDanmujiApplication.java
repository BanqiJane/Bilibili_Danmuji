package xyz.acproject.danmuji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
	@Autowired
	private SetServiceImpl checkService;
	
	public static void main(String[] args) {
		SpringApplication.run(BiliBiliDanmujiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO 自动生成的方法存根
		checkService.init(0);
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
