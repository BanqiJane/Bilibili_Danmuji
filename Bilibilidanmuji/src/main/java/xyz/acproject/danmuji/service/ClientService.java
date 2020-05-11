package xyz.acproject.danmuji.service;

import org.springframework.stereotype.Service;

import xyz.acproject.danmuji.common.CommonTools;
import xyz.acproject.danmuji.conf.WebSocketProxy;
import xyz.acproject.danmuji.entity.Conf;
import xyz.acproject.danmuji.entity.FristSecurityData;
import xyz.acproject.danmuji.http.HttpGetData;
import xyz.acproject.danmuji.tools.ByteUtils;
@Service
public class ClientService {
	private String heartByte="00000010001000010000000200000001";
	private String firstByte="000000%replace%001000010000000700000001";
	public void startService() throws Exception {
		long roomid =139;
		Conf conf = HttpGetData.httpGetConf(roomid);
		WebSocketProxy webSocketProxy = new WebSocketProxy(CommonTools.GetWsUrl(conf.getHost_server_list()));
		FristSecurityData fristSecurityData = new FristSecurityData(Long.valueOf(HttpGetData.httpGetRoomData(roomid).getRoomid()),conf.getToken());
		firstByte = firstByte.replace("%replace%", Integer.toHexString(fristSecurityData.toJson().getBytes().length+16));
		byte[] byte_1 = ByteUtils.hexToByteArray(firstByte);
		byte[] byte_2 = fristSecurityData.toJson().getBytes("utf-8");
		byte[] req = ByteUtils.byteMerger(byte_1, byte_2);
		webSocketProxy.send(req);
		Thread heartThread = new Thread(new HeartByteService(webSocketProxy, heartByte));
		heartThread.start();
	}
}
