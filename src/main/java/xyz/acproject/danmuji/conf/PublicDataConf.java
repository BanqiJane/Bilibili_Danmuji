package xyz.acproject.danmuji.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import xyz.acproject.danmuji.client.WebSocketProxy;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.entity.danmu_data.Interact;
import xyz.acproject.danmuji.entity.room_data.MedalInfoAnchor;
import xyz.acproject.danmuji.entity.user_data.AutoSendGift;
import xyz.acproject.danmuji.entity.user_data.User;
import xyz.acproject.danmuji.entity.user_data.UserCookie;
import xyz.acproject.danmuji.entity.user_data.UserManager;
import xyz.acproject.danmuji.entity.user_in_room_barrageMsg.UserBarrageMsg;
import xyz.acproject.danmuji.thread.*;
import xyz.acproject.danmuji.thread.core.HeartByteThread;
import xyz.acproject.danmuji.thread.core.ParseMessageThread;
import xyz.acproject.danmuji.thread.core.ReConnThread;
import xyz.acproject.danmuji.thread.online.HeartBeatThread;
import xyz.acproject.danmuji.thread.online.HeartBeatsThread;
import xyz.acproject.danmuji.thread.online.SmallHeartThread;
import xyz.acproject.danmuji.thread.online.UserOnlineHeartThread;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName PublicDataConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:34
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Configuration
public class PublicDataConf {
	//url 直播弹幕websocket地址
	public static String URL = "wss://broadcastlv.chat.bilibili.com:2245/sub";
	//房间号
	public static Long ROOMID = null;
	//短号
	public static Integer SHORTROOMID = null;
	//主播uid
	public static Long AUID = null;
	//主播粉丝数
	public static Long FANSNUM =null;
	//主播名称
	public static String ANCHOR_NAME = null;
	//主播勋章信息
	public static MedalInfoAnchor MEDALINFOANCHOR = null;
	//房间人气
	public static Long ROOM_POPULARITY =1L;
	//房间观看人数（历史）
	public static Long ROOM_WATCHER = 0L;
	//点赞数量
	public static Long ROOM_LIKE = 0L;
	//直播状态 0不直播 1直播 2轮播
	public static Short lIVE_STATUS = 0;
	//cookie String串
	public static String USERCOOKIE = null;
	//user信息
	public static User USER = null;
	//cookie
	public static UserCookie COOKIE = null;
	//user弹幕长度
	public static UserBarrageMsg USERBARRAGEMESSAGE = null;
	//user房间管理信息
	public static UserManager USERMANAGER = null;
	//天选礼物屏蔽
	public static String SHIELDGIFTNAME = null;
	//天选是否正在屏蔽关注
	public static Boolean ISSHIELDFOLLOW = false;
	//天选是否正在屏蔽欢迎
	public static Boolean ISSHIELDWELCOME = false;
	//设置
	public static CenterSetConf centerSetConf;

	
	
	//心跳包 16进制
	public final static String heartByte="0000001f0010000100000002000000015b6f626a656374204f626a6563745d";
	//包头长
	public final static char packageHeadLength = 16;
	//验证包协议类型
	public final static int firstPackageType = 7;
	//心跳包协议类型
	public final static int heartPackageType = 2;
	//心跳包&验证包协议版本
	public final static char packageVersion = 1;
    //心跳包&验证包的尾巴其他
	public final static int packageOther = 1;
	
	//websocket客户端主线程
	public static WebSocketProxy webSocketProxy;
	//心跳线程
	public static HeartByteThread heartByteThread;
	//重新连接线程
	public static ReConnThread reConnThread;
	//处理信息分类线程
	public static ParseMessageThread parseMessageThread;
	//处理弹幕包集合
	public final static Vector<String> resultStrs = new Vector<String>(100);
	//礼物感谢集
	public final static Map<String,Vector<Gift>> thankGiftConcurrentHashMap = new ConcurrentHashMap<String,Vector<Gift>>(3000);
	//待发弹幕集
	public final static Vector<String> barrageString = new Vector<String>();
	//log日志待写入集合
	public final static Vector<String> logString = new Vector<String>(100);
	//待发送感谢关注集合
	public final static Vector<Interact> interacts = new Vector<Interact>(200);
	//待发送欢迎进入直播间集合
	public final static Vector<Interact> interactWelcome = new Vector<Interact>(400);
	//自动回复处理弹幕
	public final static Vector<AutoReply> replys = new Vector<AutoReply>();
	
