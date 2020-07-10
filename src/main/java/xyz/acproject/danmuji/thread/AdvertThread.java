package xyz.acproject.danmuji.thread;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.enums.AdvertStatus;

public class AdvertThread extends Thread {
//	@SuppressWarnings("unused")
//	private Logger LOGGER = LogManager.getLogger(AdvertThread.class);
	public volatile boolean FLAG = false;
	private Short time;
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
			if (StringUtils.indexOf(getAdvertBarrage(), "\n") != -1) {
				strings = StringUtils.split(getAdvertBarrage(), "\n");;

				if (getAdvertStatus().getCode() == 0) {
					// 顺序发
					for (String string : strings) {
						try {
							Thread.sleep(getTime() * 1000);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
//							LOGGER.debug("广告姬线程关闭:" + e);
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
						Thread.sleep(getTime() * 1000);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
//						LOGGER.debug("广告姬线程关闭:" + e);
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
					Thread.sleep(getTime() * 1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
//					LOGGER.debug("广告姬线程关闭:" + e);
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
//				LOGGER.debug("广告姬线程关闭:" + e);
			}

		}
	}

	public Short getTime() {
		return time;
	}

	public void setTime(Short time) {
		this.time = time;
	}

	public String getAdvertBarrage() {
		return advertBarrage;
	}

	public void setAdvertBarrage(String advertBarrage) {
		this.advertBarrage = advertBarrage;
	}

	public AdvertStatus getAdvertStatus() {
		return advertStatus;
	}

	public void setAdvertStatus(AdvertStatus advertStatus) {
		this.advertStatus = advertStatus;
	}

}
