package xyz.acproject.danmuji.thread;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.ThreadConf;
import xyz.acproject.danmuji.controller.DanmuWebsocket;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

public class ParseFollowThread extends Thread {
//	private Logger LOGGER = LogManager.getLogger(ParseFollowThread.class);
	public volatile boolean FLAG = false;
	private Boolean isThankFollow = false;
	private String thankFollowString = "感谢 %uNames% 的关注";
	private ConcurrentHashMap<Long, String> followsConcurrentHashMap = new ConcurrentHashMap<Long, String>(251);
	private Short num = 1;
	private DanmuWebsocket danmuWebsocket = SpringUtils.getBean(DanmuWebsocket.class);

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		ConcurrentHashMap<Long, String> followConcurrentHashMap = new ConcurrentHashMap<Long, String>(251);
		ConcurrentHashMap<Long, String> oldConcurrentHashMap = new ConcurrentHashMap<Long, String>(251);
		String thankFollowStr = null;
		StringBuilder stringBuilder = new StringBuilder(50);
		Long mid = null;
		int page = 0;
		while (!FLAG) {
			if (FLAG) {
				return;
			}
			if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
				return;
			}
			if (PublicDataConf.FANSNUM > 0) {
				if (oldConcurrentHashMap.size() < 1) {
					try {
						oldConcurrentHashMap.clear();
						oldConcurrentHashMap.putAll(HttpRoomData.httpGetFollowers());
					} catch (Exception e) {
						// TODO: handle exception
//						LOGGER.debug("关注线程抛出异常" + e);
					}
				} else {
					oldConcurrentHashMap.clear();
					oldConcurrentHashMap.putAll(followsConcurrentHashMap);
				}
			} else {
				oldConcurrentHashMap.clear();
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				LOGGER.debug("处理关注信息线程关闭:" + e);
//				e.printStackTrace();
			}
			try {
				followConcurrentHashMap.putAll(HttpRoomData.httpGetFollowers());
			} catch (Exception e) {
				// TODO: handle exception
//				LOGGER.debug("关注线程抛出异常" + e);
			}
			if (followsConcurrentHashMap.size() > 0) {
				followsConcurrentHashMap.clear();
			}
			if (followConcurrentHashMap.size() < 1) {
				oldConcurrentHashMap.clear();
			}
			followsConcurrentHashMap.putAll(followConcurrentHashMap);

