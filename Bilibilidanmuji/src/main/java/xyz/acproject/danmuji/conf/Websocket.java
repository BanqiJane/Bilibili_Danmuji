package xyz.acproject.danmuji.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import xyz.acproject.danmuji.tools.PrintUtils;

public class Websocket extends WebSocketClient {

	public Websocket(String url) throws URISyntaxException {
		super(new URI(url));
		// TODO 自动生成的构造函数存根
		System.out.println(url);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		// TODO 自动生成的方法存根
		System.out.println("open");
	}

	@Override
	public void onMessage(ByteBuffer message) {
		// TODO 自动生成的方法存根
		PrintUtils.ByteBufferToString(message);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		// TODO 自动生成的方法存根
		 System.out.println("closed");
	}

	@Override
	public void onError(Exception ex) {
		// TODO 自动生成的方法存根
		ex.printStackTrace();
	}

	@Override
	public void onMessage(String message) {
		// TODO 自动生成的方法存根
		
	}

}
