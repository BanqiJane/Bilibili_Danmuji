package xyz.acproject.danmuji.thread.core;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.service.impl.ClientServiceImpl;
import xyz.acproject.danmuji.utils.SpringUtils;

/**
 * @ClassName ReConnThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:52
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ReConnThread extends Thread {
	public volatile boolean RFLAG = false;
	private volatile Integer num =0;
	private ClientServiceImpl clientService = SpringUtils.getBean(ClientServiceImpl.class); 
	private int TIME = 10000;

	@Override
	public synchronized void run() {
		// TODO 自动生成的方法存根
		super.run();
		while (!RFLAG) {
			try {
				Thread.sleep(TIME);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(RFLAG) {
				return;
			}
			if(num>20) {
				this.TIME = 300000;
//				RFLAG=true;
//				num=0;
//				return;
			}
			if (!PublicDataConf.webSocketProxy.isOpen()) {
				try {
					clientService.reConnService();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				num++;
			System.out.println("每"+this.TIME/1000+"秒,进行重连第"+num+"次(來源於綫程重連機制beta1.2)");
			}else {
				num=0;
				RFLAG=true;
				return;
			}
		}
	}

}
