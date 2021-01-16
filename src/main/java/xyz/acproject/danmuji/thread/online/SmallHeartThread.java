package xyz.acproject.danmuji.thread.online;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.acproject.danmuji.entity.heart.XData;
import xyz.acproject.danmuji.entity.room_data.RoomInfo;
import xyz.acproject.danmuji.http.HttpHeartBeatData;
import xyz.acproject.danmuji.http.HttpRoomData;

/**
 * @ClassName SmallHeartThread
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月31日 上午8:31:16
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class SmallHeartThread extends Thread {
	private static Logger logger = LogManager.getLogger(SmallHeartThread.class);
	public volatile boolean FLAG = false;
	private XData xData;
	@Override
	public void run() {
		int num=0;
		RoomInfo roomInfo = HttpRoomData.httpGetRoomInfo();
		XData xDataIn =null;
		// TODO 自动生成的方法存根
		super.run();
		while (!FLAG) {
			if (FLAG) {
				return;
			}
			if(num>=255) {
				return;
			}
			if(num==0) {
				try {
					setxData(HttpHeartBeatData.httpPostE(roomInfo));	
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			try {
				Thread.sleep(getxData().getTime()*1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				logger.error("null错误");
				return;
			}
			num+=1;
			try {
				xDataIn = HttpHeartBeatData.httpPostX(roomInfo, num, getxData());
				if(xDataIn==null||xDataIn.getError()){
					num=0;
				}else {
					setxData(xDataIn);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	public XData getxData() {
		return xData;
	}
	public void setxData(XData xData) {
		this.xData = xData;
	}
	
	
}
