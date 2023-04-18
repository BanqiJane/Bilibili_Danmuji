package xyz.acproject.danmuji.thread;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.enums.AdvertStatus;

import java.math.BigDecimal;

/**
 * @ClassName AdvertThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:30:17
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Getter
@Setter
public class AdvertThread extends Thread {
//	@SuppressWarnings("unused")
//	private Logger LOGGER = LogManager.getLogger(AdvertThread.class);
	public volatile boolean FLAG = false;
	private double time =0;
	private String advertBarrage;
	private AdvertStatus advertStatus;

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		String strings[] = null;
		while (!FLAG) {
			if (FLAG) {
				return;
			}
			if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
				return;
			}
			long delay_time = new BigDecimal(getTime()).multiply(new BigDecimal("1000")).longValue();
			if (StringUtils.indexOf(getAdvertBarrage(), "\n") != -1) {
				strings = StringUtils.split(getAdvertBarrage(), "\n");

				if (getAdvertStatus().getCode() == 0) {
					// 顺序发
					for (String string : strings) {
						try {
							Thread.sleep(delay_time);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
//							LOGGER.info("广告姬线程关闭:" + e);
						}
						if (PublicDataConf.sendBarrageThread != null&&!PublicDataConf.sendBarrageThread.FLAG) {
						PublicDataConf.barrageString.add(string);
						synchronized (PublicDataConf.sendBarrageThread) {
							PublicDataConf.sendBarrageThread.notify();
						}
						}
					}
				} else {
					// 随机发
					try {
						Thread.sleep(delay_time);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
//						LOGGER.info("广告姬线程关闭:" + e);
					}
					int strLength = strings.length;
					if (strLength > 1) {
						int randomNum = (int) Math.ceil(Math.random() * strLength);
						if (PublicDataConf.sendBarrageThread != null&&!PublicDataConf.sendBarrageThread.FLAG) {
						PublicDataConf.barrageString.add(strings[randomNum - 1]);
							synchronized (PublicDataConf.sendBarrageThread) {
								PublicDataConf.sendBarrageThread.notify();
							}
						}
					} else {
						if (PublicDataConf.sendBarrageThread != null&&!PublicDataConf.sendBarrageThread.FLAG) {
						PublicDataConf.barrageString.add(getAdvertBarrage());
						synchronized (PublicDataConf.sendBarrageThread) {
							PublicDataConf.sendBarrageThread.notify();
						}
						}
					}

				}

			} else {
				try {
					Thread.sleep(delay_time);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
//					LOGGER.info("广告姬线程关闭:" + e);
				}
				if (PublicDataConf.sendBarrageThread != null&&!PublicDataConf.sendBarrageThread.FLAG) {
				PublicDataConf.barrageString.add(getAdvertBarrage());
				synchronized (PublicDataConf.sendBarrageThread) {
					PublicDataConf.sendBarrageThread.notify();
				}
				}

			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				LOGGER.info("广告姬线程关闭:" + e);
			}

		}
	}


}
