package xyz.acproject.danmuji.service;

import xyz.acproject.danmuji.conf.Websocket;
import xyz.acproject.danmuji.tools.ByteUtils;

public class HeartByteService implements Runnable{
	Websocket client;
	String heartByte;
	
	public HeartByteService(Websocket client, String heartByte) {
		super();
		this.client = client;
		this.heartByte = heartByte;
	}


	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while (true) {
			try {
				Thread.sleep(30000);
				client.send(ByteUtils.hexToByteArray(heartByte));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
}
