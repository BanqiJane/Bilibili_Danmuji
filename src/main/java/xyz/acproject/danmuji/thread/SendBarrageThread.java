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
                    && !StringUtils.isEmpty(PublicDataConf.barrageString.get(0))) {
                barrageStr = PublicDataConf.barrageString.get(0);
                int strLength = barrageStr.length();
                int maxLength = 20;
                if (PublicDataConf.USERBARRAGEMESSAGE != null) {
                    maxLength = PublicDataConf.USERBARRAGEMESSAGE.getDanmu().getLength();
                }
                if (strLength > maxLength) {
                    int num = (int) Math.ceil((float) strLength / (float) maxLength);
                    for (int i = 0; i <= num; i++) {
                        if (!PublicDataConf.TEST_MODE) {
                            try {
                                if (HttpUserData.httpPostSendBarrage(StringUtils.substring(barrageStr, i * maxLength, strLength > maxLength * (i + 1) ? maxLength * (i + 1) : strLength)) != 0) {
                                    break;
                                }
                            } catch (Exception e) {
//								LOGGER.error("发送弹幕线程抛出:" + e);
                                // TODO: handle exception
                            }
                        } else {
                            LOGGER.info(StringUtils.substring(barrageStr, i * maxLength, strLength > maxLength * (i + 1) ? maxLength * (i + 1) : strLength));
                        }
                        try {
                            Thread.sleep(1455);
                        } catch (InterruptedException e) {
                            // TODO 自动生成的 catch 块
//							LOGGER.info("发送弹幕线程关闭:" + e);
                        }
//						if (i > 1 && i < num) {
//							try {
//								Thread.sleep(1300);
//							} catch (InterruptedException e) {
//								// TODO 自动生成的 catch 块
//								LOGGER.info("发送弹幕线程关闭:" + e);
//							}
//						}
                    }
                } else {

                    if (!PublicDataConf.TEST_MODE) {
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
