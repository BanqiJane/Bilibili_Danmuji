package xyz.acproject.danmuji.thread;

import lombok.Getter;
import lombok.Setter;
import xyz.acproject.danmuji.conf.PublicDataConf;

/**
 * @ClassName FollowShieldThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:30:23
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Getter
@Setter
public class FollowShieldThread extends Thread{
//	@SuppressWarnings("unused")
//	private Logger LOGGER = LogManager.getLogger(GiftShieldThread.class);
	public volatile boolean FLAG = false;
	private int time = 300;

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		if (FLAG) {
			return;
		}
		if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
			return;
		}
		PublicDataConf.ISSHIELDFOLLOW = true;
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
//			e.printStackTrace();
		}
		PublicDataConf.ISSHIELDFOLLOW = false;
	}


}