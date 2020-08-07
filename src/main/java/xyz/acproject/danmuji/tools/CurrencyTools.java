package xyz.acproject.danmuji.tools;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.BarrageHeadHandle;
import xyz.acproject.danmuji.entity.server_data.HostServer;
import xyz.acproject.danmuji.utils.ByteUtils;

public class CurrencyTools {
	private static Logger LOGGER = LogManager.getLogger(CurrencyTools.class);
	/**
	 * 获取随机破站弹幕服务器地址
	 * 
	 * @param hostServers
	 * @return
	 */
	public static String GetWsUrl(List<HostServer> hostServers) {
		StringBuilder stringBuilder = new StringBuilder();
		String wsUrl = null;
		if (hostServers.size() > 0) {
			HostServer hostServer = hostServers.get((int) Math.random() * hostServers.size());
			stringBuilder.append("wss://");
			stringBuilder.append(hostServer.getHost());
			stringBuilder.append(":");
			stringBuilder.append(hostServer.getWss_port());
			stringBuilder.append("/sub");
			wsUrl = stringBuilder.toString();
			LOGGER.debug("获取破站websocket地址：" + wsUrl);
			stringBuilder.delete(0, stringBuilder.length());
		}
		return wsUrl;
	}
	
	/**
	 * 获取心跳包byte[]
	 * 
	 * @return
	 */
	public static byte[] heartBytes() {
		return ByteUtils.byteMerger(HandleWebsocketPackage.BEhandle(BarrageHeadHandle.getBarrageHeadHandle(
 				"[object Object]".getBytes().length+16,
				PublicDataConf.packageHeadLength, PublicDataConf.packageVersion, PublicDataConf.heartPackageType,
				PublicDataConf.packageOther)),"[object Object]".getBytes());
	}
}
