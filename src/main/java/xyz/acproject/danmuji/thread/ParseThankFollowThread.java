package xyz.acproject.danmuji.thread;

import java.util.Vector;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.danmu_data.Interact;

public class ParseThankFollowThread extends Thread {
//	private Logger LOGGER = LogManager.getLogger(ParseThankFollowThread.class);
	public volatile boolean FLAG = false;
	private String thankFollowString = "感谢 %uNames% 的关注";
	private Short num = 1;
	private Long delaytime = 3000L;
	private Long timestamp;
	@Override
	public void run() {
		String thankFollowStr = null;
		StringBuilder stringBuilder = new StringBuilder(300);
		Vector<Interact> interacts = new Vector<Interact>();
		synchronized (timestamp) {
			while (!FLAG) {
				if (FLAG) {
					return;
				}
				if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
					return;
				}
				long nowTime = System.currentTimeMillis();
				if (nowTime - getTimestamp() < getDelaytime()) {
				} else {
					//do something
					if(PublicDataConf.interacts.size()>0) {
						interacts.addAll(PublicDataConf.interacts);
						for (int i = 0; i < interacts.size(); i += getNum()) {
							for (int j = i; j < i + getNum(); j++) {
								if (j >= interacts.size()) {
									break;
								}
								stringBuilder.append(interacts.get(j).getUname()).append(",");
							}
							stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
							thankFollowStr = getThankFollowString().replaceAll("%uNames%", stringBuilder.toString());
							stringBuilder.delete(0, stringBuilder.length());
							if (PublicDataConf.sendBarrageThread != null
									&& !PublicDataConf.sendBarrageThread.FLAG) {
								PublicDataConf.barrageString.add(thankFollowStr);
								synchronized (PublicDataConf.sendBarrageThread) {
									PublicDataConf.sendBarrageThread.notify();
								}
							}
							thankFollowStr = getThankFollowString();
						}
					}
					interacts.clear();
					PublicDataConf.interacts.clear();
					break;
				}
			}
		}

	}

	

	public String getThankFollowString() {
		return thankFollowString;
	}

	public void setThankFollowString(String thankFollowString) {
		this.thankFollowString = thankFollowString;
	}

	public Short getNum() {
		return num;
	}

	public void setNum(Short num) {
		this.num = num;
	}

	public Long getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(Long delaytime) {
		this.delaytime = delaytime;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}


}