	//日志线程
	public static LogThread logThread;
	//处理感谢关注线程
	public static ParseThankFollowThread parsethankFollowThread = new ParseThankFollowThread();
	//处理感谢进入直播间线程
	public static ParseThankWelcomeThread parseThankWelcomeThread = new ParseThankWelcomeThread();
	//广告姬线程
	public static AdvertThread advertThread;
	//感谢礼物数据集线程
	public static ParseThankGiftThread parsethankGiftThread = new ParseThankGiftThread();
	//发送弹幕线程
	public static SendBarrageThread sendBarrageThread;
	//屏蔽天选礼物线程
	public static GiftShieldThread giftShieldThread = new GiftShieldThread();
	//屏蔽天选关注线程
	public static FollowShieldThread followShieldThread = new FollowShieldThread();
	//屏蔽天选欢迎线程
	public static WelcomeShieldThread welcomeShieldThread = new WelcomeShieldThread();
	//自动回复线程
	public static AutoReplyThread autoReplyThread;
	
	//用户在线线程集
	public static HeartBeatThread heartBeatThread;
	public static HeartBeatsThread heartBeatsThread;
	public static UserOnlineHeartThread userOnlineHeartThread;
	//小心心线程
	public static SmallHeartThread smallHeartThread;
	//签到线程

	//是否显示人气
	public static Boolean IS_ROOM_POPULARITY =false;
	
	//task
//	public static SchedulingRunnableUtil dosigntask = null;
	
	public static Long ROOMID_LONG = null;
	public static String SMALLHEART_ADRESS = null;
	public static boolean is_sign= false;

	public static String VERSION ="";

	public static String NEW_VERSION ="";
	public static String NEW_VERSION_DOWNLOAD_URL ="";

	public static String ANNOUNCE = null;

	public final static String PROFILE_NAME = "DanmujiProfile";
	public final static String PROFILE_SET_NAME = "set";

	public final static String PROFILE_COOKIE_NAME = "ySZL4SBB";
	public static boolean INIT_CHECK_EDITION = false;
	public static boolean INIT_CHECK_ANNOUNCE = false;
	public static int manager_login_size=0;

//	//view
//	//房间礼物集合
//	public static Map<Integer, RoomGift> roomGiftConcurrentHashMap = new ConcurrentHashMap<Integer, RoomGift>(300);
//
	//可以赠送礼物集合 要初始化
	public static Map<Integer,AutoSendGift> autoSendGiftMap = null;


	//方法区

	public static void init_user(){
		PublicDataConf.COOKIE = null;
		PublicDataConf.USER = null;
		PublicDataConf.USERCOOKIE = null;
		PublicDataConf.USERBARRAGEMESSAGE = null;
	}

	public static void init_send(){
		PublicDataConf.replys.clear();
		PublicDataConf.thankGiftConcurrentHashMap.clear();
		PublicDataConf.barrageString.clear();
		PublicDataConf.interacts.clear();
		PublicDataConf.interactWelcome.clear();
	}

	public static void init_all(){
		PublicDataConf.replys.clear();
		PublicDataConf.resultStrs.clear();
		PublicDataConf.thankGiftConcurrentHashMap.clear();
		PublicDataConf.barrageString.clear();
		PublicDataConf.logString.clear();
		PublicDataConf.interacts.clear();
		PublicDataConf.interactWelcome.clear();
		PublicDataConf.SHIELDGIFTNAME = null;
		PublicDataConf.ISSHIELDFOLLOW = false;
		PublicDataConf.ISSHIELDWELCOME = false;
	}

	public static void init_connect(){
		PublicDataConf.SHIELDGIFTNAME = null;
		PublicDataConf.replys.clear();
		PublicDataConf.resultStrs.clear();
		PublicDataConf.thankGiftConcurrentHashMap.clear();
		PublicDataConf.barrageString.clear();
		PublicDataConf.interacts.clear();
		PublicDataConf.logString.clear();
		PublicDataConf.interactWelcome.clear();
		PublicDataConf.ISSHIELDWELCOME=false;
		PublicDataConf.ISSHIELDFOLLOW=false;
		PublicDataConf.ROOMID = null;
		PublicDataConf.ANCHOR_NAME = null;
		PublicDataConf.AUID = null;
		PublicDataConf.FANSNUM = null;
		PublicDataConf.SHORTROOMID = null;
		PublicDataConf.lIVE_STATUS = 0;
		PublicDataConf.ROOM_POPULARITY = 1L;
	}

	@Value("${danmuji.version}")
	public void setVERSION(String VERSION) {
		PublicDataConf.VERSION = VERSION;
	}
	@Value("${danmuji.version}")
	public void setNewVersion(String newVersion) {
		PublicDataConf.NEW_VERSION = newVersion;
	}
}
