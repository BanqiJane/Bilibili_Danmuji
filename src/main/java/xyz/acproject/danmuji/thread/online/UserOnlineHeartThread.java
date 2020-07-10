package xyz.acproject.danmuji.thread.online;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.http.HttpHeartBeatData;

public class UserOnlineHeartThread extends Thread{
//	private static Logger LOGGER = LogManager.getLogger(UserOnlineHeartThread.class);
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
			try {
				Thread.sleep(300*1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				LOGGER.debug("在线心跳线程post 5m关闭:"+e);
//				e.printStackTrace();
			}
			HttpHeartBeatData.httpPostUserOnlineHeartBeat();
		}
	}
}
