package xyz.acproject.danmuji.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.thread.core.ReConnThread;
import xyz.acproject.danmuji.tools.CommonTools;

public class Websocket extends WebSocketClient {
	private static Logger LOGGER = LogManager.getLogger(Websocket.class);

	public Websocket(String url, Room room) throws URISyntaxException {
		super(new URI(url));
		LOGGER.debug("已尝试连接至服务器地址:" + url + ";真实房间号为:" + room.getRoomid() + ";主播名字为:" + room.getUname());
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		// TODO 自动生成的方法存根
		LOGGER.debug("websocket connect open(连接窗口打开)");
	}

	@Override
	public void onMessage(ByteBuffer message) {
		// TODO 自动生成的方法存根
		if(PublicDataConf.parseMessageThread!=null&&!PublicDataConf.parseMessageThread.FLAG) {
		CommonTools.handle_Message(message);
		synchronized (PublicDataConf.parseMessageThread) {
			PublicDataConf.parseMessageThread.notify();
		}
	}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO 自动生成的方法存根
		LOGGER.debug("websocket connect close(连接已经断开)，纠错码:" + code);
		PublicDataConf.heartByteThread.HFLAG = true;
		PublicDataConf.parseMessageThread.FLAG = true;
		if (code != 1000) {
			LOGGER.debug("websocket connect close(连接意外断开，正在尝试重连)，错误码:" + code);
			if (!PublicDataConf.webSocketProxy.isOpen()) {
				if (PublicDataConf.reConnThread != null) {
					if (PublicDataConf.reConnThread.getState().toString().equals("TERMINATED")) {
						PublicDataConf.reConnThread = new ReConnThread();
						PublicDataConf.reConnThread.start();
					} else {

					}
				} else {
					PublicDataConf.reConnThread = new ReConnThread();
					PublicDataConf.reConnThread.start();
				}
			} else {
				PublicDataConf.reConnThread.RFLAG = true;
			}
		}
	}

	@Override
	public void onError(Exception ex) {
		// TODO 自动生成的方法存根
		LOGGER.error("[错误信息，开启debug模式抓取一阵子并将日志发给管理员]websocket connect error,message:" + ex.getMessage());
	}

	@Override
	public void onMessage(String message) {
		// TODO 自动生成的方法存根

	}

}
