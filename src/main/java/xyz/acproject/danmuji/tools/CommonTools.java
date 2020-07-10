package xyz.acproject.danmuji.tools;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.HexUtils;

import struct.JavaStruct;
import struct.StructException;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.BarrageHeadHandle;
import xyz.acproject.danmuji.entity.server_data.HostServer;
import xyz.acproject.danmuji.utils.ByteUtils;

public class CommonTools {
	private static Logger LOGGER = LogManager.getLogger(CommonTools.class);
	/**
	 * 获取随机破站弹幕服务器地址
	 * 
	 * @param hostServers
	 * @return
	 */
	public static String GetWsUrl(List<HostServer> hostServers) {
		StringBuilder stringBuilder = new StringBuilder();
		String wsUrl= null;
		if(hostServers.size()>0) {
			HostServer hostServer = hostServers.get((int)Math.random()*hostServers.size());
			stringBuilder.append("wss://");
			stringBuilder.append(hostServer.getHost());
			stringBuilder.append(":");
			stringBuilder.append(hostServer.getWss_port());
			stringBuilder.append("/sub");
			wsUrl = stringBuilder.toString();
			LOGGER.debug("获取破站websocket地址："+wsUrl);
			stringBuilder.delete(0, stringBuilder.length());
		}
		return wsUrl;
	}
	/**
	 * 处理父数据包 单线程引用写法
	 * 
	 * @param message
	 */
	@SuppressWarnings("unused")
	public static void handle_Message(ByteBuffer message) {
//		StringBuilder stringBuilder = new StringBuilder();
		byte[] bytes = ByteUtils.decodeValue(message);
		String resultStr = null;
		BarrageHeadHandle barrageHeadHandle = null;
		if (bytes.length>0) {
			barrageHeadHandle = BEhandle(bytes);
			int data_len = barrageHeadHandle.getPackageLength();// 数据包长度 为int
			int head_len =barrageHeadHandle.getPackageHeadLength();// 数据包头部长度 为char 为16
			// 数据包协议版本 0未压缩的json格式数据 1客户端心跳通常为人气值 4字节整数 2为带zlib压缩过的json格式数据
			int data_ver = barrageHeadHandle.getPackageVersion();// 数据包协议版本 为char 有0，1，2
			int data_type = barrageHeadHandle.getPackageType();// 数据包协议类型 int 目前已知有2，3，5，7，8
			int data_other = barrageHeadHandle.getPackageOther();// 暂时未知 int 目前已知有0，1
//			LOGGER.debug("数据包:" + "(" + data_len + "," + head_len + "," + data_ver + "," + data_type + ","
//					+ data_other + ")");
			resultStr =HexUtils.toHexString(ByteUtils.subBytes(bytes, head_len, data_len - head_len));
			if (data_ver == 2) {
				if (data_type == 5) {
					CommonTools.handle_zlibMessage(ByteUtils.hexStringTozlibInflateByteArray(resultStr));
				}else {
					LOGGER.debug("！！！！！！！！！！未知数据(1)v:"+data_ver+"t:"+data_type+":"+resultStr);
				}
			} else if (data_ver == 1) {
				if (data_type == 3) {
					PublicDataConf.ROOM_POPULARITY=Long.parseLong(resultStr, 16);
//					if(PublicDataConf.IS_ROOM_POPULARITY) {
//					stringBuilder.append(JodaTimeUtils.format(System.currentTimeMillis()));
//					stringBuilder.append(":房间人气:");
//					stringBuilder.append(PublicDataConf.ROOM_POPULARITY);
//					System.out.println(stringBuilder.toString());
//					stringBuilder.delete(0, stringBuilder.length());
//					}
//					resultStr = String.valueOf("当前房间人气值为:" + Long.parseLong(resultStr, 16));
//					LOGGER.debug(resultStr);
				}else if(data_type==8){
					//返回{code 0} 验证头消息成功后返回
					resultStr = ByteUtils.hexStringToString(resultStr);
//					LOGGER.debug("服务器验证信息返回:"+resultStr);
				}else {
					LOGGER.debug("！！！！！！！！！！未知数据(1)v:"+data_ver+"t:"+data_type+":"+resultStr);
				}
			} else if (data_ver == 0) {
				resultStr = ByteUtils.hexStringToString(resultStr);
//				resultStr = ByteUtils.unicodeToString(resultStr);
				PublicDataConf.resultStrs.add(resultStr);
			} else {
				LOGGER.debug("！！！！！！！！！！未知数据(1):"+resultStr);
			}
			if (resultStr == null) {
				LOGGER.debug("空数据(1):"+resultStr);
			}
		}
		bytes=null;
		resultStr=null;
	}
	
