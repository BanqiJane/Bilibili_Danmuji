package xyz.acproject.danmuji.thread.core;


import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.utils.HexUtils;

/**
 * @ClassName HeartByteThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:44
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HeartByteThread extends Thread {
//	private Logger LOGGER = LogManager.getLogger(HeartByteThread.class);
//	Websocket client;
//	String heartByte;
	public volatile boolean HFLAG = false;

//	public HeartByteThread(Websocket client, String heartByte) {
//		super();
//		this.client = client;
//		this.heartByte = heartByte;
//	}

	@Override
	public void run() {
		super.run();
		// TODO 自动生成的方法存根
		while (!HFLAG) {
			if (HFLAG) {
				return;
			}
			if(PublicDataConf.webSocketProxy.isOpen()) {
				try {
					Thread.sleep(30000);
					PublicDataConf.webSocketProxy.send(HexUtils.fromHexString(PublicDataConf.heartByte));
				} catch (Exception e) {
					// TODO: handle exception
//					LOGGER.info("心跳线程关闭:"+e);
//					e.printStackTrace();
				}
			}
			
		}
	}

}
