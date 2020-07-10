package xyz.acproject.danmuji.thread.core;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.utils.SpringUtils;

public class ReConnThread extends Thread {
	public volatile boolean RFLAG = false;
	private volatile Integer num =0;
	private ClientService clientService = SpringUtils.getBean(ClientService.class); 

	@Override
	public synchronized void run() {
		// TODO 自动生成的方法存根
		super.run();
		while (!RFLAG) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(RFLAG) {
				return;
			}
			if(num>20) {
				RFLAG=true;
				num=0;
				return;
			}
			if (!PublicDataConf.webSocketProxy.isOpen()) {
				try {
					clientService.reConnService();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				num++;
			System.out.println("每十秒,进行重连第"+num+"次(來源於綫程重連機制beta1.1)");
			}else {
				num=0;
				RFLAG=true;
				return;
			}
		}
	}

}
