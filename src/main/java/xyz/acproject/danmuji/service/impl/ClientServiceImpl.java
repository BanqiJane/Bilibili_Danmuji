package xyz.acproject.danmuji.service.impl;

import java.util.Hashtable;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.acproject.danmuji.client.WebSocketProxy;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.BarrageHeadHandle;
import xyz.acproject.danmuji.entity.FristSecurityData;
import xyz.acproject.danmuji.entity.room_data.CheckTx;
import xyz.acproject.danmuji.entity.room_data.Room;
import xyz.acproject.danmuji.entity.room_data.RoomInit;
import xyz.acproject.danmuji.entity.server_data.Conf;
import xyz.acproject.danmuji.file.GuardFileTools;
import xyz.acproject.danmuji.http.HttpRoomData;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;
import xyz.acproject.danmuji.tools.CurrencyTools;
import xyz.acproject.danmuji.tools.HandleWebsocketPackage;
import xyz.acproject.danmuji.utils.ByteUtils;

/**
 * @author BanqiJane
 * @ClassName ClientServiceImpl
 * @Description TODO
 * @date 2020年8月10日 下午12:16:42
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private SetService setService;
    @Autowired
    private ThreadComponent threadComponent;

    public void startConnService(long roomid) throws Exception {
        if (roomid < 1) {
            return;
        }
        RoomInit roomInit = HttpRoomData.httpGetRoomInit(roomid);
        if (roomInit.getShort_id() > 0) {
            PublicDataConf.SHORTROOMID = roomInit.getShort_id();
        }
        PublicDataConf.ROOMID = roomInit.getRoom_id();
        Room room = HttpRoomData.httpGetRoomData(roomid);
        try {
            if (roomInit.getRoom_id() < 1 || roomInit.getRoom_id() == null) {
                return;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }
        Conf conf = HttpRoomData.httpGetConf();
        if (conf == null) {
            return;
        }
        PublicDataConf.AUID = roomInit.getUid();
        PublicDataConf.FANSNUM = HttpRoomData.httpGetFollowersNum();

        PublicDataConf.URL = CurrencyTools.GetWsUrl(conf.getHost_list());
        PublicDataConf.ANCHOR_NAME = room.getUname();
        PublicDataConf.lIVE_STATUS = roomInit.getLive_status();
        if (PublicDataConf.lIVE_STATUS == 1) {
            PublicDataConf.IS_ROOM_POPULARITY = true;
        }
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            HttpUserData.httpGetUserBarrageMsg();
        }
        FristSecurityData fristSecurityData = null;
        PublicDataConf.webSocketProxy = new WebSocketProxy(PublicDataConf.URL, room);
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            fristSecurityData = new FristSecurityData(PublicDataConf.USER.getUid(), PublicDataConf.ROOMID,
                    conf.getToken());
        } else {
            fristSecurityData = new FristSecurityData(PublicDataConf.ROOMID, conf.getToken());
        }
        byte[] byte_1 = HandleWebsocketPackage.BEhandle(BarrageHeadHandle.getBarrageHeadHandle(
                fristSecurityData.toJson().toString().getBytes().length + PublicDataConf.packageHeadLength,
                PublicDataConf.packageHeadLength, PublicDataConf.packageVersion, PublicDataConf.firstPackageType,
                PublicDataConf.packageOther));
        byte[] byte_2 = fristSecurityData.toJson().getBytes();
        byte[] req = ByteUtils.byteMerger(byte_1, byte_2);
        PublicDataConf.webSocketProxy.send(req);
        PublicDataConf.webSocketProxy.send(HexUtils.fromHexString(PublicDataConf.heartByte));
        threadComponent.startHeartByteThread();
        setService.holdSet(PublicDataConf.centerSetConf);
        // 登录发现天选屏蔽礼物
        if (PublicDataConf.centerSetConf != null && PublicDataConf.centerSetConf.getThank_gift().isIs_tx_shield()) {
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                CheckTx checkTx = HttpRoomData.httpGetCheckTX();
                if (checkTx != null) {
                    if (!StringUtils.isEmpty(checkTx.getGift_name())) {
                        if (checkTx.getTime() > 0) {
                            threadComponent.startGiftShieldThread(checkTx.getGift_name(), checkTx.getTime());
                        }
                    }
                }
            }
        }
        // 登录发现天选屏蔽关注
        if (PublicDataConf.centerSetConf != null && PublicDataConf.centerSetConf.getFollow().isIs_tx_shield()) {
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                CheckTx checkTx = HttpRoomData.httpGetCheckTX();
                if (checkTx != null) {
                    if (checkTx.getTime() > 0) {
                        // do something
                        threadComponent.startFollowShieldThread(checkTx.getTime());
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
            if (PublicDataConf.centerSetConf != null
                    && PublicDataConf.centerSetConf.getThank_gift().isIs_guard_local()) {
                if (GuardFileTools.read() != null && GuardFileTools.read().size() > 0) {
                } else {
                    Hashtable<Long, String> guards = HttpRoomData.httpGetGuardList();
                    if (guards != null && guards.size() > 0) {
                        for (Entry<Long, String> entry : guards.entrySet()) {
                            GuardFileTools.write(entry.getKey() + "," + entry.getValue());
                        }
                    }
                    if (guards != null) {
                        guards.clear();
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
            threadComponent.closeHeartByteThread();
            threadComponent.closeUserOnlineThread();
            threadComponent.closeAdvertThread();
            threadComponent.closeSendBarrageThread();
            threadComponent.closeLogThread();
            threadComponent.closeGiftShieldThread();
            threadComponent.closeFollowShieldThread();
            threadComponent.closeAutoReplyThread();
            threadComponent.closeSmallHeartThread();
            threadComponent.closeParseMessageThread();
            RoomInit roomInit = HttpRoomData.httpGetRoomInit(PublicDataConf.ROOMID);
            Room room = HttpRoomData.httpGetRoomData(PublicDataConf.ROOMID);
            try {
                if (roomInit.getRoom_id() < 1 || roomInit.getRoom_id() == null) {
                    return;
                }
            } catch (Exception e) {
                // TODO: handle exception
                return;
            }
            if (roomInit.getShort_id() > 0) {
                PublicDataConf.SHORTROOMID = roomInit.getShort_id();
            }
            PublicDataConf.ROOMID = roomInit.getRoom_id();
            Conf conf = HttpRoomData.httpGetConf();
            if (conf == null) {
                return;
            }
            PublicDataConf.AUID = roomInit.getUid();
            PublicDataConf.FANSNUM = HttpRoomData.httpGetFollowersNum();

            PublicDataConf.URL = CurrencyTools.GetWsUrl(conf.getHost_list());

            PublicDataConf.ANCHOR_NAME = room.getUname();
            PublicDataConf.lIVE_STATUS = roomInit.getLive_status();
            if (PublicDataConf.lIVE_STATUS == 1) {
                PublicDataConf.IS_ROOM_POPULARITY = true;
            }
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                HttpUserData.httpGetUserBarrageMsg();
            }
            FristSecurityData fristSecurityData = null;
            PublicDataConf.webSocketProxy = new WebSocketProxy(PublicDataConf.URL, room);
            if (!StringUtils.isEmpty(PublicDataConf.USERCOOKIE)) {
                fristSecurityData = new FristSecurityData(PublicDataConf.USER.getUid(), PublicDataConf.ROOMID,
                        conf.getToken());
            } else {
                fristSecurityData = new FristSecurityData(PublicDataConf.ROOMID, conf.getToken());
            }
            byte[] byte_1 = HandleWebsocketPackage.BEhandle(BarrageHeadHandle.getBarrageHeadHandle(
                    fristSecurityData.toJson().toString().getBytes().length + PublicDataConf.packageHeadLength,
                    PublicDataConf.packageHeadLength, PublicDataConf.packageVersion, PublicDataConf.firstPackageType,
                    PublicDataConf.packageOther));
            byte[] byte_2 = fristSecurityData.toJson().getBytes();
            byte[] req = ByteUtils.byteMerger(byte_1, byte_2);
            PublicDataConf.webSocketProxy.send(req);
            PublicDataConf.webSocketProxy.send(HexUtils.fromHexString(PublicDataConf.heartByte));
            threadComponent.startHeartByteThread();
            if (PublicDataConf.webSocketProxy.isOpen()) {
                setService.holdSet(PublicDataConf.centerSetConf);
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
                threadComponent.closeHeartByteThread();
                threadComponent.closeUserOnlineThread();
                threadComponent.closeAdvertThread();
                threadComponent.closeSendBarrageThread();
                threadComponent.closeLogThread();
                threadComponent.closeGiftShieldThread();
                threadComponent.closeFollowShieldThread();
                threadComponent.closeAutoReplyThread();
                threadComponent.closeSmallHeartThread();
                threadComponent.closeParseMessageThread();
                PublicDataConf.SHIELDGIFTNAME = null;
                PublicDataConf.replys.clear();
                PublicDataConf.resultStrs.clear();
                PublicDataConf.thankGiftConcurrentHashMap.clear();
                PublicDataConf.barrageString.clear();
                PublicDataConf.interacts.clear();
                PublicDataConf.logString.clear();
                PublicDataConf.ROOMID = null;
                PublicDataConf.ANCHOR_NAME = null;
                PublicDataConf.AUID = null;
                PublicDataConf.FANSNUM = null;
                PublicDataConf.SHORTROOMID = null;
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
