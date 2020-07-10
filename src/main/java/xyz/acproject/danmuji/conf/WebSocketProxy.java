package xyz.acproject.danmuji.conf;

import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.acproject.danmuji.entity.room_data.Room;

public class WebSocketProxy extends Websocket {

	private static Logger LOGGER = LogManager.getLogger(WebSocketProxy.class);

	public WebSocketProxy(String url, Room room) throws URISyntaxException, InterruptedException {
		super(url, room);
		LOGGER.debug("Connectin(连接中)...........................................");
		// TODO 自动生成的构造函数存根
		super.connectBlocking();
		LOGGER.debug("Connecting Success(连接成功)");
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO 自动生成的方法存根
		super.onClose(code, reason, remote);
	
	}
}