	/**
	 * 处理解压后子数据包
	 * 
	 * @param bytes
	 */
	@SuppressWarnings("unused")
	public static void handle_zlibMessage(byte[] bytes) {
		int offect = 0;
		String resultStr = null;
		int maxLen = bytes.length;
		byte[] byte_c =null;
		BarrageHeadHandle barrageHeadHandle = null;
		int data_len=0;
		int head_len=0;
		int data_ver=0;
		int data_type=0;
		int data_other=0;
//		int index = 1;
//		LOGGER.debug(maxLen+"长的母数据包打印开始--------------------------------------------------------------------------------");
		while (offect < maxLen) {
			byte_c=ByteUtils.subBytes(bytes, offect, maxLen-offect);
			barrageHeadHandle = BEhandle(byte_c);
			data_len = barrageHeadHandle.getPackageLength();// 数据包长度 为int
			head_len =barrageHeadHandle.getPackageHeadLength();// 数据包头部长度 为char 为16
			// 数据包协议版本 0未压缩的json格式数据 1客户端心跳通常为人气值 4字节整数 2为带zlib压缩过的json格式数据
			data_ver = barrageHeadHandle.getPackageVersion();// 数据包协议版本 为char 有0，1，2
			data_type = barrageHeadHandle.getPackageType();// 数据包协议类型 int 目前已知有2，3，5，7，8
			data_other = barrageHeadHandle.getPackageOther();// 暂时未知 int 目前已知有0，1
//			LOGGER.debug("子包<"+index+">:"+"("+data_len+","+head_len+","+data_ver+","+data_type+","+data_other+")");
			resultStr=HexUtils.toHexString(ByteUtils.subBytes(byte_c, head_len, data_len-head_len));
			if (data_ver == 2) {
				if (data_type == 5) {
					resultStr = ByteUtils.hexStringTozlibInflateString(resultStr);
//					resultStr = ByteUtils.unicodeToString(resultStr);
					LOGGER.debug("其他未处理消息(2):"+resultStr);
				}else {
					LOGGER.debug("！！！！！！！！！！未知数据(2)v:"+data_ver+"t:"+data_type+":"+resultStr);
				}
			} else if (data_ver == 1) {
				if (data_type == 3) {
					resultStr = String.valueOf("当前房间人气值为:" + Long.parseLong(resultStr, 16));
					LOGGER.debug(resultStr);
				}else {
					LOGGER.debug("！！！！！！！！！！未知数据(2)v:"+data_ver+"t:"+data_type+":"+resultStr);
				}
			} else if (data_ver == 0) {
				resultStr = ByteUtils.hexStringToString(resultStr);
//				resultStr = ByteUtils.unicodeToString(resultStr);
				PublicDataConf.resultStrs.add(resultStr);
			}else {
				LOGGER.debug("！！！！！！！！！！未知数据(2):"+resultStr);
			}
			if (resultStr == null) {
				LOGGER.debug("空数据(2):"+resultStr);
			}
//			index+=1;
			offect +=data_len;
		}
//		LOGGER.debug(maxLen+"长的母数据包打印结束--------------------------------------------------------------------------------");
		resultStr=null;
	}
	/**
	 * 处理弹幕数据
	 * 
	 * @param bytes 数据集
	 * @return
	 */
	public static BarrageHeadHandle BEhandle(byte[] bytes) {
		BarrageHeadHandle barrageHeadHandle = BarrageHeadHandle.getBarrageHeadHandle();
		try {
			JavaStruct.unpack(barrageHeadHandle, bytes);
		} catch (StructException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return barrageHeadHandle;
	}
	
}
