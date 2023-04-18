package xyz.acproject.danmuji.thread;

import java.util.Vector;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.danmu_data.Interact;

/**
 * @ClassName ParseThankFollowThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:30:34
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Getter
@Setter
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
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
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
							
							thankFollowStr =StringUtils.replace(handleThankStr(getThankFollowString()), "%uNames%", stringBuilder.toString());
							stringBuilder.delete(0, stringBuilder.length());
							if (PublicDataConf.sendBarrageThread != null
									&& !PublicDataConf.sendBarrageThread.FLAG) {
								PublicDataConf.barrageString.add(thankFollowStr);
								synchronized (PublicDataConf.sendBarrageThread) {
									PublicDataConf.sendBarrageThread.notify();
								}
							}
							thankFollowStr = null;
						}
					}
					interacts.clear();
					PublicDataConf.interacts.clear();
					break;
				}
			}
		}

	}

	public String handleThankStr(String thankStr) {
		String thankFollowStrs[] = null;
		if (StringUtils.indexOf(thankStr, "\n") != -1) {
			thankFollowStrs = StringUtils.split(thankStr, "\n");
		}
		if(thankFollowStrs!=null&&thankFollowStrs.length>1) {
			return thankFollowStrs[(int) Math.ceil(Math.random() * thankFollowStrs.length)-1];
		}
		return thankStr;
	}



}
