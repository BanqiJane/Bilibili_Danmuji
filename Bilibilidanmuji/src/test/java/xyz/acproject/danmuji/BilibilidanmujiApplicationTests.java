package xyz.acproject.danmuji;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.acproject.danmuji.service.ClientService;
@RunWith(SpringRunner.class)
@SpringBootTest
class BilibilidanmujiApplicationTests {
	@Autowired
	private ClientService clientService;
	@Test
	void contextLoads(){
			try {
				clientService.startService();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	}

}
