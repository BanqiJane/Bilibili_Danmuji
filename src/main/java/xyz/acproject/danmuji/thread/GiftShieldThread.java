package xyz.acproject.danmuji.thread;

import lombok.Getter;
import lombok.Setter;
import xyz.acproject.danmuji.conf.PublicDataConf;

/**
 * @ClassName GiftShieldThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:30:27
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Getter
@Setter
public class GiftShieldThread extends Thread {
//	@SuppressWarnings("unused")
//	private Logger LOGGER = LogManager.getLogger(GiftShieldThread.class);
	public volatile boolean FLAG = false;
	private int time = 300;
	private String giftName;

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
		PublicDataConf.SHIELDGIFTNAME = getGiftName();
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
//			e.printStackTrace();
		}
		PublicDataConf.SHIELDGIFTNAME = null;
	}


}