			if (oldConcurrentHashMap.size() > 0 && followConcurrentHashMap.size() > 0) {
				for (Iterator<Map.Entry<Long, String>> iterator = followConcurrentHashMap.entrySet()
						.iterator(); iterator.hasNext();) {
					mid = iterator.next().getKey();
					for (Entry<Long, String> entry : oldConcurrentHashMap.entrySet()) {
						if (mid.equals(entry.getKey())) {
							followConcurrentHashMap.remove(mid);
						}
					}
				}
				page = (int) Math.ceil((double) followConcurrentHashMap.size() / (double) getNum());
				for (Entry<Long, String> entry : followConcurrentHashMap.entrySet()) {
					stringBuilder.append(JodaTimeUtils.format(System.currentTimeMillis())).append(":新的关注:")
							.append(entry.getValue()).append(" 关注了直播间");
					try {
						danmuWebsocket.sendMessage(stringBuilder.toString());
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
//						e.printStackTrace();
					}
					System.out.println(stringBuilder.toString());
					if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
						PublicDataConf.logString.add(stringBuilder.toString());
						synchronized (PublicDataConf.logThread) {
							PublicDataConf.logThread.notify();
						}
					}
					stringBuilder.delete(0, stringBuilder.length());
				}
				if (getNum() > 1 && followConcurrentHashMap.size() > 1) {
					// 多个关注test 代码
					for (int i = 0; i < page; i++) {
						if (getIsThankFollow() && !StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
							thankFollowStr = getThankFollowString().replaceAll("%uNames%",
									numPrint(followConcurrentHashMap, getNum()));
							PublicDataConf.barrageString.add(thankFollowStr);
							synchronized (PublicDataConf.sendBarrageThread) {
								PublicDataConf.sendBarrageThread.notify();
							}
						}
					}
				} else {
					// 单个关注
					for (Entry<Long, String> entry : followConcurrentHashMap.entrySet()) {
						if (getIsThankFollow() && !StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
							thankFollowStr = getThankFollowString().replaceAll("%uNames%", entry.getValue());
							PublicDataConf.barrageString.add(thankFollowStr);
							if (PublicDataConf.sendBarrageThread != null) {
								synchronized (PublicDataConf.sendBarrageThread) {
									PublicDataConf.sendBarrageThread.notify();
								}
							} else {
								ThreadConf.startSendBarrageThread();
							}
						}
					}
				}

			} else if (oldConcurrentHashMap.size() < 1 && followConcurrentHashMap.size() > 0) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				page = (int) Math.ceil((double) followConcurrentHashMap.size() / (double) getNum());
				for (Entry<Long, String> entry : followConcurrentHashMap.entrySet()) {
					stringBuilder.append(JodaTimeUtils.format(System.currentTimeMillis())).append(":新的关注:")
							.append(entry.getValue()).append(" 关注了直播间");
					try {
						danmuWebsocket.sendMessage(stringBuilder.toString());
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
//						e.printStackTrace();
					}
					System.out.println(stringBuilder.toString());
					if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
						PublicDataConf.logString.add(stringBuilder.toString());
						synchronized (PublicDataConf.logThread) {
							PublicDataConf.logThread.notify();
						}
					}
					stringBuilder.delete(0, stringBuilder.length());
				}
				if (getNum() > 1 && followConcurrentHashMap.size() > 1) {
					// 多个关注test 代码
					for (int i = 0; i < page; i++) {
						if (getIsThankFollow() && !StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
							thankFollowStr = getThankFollowString().replaceAll("%uNames%",
									numPrint(followConcurrentHashMap, getNum()));
							PublicDataConf.barrageString.add(thankFollowStr);
							if (PublicDataConf.sendBarrageThread != null) {
								synchronized (PublicDataConf.sendBarrageThread) {
									PublicDataConf.sendBarrageThread.notify();
								}
							} else {
								ThreadConf.startSendBarrageThread();
							}
						}
					}
				} else {
					// 单个关注
					for (Entry<Long, String> entry : followConcurrentHashMap.entrySet()) {
						if (getIsThankFollow() && !StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
							thankFollowStr = getThankFollowString().replaceAll("%uNames%", entry.getValue());
							if (PublicDataConf.sendBarrageThread != null) {
								synchronized (PublicDataConf.sendBarrageThread) {
									PublicDataConf.sendBarrageThread.notify();
								}
							} else {
								ThreadConf.startSendBarrageThread();
							}
						}
					}
				}
			}
			followConcurrentHashMap.clear();
		}

	}

	public String numPrint(ConcurrentHashMap<Long, String> concurrentHashMap, int max) {
		int i = 1;
		StringBuilder stringBuilder = new StringBuilder(120);
		for (Iterator<Entry<Long, String>> iterator = concurrentHashMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<Long, String> entryMap = iterator.next();
			stringBuilder.append(entryMap.getValue());
			stringBuilder.append(",");
			i++;
			iterator.remove();
			if (i > max) {
				break;
			}
		}
		stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
		return stringBuilder.toString();
	}

	public String getThankFollowString() {
		return thankFollowString;
	}

	public void setThankFollowString(String thankFollowString) {
		this.thankFollowString = thankFollowString;
	}

	public Boolean getIsThankFollow() {
		return isThankFollow;
	}

	public void setIsThankFollow(Boolean isThankFollow) {
		this.isThankFollow = isThankFollow;
	}

	public Short getNum() {
		return num;
	}

	public void setNum(Short num) {
		this.num = num;
	}

	public ConcurrentHashMap<Long, String> getFollowsConcurrentHashMap() {
		return followsConcurrentHashMap;
	}

	public void setFollowsConcurrentHashMap(ConcurrentHashMap<Long, String> followsConcurrentHashMap) {
		this.followsConcurrentHashMap = followsConcurrentHashMap;
	}

}
