package xyz.acproject.danmuji.tools;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.BarrageHeadHandle;
import xyz.acproject.danmuji.entity.server_data.HostServer;
import xyz.acproject.danmuji.utils.ByteUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

import javax.swing.*;

/**
 * @author BanqiJane
 * @ClassName CurrencyTools
 * @Description TODO
 * @date 2020年8月10日 下午12:31:10
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class CurrencyTools {
    private static Logger LOGGER = LogManager.getLogger(CurrencyTools.class);
    /**
     * 获取随机破站弹幕服务器地址 20201218优化获取
     *
     * @param hostServers
     * @return
     */
    public static String GetWsUrl(List<HostServer> hostServers) {
        StringBuilder stringBuilder = new StringBuilder();
        String wsUrl = null;
        int control = 0;
        if (hostServers.size() > 0) {
             while(!(PublicDataConf.URL).equals(wsUrl)) {
                 if(control>5){
                     break;
                 }
                HostServer hostServer = hostServers.get((int) (Math.random() * hostServers.size()));
                stringBuilder.append("wss://");
                stringBuilder.append(hostServer.getHost());
                stringBuilder.append(":");
                stringBuilder.append(hostServer.getWss_port());
                stringBuilder.append("/sub");
                wsUrl = stringBuilder.toString();
                stringBuilder.delete(0, stringBuilder.length());
                control++;
            }
        }
        LOGGER.debug("获取破站弹幕服务器websocket地址：" + wsUrl);
        return wsUrl;
    }

    /**
     * @param time
     * @return
     */
    public static String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (time - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        if (second < 10) {
            diffTime = diffTime + ":0" + second;
        } else {
            diffTime = diffTime + ":" + second;
        }
        return diffTime;
    }

    /**
     * 获取心跳包byte[]
     *
     * @return
     */
    public static byte[] heartBytes() {
        return ByteUtils.byteMerger(
                HandleWebsocketPackage.BEhandle(BarrageHeadHandle.getBarrageHeadHandle(
                        "[object Object]".getBytes().length + 16, PublicDataConf.packageHeadLength,
                        PublicDataConf.packageVersion, PublicDataConf.heartPackageType, PublicDataConf.packageOther)),
                "[object Object]".getBytes());
    }

    /**
     * 生成uuid 8-4-4-4-12
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return 返回MD5
     */
    public static String deviceHash() {
        String hashString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()+-";
        char[] hashChars = hashString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append(System.currentTimeMillis()).append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)])
                .append(hashChars[(int) (Math.random() * hashChars.length)]);
        return DigestUtils.md5Hex(stringBuilder.toString());
    }

    /**
     * 过滤房间号
     *
     * @return
     */
    public static long parseRoomId() {
        if (PublicDataConf.SHORTROOMID != null && PublicDataConf.SHORTROOMID > 0) {
            return PublicDataConf.SHORTROOMID;
        }
        return PublicDataConf.ROOMID;

    }

    /**
     * 获取天气接口用
     *
     * @return
     */
    public static String getWeatherDay() {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String weekString = "一";
        StringBuilder weatherDay = new StringBuilder();
        switch (week) {
            case 1:
                weekString = "一";
                break;
            case 2:
                weekString = "二";
                break;
            case 3:
                weekString = "三";
                break;
            case 4:
                weekString = "四";
                break;
            case 5:
                weekString = "五";
                break;
            case 6:
                weekString = "六";
                break;
            case 0:
                weekString = "天";
                break;
            default:
                weekString = "一";
                break;
        }
        return weatherDay.append(day).append("日星期").append(weekString).toString();
    }


}
