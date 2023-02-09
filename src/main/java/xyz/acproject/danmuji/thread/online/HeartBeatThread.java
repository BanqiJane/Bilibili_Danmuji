package xyz.acproject.danmuji.thread.online;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.http.HttpHeartBeatData;

/**
 * @ClassName HeartBeatThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:30:03
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HeartBeatThread extends Thread{
//	private static Logger LOGGER = LogManager.getLogger(HeartBeatThread.class);
	public volatile boolean FLAG = false;
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		while (!FLAG) {
			if (FLAG) {
				return;
			}
			if(PublicDataConf.USER==null) {
				return;
			}
			HttpHeartBeatData.httpGetHeartBeatOrS(null);
			try {
				Thread.sleep(96*1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				LOGGER.info("在线心跳线程96s关闭"+e);
//				e.printStackTrace();
			}
		}
	}
}
