package xyz.acproject.danmuji.thread;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.http.HttpUserData;

/**
 * @author BanqiJane
 * @ClassName SendBarrageThread
 * @Description TODO
 * @date 2020年8月10日 下午12:30:43
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class SendBarrageThread extends Thread {
    private Logger LOGGER = LogManager.getLogger(SendBarrageThread.class);
    public volatile boolean FLAG = false;

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        super.run();
        String barrageStr = null;
        while (!FLAG) {
            if (FLAG) {
                return;
            }
            if (PublicDataConf.webSocketProxy != null && !PublicDataConf.webSocketProxy.isOpen()) {
                return;
            }
            if (null != PublicDataConf.barrageString && !PublicDataConf.barrageString.isEmpty()
                    && StringUtils.isNotBlank(PublicDataConf.barrageString.get(0))) {
                barrageStr = PublicDataConf.barrageString.get(0);
                int strLength = barrageStr.length();
                int maxLength = 20;
                if (PublicDataConf.USERBARRAGEMESSAGE != null) {
                    maxLength = PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getLength();
                }
                //大于就分割发送
                if (strLength > maxLength) {
                    int num = (int) Math.ceil((float) strLength / (float) maxLength);
                    for (int i = 0; i < num; i++) {
                        try {
                            String barrageStr_split = StringUtils.substring(barrageStr, i * maxLength, strLength > maxLength * (i + 1) ? maxLength * (i + 1) : strLength);
                            if (!PublicDataConf.centerSetConf.isTest_mode()) {
                                if (HttpUserData.httpPostSendBarrage(barrageStr_split) != 0) {
                                    break;
                                }
                            } else {
                                LOGGER.info(barrageStr_split);
                            }
                            Thread.sleep(1455);
                        } catch (Exception e) {
                            System.err.println("发送弹幕线程抛出:" + e);
                            // TODO: handle exception
                        }
                    }
                } else {

                    if (!PublicDataConf.centerSetConf.isTest_mode()) {
                        try {
                            // TODO 自动生成的方法存根
                            HttpUserData.httpPostSendBarrage(barrageStr);

                        } catch (Exception e) {
//							LOGGER.error("发送弹幕线程抛出v:" + e);
                            // TODO: handle exception
                        }
//
                    } else {
                        LOGGER.info(barrageStr);
                    }
                }
                PublicDataConf.barrageString.remove(0);
                try {
                    Thread.sleep(1455);
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
//					LOGGER.info("发送弹幕线程关闭:" + e);
                }

            } else {
                synchronized (PublicDataConf.sendBarrageThread) {
                    try {
                        PublicDataConf.sendBarrageThread.wait();
                    } catch (InterruptedException e) {
                        // TODO 自动生成的 catch 块
//						LOGGER.info("发送弹幕线程关闭:" + e);
                    }
                }
            }
        }
    }
}
