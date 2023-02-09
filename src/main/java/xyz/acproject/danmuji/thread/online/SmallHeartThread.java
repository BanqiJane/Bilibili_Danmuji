package xyz.acproject.danmuji.thread.online;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.acproject.danmuji.entity.heart.XData;
import xyz.acproject.danmuji.entity.room_data.RoomInfo;
import xyz.acproject.danmuji.http.HttpHeartBeatData;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.util.Date;

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
		//废除小心心线程了
		this.FLAG = true;
		int num=0;
		long startETime = 0;
		long endETime = 0;
		long startXTime =0;
		long endXTime =0;
		RoomInfo roomInfo = null;
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
			startETime = System.currentTimeMillis();
			if(num==0) {
				roomInfo = HttpRoomData.httpGetRoomInfo().getRoomInfo();
				try {
					setxData(HttpHeartBeatData.httpPostE(roomInfo));	
				} catch (Exception e) {
					// TODO: handle exception
					num=0;
				}
			}
			boolean flag = checkTomorrow(System.currentTimeMillis(),getxData());
			endETime = System.currentTimeMillis();
			try {
				long sleepMills = (getxData().getTime()*1000)-(endETime-startETime)-(endXTime-startXTime);
				if(sleepMills>0) {
//				logger.info("small heart sleep:{}",sleepMills);
					Thread.sleep(sleepMills);
				}
				if(flag){
					num=0;
					continue;
				}
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
//				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				logger.error("null错误");
				return;
			}finally {
				startETime=0;
				endETime=0;
			}
			num+=1;
			startXTime = System.currentTimeMillis();
			try {
				//发送x包
				xDataIn = HttpHeartBeatData.httpPostX(roomInfo, num, getxData().startTime(startXTime));
				if(xDataIn==null||xDataIn.getError()){
					num=0;
				}else {
					setxData(xDataIn);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			endXTime = System.currentTimeMillis();
		}
	}
	public XData getxData() {
		return xData;
	}
	public void setxData(XData xData) {
		this.xData = xData;
	}



	private boolean checkTomorrow(long time,XData xData){
		//这里加上时间是明天那就返回错误
		long tomorrow_time = JodaTimeUtils.getZero(JodaTimeUtils.changeDay(new Date(time),1)).getTime();
		long change_time = xData.getStartTime()+(xData.getTime()*1000);
		if(change_time>=tomorrow_time){
			logger.info("small heart change day");
			return true;
		}
		return false;
	}
	
}
