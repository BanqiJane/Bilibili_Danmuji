package xyz.acproject.danmuji.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.thread.core.ReConnThread;
import xyz.acproject.danmuji.tools.HandleWebsocketPackage;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * @ClassName Websocket
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:20:25
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Websocket extends WebSocketClient {
	private static Logger LOGGER = LogManager.getLogger(Websocket.class);

	public Websocket(String url, Room room) throws URISyntaxException {
		super(new URI(url));
		LOGGER.info("已尝试连接至服务器地址:" + url + ";真实房间号为:" + room.getRoomid() + ";主播名字为:" + room.getUname());
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		// TODO 自动生成的方法存根
		LOGGER.info("websocket connect open(连接窗口打开)");
	}

	@Override
	public void onMessage(ByteBuffer message) {
		// TODO 自动生成的方法存根
		if(PublicDataConf.parseMessageThread!=null&&!PublicDataConf.parseMessageThread.FLAG) {
		try {
			HandleWebsocketPackage.handle_Message(message);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			LOGGER.info("解析错误日志生成，请将log底下文件发给管理员,或github开issue发送错误"+e);
		}
//			synchronized (PublicDataConf.parseMessageThread) {
//				PublicDataConf.parseMessageThread.notify();
//			}
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO 自动生成的方法存根
		LOGGER.info("websocket connect close(连接已经断开)，纠错码:" + code);
		PublicDataConf.heartByteThread.HFLAG = true;
		PublicDataConf.parseMessageThread.FLAG = true;
		if (code != 1000) {
			LOGGER.info("websocket connect close(连接意外断开，正在尝试重连)，错误码:" + code);
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
		LOGGER.error("[错误信息，请将log文件下的日志发送给管理员]websocket connect error,message:" + ex.getMessage());
		LOGGER.info("尝试重新链接");
		synchronized (PublicDataConf.webSocketProxy) {
			PublicDataConf.webSocketProxy.close(1006);
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
	public void onMessage(String message) {
		// TODO 自动生成的方法存根

	}

}
