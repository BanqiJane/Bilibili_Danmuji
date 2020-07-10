package xyz.acproject.danmuji.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.SetMethodCode;
import xyz.acproject.danmuji.conf.ThreadConf;
import xyz.acproject.danmuji.conf.WebSocketProxy;
import xyz.acproject.danmuji.entity.FristSecurityData;
import xyz.acproject.danmuji.entity.room_data.CheckTx;
import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.entity.room_data.RoomInit;
import xyz.acproject.danmuji.entity.server_data.Conf;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.tools.CommonTools;
import xyz.acproject.danmuji.utils.ByteUtils;

@Service
public class ClientService {
	public void startService(long roomid) throws Exception {
		if (roomid < 1) {
			return;
		}
		RoomInit roomInit = HttpRoomData.httpGetRoomInit(roomid);
		Room room = HttpRoomData.httpGetRoomData(roomid);
		try {
			if (roomInit.getRoom_id() < 1 || roomInit.getRoom_id() == null) {
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		PublicDataConf.ROOMID = roomInit.getRoom_id();
		Conf conf = HttpRoomData.httpGetConf();
		PublicDataConf.AUID = roomInit.getUid();
		PublicDataConf.FANSNUM = HttpRoomData.httpGetFollowersNum();

		PublicDataConf.URL = CommonTools.GetWsUrl(conf.getHost_list());
		PublicDataConf.ANCHOR_NAME = room.getUname();
		PublicDataConf.lIVE_STATUS = roomInit.getLive_status();
		if (PublicDataConf.lIVE_STATUS == 1) {
			PublicDataConf.IS_ROOM_POPULARITY = true;
		}
		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
			HttpUserData.httpGetUserBarrageMsg();
		}
		PublicDataConf.webSocketProxy = new WebSocketProxy(PublicDataConf.URL, room);
		FristSecurityData fristSecurityData = new FristSecurityData(PublicDataConf.ROOMID, conf.getToken());
//		if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE) && PublicDataConf.USER != null) {
//			fristSecurityData.setUid(PublicDataConf.USER.getUid());
//		}
//		String firstByte = PublicDataConf.firstByte.replace("%replace%",
//				Integer.toHexString(fristSecurityData.toJson().toString().getBytes().length + 16).length() > 2
//						? Integer.toHexString(fristSecurityData.toJson().toString().getBytes().length + 16)
//						: '0' + Integer.toHexString(fristSecurityData.toJson().toString().getBytes().length + 16));
		String firstByte = PublicDataConf.firstByte.replace("%replace%",
				'0' + Integer.toHexString(fristSecurityData.toJson().toString().getBytes().length + 16));
		byte[] byte_1 = HexUtils.fromHexString(firstByte);
		byte[] byte_2 = fristSecurityData.toJson().getBytes();
		byte[] req = ByteUtils.byteMerger(byte_1, byte_2);
		PublicDataConf.webSocketProxy.send(req);
		PublicDataConf.webSocketProxy.send(HexUtils.fromHexString(PublicDataConf.heartByte));
		ThreadConf.startHeartByteThread();
		SetMethodCode.start(PublicDataConf.centerSetConf);
		if (PublicDataConf.centerSetConf!=null&&PublicDataConf.centerSetConf.getThank_gift().isIs_tx_shield()) {
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				CheckTx checkTx = HttpRoomData.httpGetCheckTX();
				if (checkTx != null) {
					if (!StringUtils.isEmpty(checkTx.getGift_name())) {
						if (checkTx.getTime() > 0) {
							ThreadConf.startGiftShieldThread(checkTx.getGift_name(), checkTx.getTime());
						}
					}
				}
			}
		}
		room = null;
		roomInit = null;
		conf = null;
	}

	public void reConnService() throws Exception {
		if (!PublicDataConf.webSocketProxy.isOpen()) {
			ThreadConf.closeHeartByteThread();
			ThreadConf.closeParseMessageThread();
			ThreadConf.closeUserOnlineThread();
			ThreadConf.closeFollowThread();
			ThreadConf.closeAdvertThread();
			ThreadConf.closeSendBarrageThread();
			ThreadConf.closeLogThread();
			ThreadConf.closeGiftShieldThread();
			Room room = HttpRoomData.httpGetRoomData(PublicDataConf.ROOMID);
			Conf conf = HttpRoomData.httpGetConf();
			PublicDataConf.FANSNUM = HttpRoomData.httpGetFollowersNum();
			PublicDataConf.URL = CommonTools.GetWsUrl(conf.getHost_list());
			if (PublicDataConf.lIVE_STATUS == 1) {
				PublicDataConf.IS_ROOM_POPULARITY = true;
			}
			if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
				HttpUserData.httpGetUserBarrageMsg();
			}
			PublicDataConf.webSocketProxy = new WebSocketProxy(PublicDataConf.URL, room);
			FristSecurityData fristSecurityData = new FristSecurityData(PublicDataConf.ROOMID, conf.getToken());
			String firstByte = PublicDataConf.firstByte.replace("%replace%",
					'0' + Integer.toHexString(fristSecurityData.toJson().toString().getBytes().length + 16));
			byte[] byte_1 = HexUtils.fromHexString(firstByte);
			byte[] byte_2 = fristSecurityData.toJson().getBytes("utf-8");
			byte[] req = ByteUtils.byteMerger(byte_1, byte_2);
			PublicDataConf.webSocketProxy.send(req);
			PublicDataConf.webSocketProxy.send(HexUtils.fromHexString(PublicDataConf.heartByte));
			ThreadConf.startHeartByteThread();
			if (PublicDataConf.webSocketProxy.isOpen()) {
				SetMethodCode.start(PublicDataConf.centerSetConf);
			}
		}
	}

	public boolean closeConnService() {
		boolean flag = false;
		if (PublicDataConf.webSocketProxy != null) {
			if (PublicDataConf.webSocketProxy.isOpen()) {
				synchronized (PublicDataConf.webSocketProxy) {
					PublicDataConf.webSocketProxy.close();
					try {
						PublicDataConf.webSocketProxy.closeBlocking();
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					PublicDataConf.webSocketProxy.closeConnection(1000, "手动关闭");
					PublicDataConf.webSocketProxy = null;
				}
				ThreadConf.closeHeartByteThread();
				ThreadConf.closeParseMessageThread();
				ThreadConf.closeUserOnlineThread();
				ThreadConf.closeFollowThread();
				ThreadConf.closeAdvertThread();
				ThreadConf.closeSendBarrageThread();
				ThreadConf.closeLogThread();
				ThreadConf.closeGiftShieldThread();
				PublicDataConf.SHIELDGIFTNAME = null;
				PublicDataConf.resultStrs.clear();
				PublicDataConf.thankGiftConcurrentHashMap.clear();
				PublicDataConf.barrageString.clear();
				PublicDataConf.logString.clear();
				PublicDataConf.ROOMID = null;
				PublicDataConf.ANCHOR_NAME = null;
				PublicDataConf.AUID = null;
				PublicDataConf.FANSNUM = null;
				PublicDataConf.lIVE_STATUS = 0;
				PublicDataConf.ROOM_POPULARITY = 1L;
				if (null == PublicDataConf.webSocketProxy || !PublicDataConf.webSocketProxy.isOpen()) {
					flag = true;
				}
			} else {
				flag = true;
			}
		}
		return flag;
	}
}
