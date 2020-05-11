package xyz.acproject.danmuji.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.acproject.danmuji.entity.HostServer;

public class CommonTools {
	private static Logger LOGGER = LogManager.getLogger(CommonTools.class);
	public static String GetWsUrl(List<HostServer> hostServers) {
		final StringBuilder stringBuilder = new StringBuilder();
		String wsUrl= null;
		if(hostServers.size()>0) {
			HostServer hostServer = hostServers.get((int)Math.random()*hostServers.size());
			stringBuilder.append("wss://");
			stringBuilder.append(hostServer.getHost());
//			stringBuilder.append(":");
//			stringBuilder.append(hostServer.getWss_port());
			stringBuilder.append("/sub");
			wsUrl = stringBuilder.toString();
			LOGGER.debug("获取破站websocket地址："+wsUrl);
		}
		return wsUrl;
	}
}
