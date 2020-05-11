package xyz.acproject.danmuji.tools;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintUtils {
	private static Logger LOGGER = LogManager.getLogger(PrintUtils.class);

	public static void ByteBufferToString(ByteBuffer message) {
		String resultStr = null;
		byte[] bytes = ByteUtils.decodeValue(message);
		String hexString = ByteUtils.bytesToHexString(bytes);
		// 数据包协议版本 0未压缩的json格式数据 1客户端心跳通常为人气值 4字节整数 2为带zlib压缩过的json格式数据
		Integer dataVersion = Integer.valueOf(hexString.substring(15, 16));
		// 2心跳包 每30秒发送一次 3心跳回应 人气值 4字节整数 5通知 弹幕礼物公告等内容 7加入房间发送的第一个数据包 8认证成功后返回的第一个数据包
		Integer dataType = Integer.valueOf(hexString.substring(23, 24));
		if (dataVersion == 2) {
			if (dataType == 5) {
				resultStr = ByteUtils.hexStringTozlibInflate(stringToArrayStrByte(hexString));
				resultStr = ByteUtils.unicodeToString(resultStr);
			}
		} else if (dataVersion == 1) {
			if (dataType == 3) {
				resultStr = String.valueOf("当前房间人气值为:" + Long.parseLong(stringToArrayStrByte(hexString), 16));
			}
		} else if (dataVersion == 0) {
			resultStr = ByteUtils.hexStringToString(stringToArrayStrByte(hexString));
			resultStr = ByteUtils.unicodeToString(resultStr);
		}
		if (resultStr == null) {
			return;
		}
		String[] split = resultStr.split("%split%");
		for (String string : split) {
			LOGGER.debug(string, "utf-8");
		}
	}

	/**
	 * 
	 * 切割hexString字符串
	 * 
	 * @param hexString
	 * @return
	 */
	public static String stringToArrayStrByte(String hexString) {
		StringBuffer sb = new StringBuffer();
		int endIndex = Integer.parseInt(hexString.substring(0, 8), 16) * 2;
		if (hexString.length() == endIndex) {
			return hexString.substring(32, endIndex);
		}
		sb.append(hexString.substring(32, endIndex) + "706c697425"
				+ stringToArrayStrByte(hexString.substring(endIndex, hexString.length())));
		return sb.toString();
	}
}
