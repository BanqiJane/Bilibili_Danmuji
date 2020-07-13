package xyz.acproject.danmuji.thread.core;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.conf.SetMethodCode;
import xyz.acproject.danmuji.conf.set.ThankGiftRuleSet;
import xyz.acproject.danmuji.controller.DanmuWebsocket;
import xyz.acproject.danmuji.entity.Welcome.WelcomeGuard;
import xyz.acproject.danmuji.entity.Welcome.WelcomeVip;
import xyz.acproject.danmuji.entity.danmu_data.Barrage;
import xyz.acproject.danmuji.entity.danmu_data.BlockMessage;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.entity.danmu_data.Guard;
import xyz.acproject.danmuji.entity.superchat.SuperChat;
import xyz.acproject.danmuji.enums.ShieldGift;
import xyz.acproject.danmuji.enums.ShieldMessage;
import xyz.acproject.danmuji.enums.ThankGiftStatus;
import xyz.acproject.danmuji.http.HttpUserData;
import xyz.acproject.danmuji.thread.GiftShieldThread;
import xyz.acproject.danmuji.thread.ParseThankGiftThread;
import xyz.acproject.danmuji.tools.ParseIndentityTools;
import xyz.acproject.danmuji.tools.ShieldGiftTools;
import xyz.acproject.danmuji.utils.JodaTimeUtils;
import xyz.acproject.danmuji.utils.SpringUtils;

/**
 * @author banqijane
 *
 */
public class ParseMessageThread extends Thread {
	private Logger LOGGER = LogManager.getLogger(ParseMessageThread.class);
	public volatile boolean FLAG = false;
	private ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap;
	private ShieldGift shieldGift = ShieldGift.OPTIONAL; // 给予默认值
	private Long delaytime = 0L; // 给予默认值
	private String thankGiftString = "感谢%uName%大佬%Type%的%GiftName% x%Num%";// 给予默认值
	private HashSet<String> giftStrings;
	private ThankGiftStatus thankGiftStatus = ThankGiftStatus.one_people;
	private short num = 2;
	private HashSet<ThankGiftRuleSet> thankGiftRuleSets;
	private String guardReport;
	private DanmuWebsocket danmuWebsocket = SpringUtils.getBean(DanmuWebsocket.class);
	private String barrageReport;

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		JSONObject jsonObject = null;
		JSONArray array = null;
		Barrage barrage = null;
		Gift gift = null;
//		Fans fans = null;
//		Rannk rannk = null;
		Guard guard = null;
		SuperChat superChat = null;
		BlockMessage blockMessage = null;
		WelcomeGuard welcomeGuard = null;
		WelcomeVip welcomeVip = null;
//		GiftFile giftFile = null;
		String message = "";
		String cmd = "";
		StringBuilder stringBuilder = new StringBuilder(200);
		while (!FLAG) {
			if (FLAG) {
				LOGGER.debug("数据解析线程手动中止");
				return;
			}
			if (null != PublicDataConf.resultStrs && !PublicDataConf.resultStrs.isEmpty()
					&& !StringUtils.isEmpty(PublicDataConf.resultStrs.get(0))) {
				message = PublicDataConf.resultStrs.get(0);
				try {
					jsonObject = JSONObject.parseObject(message);
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.debug("抛出解析异常:" + e);
					LOGGER.debug(message);
					synchronized (PublicDataConf.parseMessageThread) {
						try {
							PublicDataConf.parseMessageThread.wait();
						} catch (InterruptedException e1) {
							// TODO 自动生成的 catch 块
							LOGGER.debug("处理弹幕包信息线程关闭:" + e1);
//							e.printStackTrace();
						}
					}
				}
				cmd = jsonObject.getString("cmd");
				if (StringUtils.isEmpty(cmd)) {
					synchronized (PublicDataConf.parseMessageThread) {
						try {
							PublicDataConf.parseMessageThread.wait();
						} catch (InterruptedException e1) {
							// TODO 自动生成的 catch 块
							LOGGER.debug("处理弹幕包信息线程关闭:" + e1);
//							e.printStackTrace();
						}
					}
				}
				cmd = parseCmd(cmd);
				switch (cmd) {
				// 弹幕
				case "DANMU_MSG":
					array = jsonObject.getJSONArray("info");
					barrage = Barrage.getBarrage(((JSONArray) array.get(2)).getLong(0),
							((JSONArray) array.get(2)).getString(1), array.getString(1),
							((JSONArray) array.get(0)).getShort(9), ((JSONArray) array.get(0)).getLong(4),
							((JSONArray) array.get(2)).getShort(2), ((JSONArray) array.get(2)).getShort(3),
							((JSONArray) array.get(2)).getShort(4), ((JSONArray) array.get(2)).getInteger(5),
							((JSONArray) array.get(2)).getShort(6),
							((JSONArray) array.get(3)).size() <= 0 ? 0 : ((JSONArray) array.get(3)).getShort(0),
							((JSONArray) array.get(3)).size() <= 0 ? "" : ((JSONArray) array.get(3)).getString(1),
							((JSONArray) array.get(3)).size() <= 0 ? "" : ((JSONArray) array.get(3)).getString(2),
							((JSONArray) array.get(3)).size() <= 0 ? 0L : ((JSONArray) array.get(3)).getLong(3),
							((JSONArray) array.get(4)).getShort(0), ((JSONArray) array.get(4)).getString(3),
							((JSONArray) array.get(5)).getString(0), ((JSONArray) array.get(5)).getString(1),
							array.getShort(7));
					// 过滤礼物自动弹幕

					if (barrage.getMsg_type() == 0) {
						// 判断类型输出
						stringBuilder.append(JodaTimeUtils.format(barrage.getTimestamp()));
						stringBuilder.append(":收到弹幕:");
						if (getMessageControlMap().get(ShieldMessage.is_barrage_guardAndvip) != null
								&& getMessageControlMap().get(ShieldMessage.is_barrage_guardAndvip)) {
							// 老爷
							stringBuilder.append(ParseIndentityTools.parseVip(barrage));
							// 舰长
							stringBuilder.append(ParseIndentityTools.parseGuard(barrage.getUguard()));
						}
						if (getMessageControlMap().get(ShieldMessage.is_barrage_manager) != null
								&& getMessageControlMap().get(ShieldMessage.is_barrage_manager)) {
							// 房管
							stringBuilder
									.append(ParseIndentityTools.parseManager(barrage.getUid(), barrage.getManager()));
						}
						if (getMessageControlMap().get(ShieldMessage.is_barrage_medal) != null
								&& getMessageControlMap().get(ShieldMessage.is_barrage_medal)) {
							// 勋章+勋章等级
							if (!StringUtils.isEmpty(barrage.getMedal_name())) {
								stringBuilder.append("[").append(barrage.getMedal_name())
										.append(barrage.getMedal_level()).append("]");
							}
						}
						if (getMessageControlMap().get(ShieldMessage.is_barrage_ul) != null
								&& getMessageControlMap().get(ShieldMessage.is_barrage_ul)) {
							// ul等级
							stringBuilder.append("[").append("UL").append(barrage.getUlevel()).append("]");
						}
						stringBuilder.append(barrage.getUname());
						stringBuilder.append(" 它说:");
						stringBuilder.append(barrage.getMsg());
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
//						LOGGER.debug("弹幕test：" + message);
					} else {
//				    LOGGER.debug("test中貌似为礼物弹幕：" + message);
					}
					break;

				// 送普通礼物
				case "SEND_GIFT":
					if (getMessageControlMap().get(ShieldMessage.is_gift) != null
							&& getMessageControlMap().get(ShieldMessage.is_gift)) {
						jsonObject = JSONObject.parseObject(jsonObject.getString("data"));
						gift = Gift.getGift(jsonObject.getInteger("giftId"), jsonObject.getShort("giftType"),
								jsonObject.getString("giftName"), jsonObject.getInteger("num"),
								jsonObject.getString("uname"), jsonObject.getString("face"),
								jsonObject.getShort("guard_level"), jsonObject.getLong("uid"),
								jsonObject.getLong("timestamp"), jsonObject.getString("action"),
								jsonObject.getInteger("price"), jsonObject.getString("coin_type"),
								jsonObject.getLong("total_coin"));
//							giftFile = new GiftFile(jsonObject.getInteger("giftId"), jsonObject.getString("giftName"),
//									jsonObject.getInteger("price"), jsonObject.getString("coin_type"));
//							GiftFileTools.addGiftToFile(giftFile.toJson());
						stringBuilder.append(JodaTimeUtils.format(gift.getTimestamp() * 1000));
						stringBuilder.append(":收到道具:");
						stringBuilder.append(gift.getUname());
						stringBuilder.append(" ");
						stringBuilder.append(gift.getAction());
						stringBuilder.append("的:");
						stringBuilder.append(gift.getGiftName());
						stringBuilder.append(" x ");
						stringBuilder.append(gift.getNum());
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					} else {
						if(PublicDataConf.sendBarrageThread!=null) {
						if (!PublicDataConf.sendBarrageThread.FLAG && !PublicDataConf.parsethankGiftThread.TFLAG) {
							jsonObject = JSONObject.parseObject(jsonObject.getString("data"));
							gift = Gift.getGift(jsonObject.getInteger("giftId"), jsonObject.getShort("giftType"),
									jsonObject.getString("giftName"), jsonObject.getInteger("num"),
									jsonObject.getString("uname"), jsonObject.getString("face"),
									jsonObject.getShort("guard_level"), jsonObject.getLong("uid"),
									jsonObject.getLong("timestamp"), jsonObject.getString("action"),
									jsonObject.getInteger("price"), jsonObject.getString("coin_type"),
									jsonObject.getLong("total_coin"));
						}
						}
					}
					// 感谢礼物处理
					if (gift != null && getMessageControlMap().get(ShieldMessage.is_giftThank) != null
							&& getMessageControlMap().get(ShieldMessage.is_giftThank)) {
						if (PublicDataConf.sendBarrageThread != null && !PublicDataConf.sendBarrageThread.FLAG) {
							if (getShieldGift() != ShieldGift.CUSTOM_RULE) {
								gift = ShieldGiftTools.shieldGift(gift, getShieldGift(), getGiftStrings(), null);
							}
							if (gift != null) {
								if (!StringUtils.isEmpty(PublicDataConf.SHIELDGIFTNAME)) {
									if (gift.getGiftName().equals(PublicDataConf.SHIELDGIFTNAME)) {
										gift = null;
									}
								}
							}

							parseGiftSetting(gift);
						}
					}
//		            LOGGER.debug("让我看看是谁送礼物:::"+jsonObject);
					break;

				// 部分金瓜子礼物连击
				case "COMBO_SEND":
//			        LOGGER.debug("部分金瓜子礼物连击:::" + message);
					break;

				// 部分金瓜子礼物连击
				case "COMBO_END":
//				    LOGGER.debug("部分金瓜子礼物连击:::" + message);
					break;

				// 上舰
				case "GUARD_BUY":
					if (getMessageControlMap().get(ShieldMessage.is_gift) != null
							&& getMessageControlMap().get(ShieldMessage.is_gift)) {
						guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
						stringBuilder.append(JodaTimeUtils.format(guard.getStart_time() * 1000));
						stringBuilder.append(":有人上船:");
						stringBuilder.append(guard.getUsername());
						stringBuilder.append("在本房间开通了");
						stringBuilder.append(guard.getNum());
						stringBuilder.append("个月");
						stringBuilder.append(guard.getGift_name());
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());

						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					}
					if (getMessageControlMap().get(ShieldMessage.is_giftThank) != null
							&& getMessageControlMap().get(ShieldMessage.is_giftThank)) {
						if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG) {
							guard = JSONObject.parseObject(jsonObject.getString("data"), Guard.class);
							gift = new Gift();
							gift.setGiftName(guard.getGift_name());
							gift.setNum(guard.getNum());
							gift.setPrice(guard.getPrice());
							gift.setTotal_coin((long) guard.getNum() * guard.getPrice());
							gift.setTimestamp(guard.getStart_time());
							gift.setAction("赠送");
							gift.setCoin_type("gold");
							gift.setUname(guard.getUsername());
							gift.setUid(guard.getUid());
							gift = ShieldGiftTools.shieldGift(gift, getShieldGift(), getGiftStrings(), null);
							if(gift!=null) {
							parseGiftSetting(gift);
							}
							if (getMessageControlMap().get(ShieldMessage.is_guard_report) != null
									&& getMessageControlMap().get(ShieldMessage.is_guard_report)) {
								String report = getGuardReport().replaceAll("\n", "\\\\r\\\\n");
								report = report.replaceAll("%uName%", guard.getUsername());
								try {
//									if (PublicDataConf.ROOMID == 5067) {
									if (!StringUtils.isEmpty(getBarrageReport().trim())) {
										if (HttpUserData.httpPostSendMsg(guard.getUid(), report) == 0) {
											PublicDataConf.barrageString.add(getBarrageReport());
										}
										synchronized (PublicDataConf.sendBarrageThread) {
											PublicDataConf.sendBarrageThread.notify();
										}
									} else {
										HttpUserData.httpPostSendMsg(guard.getUid(), report);
									}
//									}else {
//										LOGGER.debug(report);
//										LOGGER.debug(getBarrageReport());
//									}
								} catch (Exception e) {
									// TODO: handle exception
									LOGGER.error("发送舰长私信失败，原因：" + e);
								}
							}
						}
					}

//					LOGGER.debug("有人上舰长啦:::" + message);
					break;

				// 上舰消息推送
				case "GUARD_LOTTERY_START":
//					LOGGER.debug("上舰消息推送:::" + message);
					break;

				// 上舰抽奖消息推送
				case "USER_TOAST_MSG":
//					LOGGER.debug("上舰抽奖消息推送:::" + message);
					break;

				// 醒目留言
				case "SUPER_CHAT_MESSAGE":
					if (getMessageControlMap().get(ShieldMessage.is_gift) != null
							&& getMessageControlMap().get(ShieldMessage.is_gift)) {
						superChat = JSONObject.parseObject(jsonObject.getString("data"), SuperChat.class);
						stringBuilder.append(JodaTimeUtils.format(superChat.getStart_time() * 1000));
						stringBuilder.append(":收到留言:");
						stringBuilder.append(superChat.getUser_info().getUname());
						stringBuilder.append(" 他用了");
						stringBuilder.append(superChat.getPrice() * 1000);
						stringBuilder.append("金瓜子留言了");
						stringBuilder.append(ParseIndentityTools.parseTime(superChat.getTime()));
						stringBuilder.append("秒说: ");
						stringBuilder.append(superChat.getMessage());
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}

						stringBuilder.delete(0, stringBuilder.length());
					}
					if (getMessageControlMap().get(ShieldMessage.is_giftThank) != null
							&& getMessageControlMap().get(ShieldMessage.is_giftThank)) {
						if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG) {
							superChat = JSONObject.parseObject(jsonObject.getString("data"), SuperChat.class);
							gift = new Gift();
							stringBuilder.append(ParseIndentityTools.parseTime(superChat.getTime()));
							stringBuilder.append("秒");
							stringBuilder.append(superChat.getGift().getGift_name());
							gift.setGiftName(stringBuilder.toString());
							gift.setNum(superChat.getGift().getNum());
							gift.setPrice(superChat.getPrice() * 1000);
							gift.setTotal_coin((long)superChat.getPrice() * 1000l);
							gift.setTimestamp(superChat.getStart_time()*1000);
							gift.setAction("赠送");
							gift.setCoin_type("gold");
							gift.setUname(superChat.getUser_info().getUname());
							gift.setUid(superChat.getUid());
							gift = ShieldGiftTools.shieldGift(gift, getShieldGift(), getGiftStrings(), null);
							if(gift!=null) {
							parseGiftSetting(gift);
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					}
					break;

				// 醒目留言日文翻译
				case "SUPER_CHAT_MESSAGE_JPN":
//					LOGGER.debug("醒目留言日文翻译消息推送:::" + message);
					break;

				// 删除醒目留言
				case "SUPER_CHAT_MESSAGE_DELETE":
//					LOGGER.debug("该条醒目留言已被删除:::" + message);
					break;

				// 欢迎老爷进来本直播间
				case "WELCOME":
					// 区分年月费老爷
					/*
					 * if(welcomVip.getSvip()==1) {
					 * System.out.println(JodaTimeUtils.getCurrentTimeString()+":欢迎年费老爷:"+welcomeVip
					 * .getUname()+" 进入直播间"); }else {
					 * System.out.println(JodaTimeUtils.getCurrentTimeString()+":欢迎月费老爷:"+welcomeVip
					 * .getUname()+" 进入直播间"); }
					 */
					if (getMessageControlMap().get(ShieldMessage.is_welcome) != null
							&& getMessageControlMap().get(ShieldMessage.is_welcome)) {
						welcomeVip = JSONObject.parseObject(jsonObject.getString("data"), WelcomeVip.class);
						stringBuilder.append(JodaTimeUtils.getCurrentTimeString());
						stringBuilder.append(":欢迎老爷:");
						stringBuilder.append(welcomeVip.getUname());
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						stringBuilder.append(" 进入直播间");

						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					}

//					LOGGER.debug("让我看看哪个老爷大户进来了:::" + message);
					break;

				// 欢迎舰长进入直播间
				case "WELCOME_GUARD":

					if (getMessageControlMap().get(ShieldMessage.is_welcome) != null
							&& getMessageControlMap().get(ShieldMessage.is_welcome)) {
						welcomeGuard = JSONObject.parseObject(jsonObject.getString("data"), WelcomeGuard.class);
						stringBuilder.append(JodaTimeUtils.getCurrentTimeString());
						switch (welcomeGuard.getGuard_level()) {
						case 3:
							stringBuilder.append(":欢迎舰长:");
							break;
						case 2:
							stringBuilder.append(":欢迎提督:");
							break;
						case 1:
							stringBuilder.append(":欢迎总督:");
							break;
						}
						stringBuilder.append(welcomeGuard.getUsername());
						stringBuilder.append(" 进入直播间");
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					}
//					LOGGER.debug("舰长大大进来直播间了:::" + message);
					break;

				// 舰长进入直播间消息
				case "ENTRY_EFFECT":
//					LOGGER.debug("舰长大大进入直播间消息推送:::" + message);
					break;

				// 节奏风暴推送 action 为start和end
				case "SPECIAL_GIFT":
//					LOGGER.debug("节奏风暴推送:::" + message);
					break;

				// 禁言消息
				case "ROOM_BLOCK_MSG":
					if (getMessageControlMap().get(ShieldMessage.is_block) != null
							&& getMessageControlMap().get(ShieldMessage.is_block)) {
						blockMessage = JSONObject.parseObject(jsonObject.getString("data"), BlockMessage.class);
						stringBuilder.append(JodaTimeUtils.getCurrentTimeString());
						stringBuilder.append(":禁言消息:");
						stringBuilder.append(blockMessage.getUname());
						if (blockMessage.getOperator() == 2) {
							stringBuilder.append("已被主播禁言");
						} else if (blockMessage.getOperator() == 1) {
							stringBuilder.append("已被房管禁言");
						} else {
							stringBuilder.append("已被管理员禁言");
						}
						try {
							danmuWebsocket.sendMessage(stringBuilder.toString());
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						System.out.println(stringBuilder.toString());
						if (PublicDataConf.logThread != null && !PublicDataConf.logThread.FLAG) {
							PublicDataConf.logString.add(stringBuilder.toString());
							synchronized (PublicDataConf.logThread) {
								PublicDataConf.logThread.notify();
							}
						}
						stringBuilder.delete(0, stringBuilder.length());
					}
//					LOGGER.debug("谁这么惨被禁言了:::" + message);
					break;

				// 本主播在本分区小时榜排名更新推送 不会更新页面的排行显示信息
				case "ACTIVITY_BANNER_UPDATE_V2":
//					LOGGER.debug("小时榜消息更新推送:::" + message);
					break;

				// 本房间分区修改
				case "ROOM_CHANGE":
//					LOGGER.debug("房间分区已更新:::" + message);
					break;

				// 本房间分区排行榜更新 更新页面的排行显示信息
				case "ROOM_RANK":
//					rannk = JSONObject.parseObject(jsonObject.getString("data"), Rannk.class);
//					stringBuilder.append(JodaTimeUtils.format(rannk.getTimestamp() * 1000)).append(":榜单更新:")
//							.append(rannk.getRank_desc());
//
//					System.out.println(stringBuilder.toString());
//					stringBuilder.delete(0, stringBuilder.length());
//					LOGGER.debug("小时榜信息更新推送:::" + message);
					break;

				// 推测为获取本小时榜榜单第一名主播的信息 推测激活条件为本直播间获得第一
				case "new_anchor_reward":
//					LOGGER.debug("获取本小时榜榜单第一名主播的信息:::" + message);
					break;

				// 小时榜榜单信息推送 推测激活条件为本直播间获得第一
				case "HOUR_RANK_AWARDS":
//					LOGGER.debug("恭喜xxxx直播间获得:::" + message);
					break;

				// 直播间粉丝数更新 经常
				case "ROOM_REAL_TIME_MESSAGE_UPDATE":
//					fans = JSONObject.parseObject(jsonObject.getString("data"), Fans.class);
//					stringBuilder.append(JodaTimeUtils.getCurrentTimeString()).append(":消息推送:").append("房间号")
//							.append(fans.getRoomid()).append("的粉丝数:").append(fans.getFans());
					PublicDataConf.FANSNUM = JSONObject.parseObject(jsonObject.getString("data")).getLong("fans");
//					System.out.println(stringBuilder.toString());
//					stringBuilder.delete(0, stringBuilder.length());
//					LOGGER.debug("直播间粉丝数更新消息推送:::" + message);
					break;

				// 直播间许愿瓶消息推送更新
				case "WISH_BOTTLE":
//					LOGGER.debug("直播间许愿瓶消息推送更新:::" + message);
					break;

				// 广播小电视类抽奖信息推送,包括本房间的舰长礼物包括,本直播间所在小时榜榜单主播信息的推送 需要unicode转义
				case "NOTICE_MSG":
//					message = ByteUtils.unicodeToString(message);
//					LOGGER.debug("小电视类抽奖信息推送:::" + message);
					break;

				// 本房间开启活动抽奖(33图,小电视图,任意门等) 也指本房间内赠送的小电视 摩天大楼类抽奖
				case "RAFFLE_START":
//					LOGGER.debug("本房间开启了活动抽奖:::" + message);
					break;

				// 本房间活动中奖用户信息推送 也指抽奖结束
				case "RAFFLE_END":
//					LOGGER.debug("看看谁是幸运儿:::" + message);
					break;

				// 本房间主播开启了天选时刻
				case "ANCHOR_LOT_START":
					if (getMessageControlMap().get(ShieldMessage.is_giftShield) == null
							&& !getMessageControlMap().get(ShieldMessage.is_giftShield)) {
						break;
					}
					if (PublicDataConf.parsethankGiftThread != null && !PublicDataConf.parsethankGiftThread.TFLAG
							&& !PublicDataConf.giftShieldThread.getState().toString().equals("RUNNABLE")) {
						String giftName = ((JSONObject) jsonObject.get("data")).getString("gift_name");
						int time = ((JSONObject) jsonObject.get("data")).getInteger("time");
						if (!StringUtils.isEmpty(giftName)) {
							if (PublicDataConf.giftShieldThread.getState().toString().equals("TERMINATED")
									|| PublicDataConf.giftShieldThread.getState().toString().equals("NEW")) {
								PublicDataConf.giftShieldThread = new GiftShieldThread();
								PublicDataConf.giftShieldThread.FLAG = false;
								PublicDataConf.giftShieldThread.setGiftName(giftName);
								PublicDataConf.giftShieldThread.setTime(ParseIndentityTools.parseTime(time));
								PublicDataConf.giftShieldThread.start();
							} else {
								PublicDataConf.giftShieldThread.setTime(time);
								PublicDataConf.giftShieldThread.setGiftName(giftName);
							}
						}
					}
					LOGGER.debug("本房间主播开启了天选时刻:::" + message);
					break;

				// 本房间天选时刻结束
				case "ANCHOR_LOT_END":
//					LOGGER.debug("本房间天选时刻结束:::" + message);
					break;

				// 本房间天选时刻获奖信息推送
				case "ANCHOR_LOT_AWARD":
//					LOGGER.debug("本房间天选时刻中奖用户是:::" + message);
					break;

				// 获得推荐位推荐消息
				case "ANCHOR_NORMAL_NOTIFY":
//					LOGGER.debug("本房间获得推荐位:::" + message);				
					break;
				// 周星消息推送
				case "WEEK_STAR_CLOCK":
//			        LOGGER.debug("周星消息推送:::" + message);
					break;

				// 推测本主播周星信息更新
				case "ROOM_BOX_MASTER":
//					LOGGER.debug("周星信息更新:::" + message);
					break;

				// 周星消息推送关闭
				case "ROOM_SKIN_MSG":
//					LOGGER.debug("周星消息推送关闭:::" + message);
					break;

				// 中型礼物多数量赠送消息推送 例如b克拉 亿元
				case "SYS_GIFT":
//					LOGGER.debug("中型礼物多数量赠送消息推送:::" + message);
					break;

				// lol活动礼物？？？
				case "ACTIVITY_MATCH_GIFT":
//					LOGGER.debug("lol专属房间礼物赠送消息推送:::" + message);
					break;

//----------------------------------------pk信息多为要uicode解码-------------------------------------------------
				// 推测为房间pk信息推送
				case "PK_BATTLE_ENTRANCE":
//			        LOGGER.debug("房间pk活动信息推送:::" + message);
					break;

				// 活动pk准备
				case "PK_BATTLE_PRE":
//			        LOGGER.debug("房间活动pk准备:::" + message);
					break;

				// 活动pk开始
				case "PK_BATTLE_START":
//			        LOGGER.debug("房间活动pk开始:::" + message);
					break;

				// 活动pk中
				case "PK_BATTLE_PROCESS":
//			        LOGGER.debug("房间活动pk中:::" + message);
					break;

				// 活动pk详细信息
				case "PK_BATTLE_CRIT":
//			        LOGGER.debug("房间活动pk详细信息推送:::" + message);
					break;

				// 活动pk类型推送
				case "PK_BATTLE_PRO_TYPE":
//			        LOGGER.debug("房间活动pk类型推送:::" + message);
					break;

				// 房间活动pk结束
				case "PK_BATTLE_END":
//			        LOGGER.debug("房间pk活动结束::" + message);
					break;

				// 活动pk结果用户 推送
				case "PK_BATTLE_SETTLE_USER":
//			        LOGGER.debug("活动pk结果用户 推送::" + message);	
					break;

				// 活动pk礼物开始 1辣条
				case "PK_LOTTERY_START":
//			        LOGGER.debug("活动pk礼物开始 推送::" + message);			
					break;

				// 活动pk结果房间
				case "PK_BATTLE_SETTLE":
//			        LOGGER.debug("活动pk结果房间推送::" + message);
					break;

				// pk开始
				case "PK_START":
//					LOGGER.debug("房间pk开始:::" + message);
					break;

				// pk准备中
				case "PK_PRE":
//					LOGGER.debug("房间pk准备中:::" + message);
					break;

				// pk载入中
				case "PK_MATCH":
//					LOGGER.debug("房间pk载入中:::" + message);
					break;

				// pk再来一次触发
				case "PK_CLICK_AGAIN":
//					LOGGER.debug("房间pk再来一次:::" + message);			
					break;
				// pk结束
				case "PK_MIC_END":
//					LOGGER.debug("房间pk结束:::" + message);	
					break;

				// pk礼物信息推送 激活条件推测为pk胜利 可获得一个辣条
				case "PK_PROCESS":
//					LOGGER.debug("房间pk礼物推送:::" + message);				
					break;

				// pk结果信息推送
				case "PK_SETTLE":
//					LOGGER.debug("房间pk结果信息推送:::" + message);					
					break;

				// pk结束信息推送
				case "PK_END":
//					LOGGER.debug("房间pk结束信息推送:::" + message);					
					break;

				// 系统信息推送
				case "SYS_MSG":
//					LOGGER.debug("系统信息推送:::" + message);					
					break;

				// 总督登场消息
				case "GUARD_MSG":
//					LOGGER.debug("总督帅气登场:::" + message);
					break;

				// 热门房间？？？？广告房间？？ 不知道这是什么 推测本直播间激活 目前常见于打广告的官方直播间 例如手游 碧蓝航线 啥的。。
				case "HOT_ROOM_NOTIFY":
//					LOGGER.debug("热门房间推送消息:::" + message);
					break;

			    //小时榜面板消息推送
				case "PANEL":
//					LOGGER.debug("热小时榜面板消息推送:::" + message);	
					break;
					
				// 星之耀宝箱使用 n
				case "ROOM_BOX_USER":
//					LOGGER.debug("星之耀宝箱使用:::" + message);
					break;

				// 语音加入？？？？ 暂不知道
				case "VOICE_JOIN_ROOM_COUNT_INFO":
//					LOGGER.debug("语音加入:::" + message);
					break;

				// 语音加入list？？？？ 暂不知道
				case "VOICE_JOIN_LIST":
//					LOGGER.debug("语音加入list:::" + message);
					break;

				// lol活动
				case "LOL_ACTIVITY":
//					LOGGER.debug("lol活动:::" + message);
					break;

				// 队伍礼物排名 目前只在6号lol房间抓取过
				case "MATCH_TEAM_GIFT_RANK":
//					LOGGER.debug("队伍礼物排名:::" + message);		
					break;

				// 6.13端午节活动粽子新增活动更新命令 激活条件有人赠送活动礼物
				case "ROOM_BANNER":
//					LOGGER.debug("收到活动礼物赠送，更新信息:::" + message);	
					break;

				// 设定房管消息 新房管的诞生
				case "room_admin_entrance":
//					LOGGER.debug("有人被设为了房管:::" + message);
					break;

				// 房管列表更新消息 激活条件为新房管的诞生
				case "ROOM_ADMINS":
//					LOGGER.debug("房管列表更新推送:::" + message);	
					break;

				// 房间护盾 推测推送消息为破站官方屏蔽的关键字 触发条件未知
				case "ROOM_SHIELD":
//					LOGGER.debug("房间护盾触发消息:::" + message);
					break;

				// 主播开启房间全局禁言
				case "ROOM_SILENT_ON":
//					LOGGER.debug("主播开启房间全局禁言:::" + message);
					break;

				// 主播关闭房间全局禁言
				case "ROOM_SILENT_OFF":
//					LOGGER.debug("主播关闭房间全局禁言:::" + message);
					break;

				// 主播状态检测 直译 不知道什么情况 statue 1 ，2 ，3 ，4
				case "ANCHOR_LOT_CHECKSTATUS":
//					LOGGER.debug("主播房间状态检测:::" + message);
					break;

				// 房间警告消息 目前已知触发条件为 房间分区不正确
				case "WARNING":
//					LOGGER.debug("房间警告消息:::" + message);
					break;
				// 直播开启
				case "LIVE":
					PublicDataConf.lIVE_STATUS = 1;
//					room_id = jsonObject.getLong("roomid");
//					if (room_id == PublicDataConf.ROOMID) {
					// 仅在直播有效 广告线程 改为配置文件
					SetMethodCode.start(PublicDataConf.centerSetConf);
					PublicDataConf.IS_ROOM_POPULARITY = true;
//					LOGGER.debug("直播开启:::" + message);
					break;

				// 直播超管被切断
				case "CUT_OFF":
//					LOGGER.debug("很不幸，本房间直播被切断:::" + message);	
					break;

				// 本房间已被封禁
				case "ROOM_LOCK":
//					LOGGER.debug("很不幸，本房间已被封禁:::" + message);	
					break;

				// 直播准备中(或者是关闭直播)
				case "PREPARING":
					PublicDataConf.lIVE_STATUS = 0;
					SetMethodCode.start(PublicDataConf.centerSetConf);
					PublicDataConf.IS_ROOM_POPULARITY = false;
//					LOGGER.debug("直播准备中(或者是关闭直播):::" + message);
					break;

				// 勋章亲密度达到上每日上限通知
				case "LITTLE_TIPS":
//					LOGGER.debug("勋章亲密度达到上每日上限:::" + message);		
					break;

				default:
//					LOGGER.debug("其他未处理消息:" + message);
					break;
				}
				PublicDataConf.resultStrs.remove(0);
			} else

			{
				synchronized (PublicDataConf.parseMessageThread) {
					try {
						PublicDataConf.parseMessageThread.wait();
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
//						LOGGER.debug("处理弹幕包信息线程关闭:" + e);
//						e.printStackTrace();
					}
				}
			}

		}

	}

	public void DelayTimeSetting() {
		synchronized (PublicDataConf.parsethankGiftThread) {
			if (PublicDataConf.parsethankGiftThread != null) {
				if (PublicDataConf.parsethankGiftThread.getState().toString().equals("TERMINATED")
						|| PublicDataConf.parsethankGiftThread.getState().toString().equals("NEW")) {
					PublicDataConf.parsethankGiftThread = new ParseThankGiftThread();
					PublicDataConf.parsethankGiftThread.setDelaytime(getDelaytime());
					PublicDataConf.parsethankGiftThread.start();
					PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
					PublicDataConf.parsethankGiftThread.setThankGiftString(getThankGiftString());
					PublicDataConf.parsethankGiftThread.setThankGiftStatus(getThankGiftStatus());
					PublicDataConf.parsethankGiftThread.setThankGiftRuleSets(getThankGiftRuleSets());
					PublicDataConf.parsethankGiftThread.setNum(getNum());
				} else {
					PublicDataConf.parsethankGiftThread.setTimestamp(System.currentTimeMillis());
					PublicDataConf.parsethankGiftThread.setThankGiftString(getThankGiftString());
					PublicDataConf.parsethankGiftThread.setThankGiftStatus(getThankGiftStatus());
					PublicDataConf.parsethankGiftThread.setThankGiftRuleSets(getThankGiftRuleSets());
					PublicDataConf.parsethankGiftThread.setNum(getNum());
				}
			}
		}
	}

	public synchronized void parseGiftSetting(Gift gift) {
		if (gift != null) {
			if (PublicDataConf.sendBarrageThread != null && PublicDataConf.parsethankGiftThread != null) {
				if (!PublicDataConf.sendBarrageThread.FLAG && !PublicDataConf.parsethankGiftThread.TFLAG) {
					if (PublicDataConf.thankGiftConcurrentHashMap.size() > 0) {
						if (PublicDataConf.thankGiftConcurrentHashMap.get(gift.getUname()) != null) {
							for (Entry<String, Vector<Gift>> entry : PublicDataConf.thankGiftConcurrentHashMap
									.entrySet()) {
								if (entry.getKey().equals(gift.getUname())) {
									Vector<Gift> gifts = entry.getValue();
									int flagNum = 0;
									for (Gift giftChild : gifts) {
										int num1 = giftChild.getNum();
										int num2 = gift.getNum();
										long total_coin1 = giftChild.getTotal_coin();
										long total_coin2 = gift.getTotal_coin();
										if (giftChild.getGiftName().equals(gift.getGiftName())) {
											giftChild.setNum(num1 + num2);
											giftChild.setTotal_coin(total_coin1 + total_coin2);
											DelayTimeSetting();
											flagNum++;
										}
									}
									if (flagNum == 0) {
										gifts.add(gift);
										DelayTimeSetting();
									}
								}
							}
						} else {
							Vector<Gift> gifts = new Vector<Gift>();
							gifts.add(gift);
							PublicDataConf.thankGiftConcurrentHashMap.put(gift.getUname(), gifts);
							DelayTimeSetting();
						}
					} else {
						Vector<Gift> gifts = new Vector<Gift>();
						gifts.add(gift);
						PublicDataConf.thankGiftConcurrentHashMap.put(gift.getUname(), gifts);
						DelayTimeSetting();
					}
				}
			}
		}
	}

	public ShieldGift getShieldGift() {
		return shieldGift;
	}

	public void setShieldGift(ShieldGift shieldGift) {
		this.shieldGift = shieldGift;
	}

	public Long getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(Long delaytime) {
		this.delaytime = delaytime;
	}

	public String getThankGiftString() {
		return thankGiftString;
	}

	public void setThankGiftString(String thankGiftString) {
		this.thankGiftString = thankGiftString;
	}

	public HashSet<String> getGiftStrings() {
		return giftStrings;
	}

	public void setGiftStrings(HashSet<String> giftStrings) {
		this.giftStrings = giftStrings;
	}

	public ConcurrentHashMap<ShieldMessage, Boolean> getMessageControlMap() {
		return messageControlMap;
	}

	public void setMessageControlMap(ConcurrentHashMap<ShieldMessage, Boolean> messageControlMap) {
		this.messageControlMap = messageControlMap;
	}

	public ThankGiftStatus getThankGiftStatus() {
		return thankGiftStatus;
	}

	public void setThankGiftStatus(ThankGiftStatus thankGiftStatus) {
		this.thankGiftStatus = thankGiftStatus;
	}

	public short getNum() {
		return num;
	}

	public void setNum(short num) {
		this.num = num;
	}

	public HashSet<ThankGiftRuleSet> getThankGiftRuleSets() {
		return thankGiftRuleSets;
	}

	public void setThankGiftRuleSets(HashSet<ThankGiftRuleSet> thankGiftRuleSets) {
		this.thankGiftRuleSets = thankGiftRuleSets;
	}

	public String getGuardReport() {
		return guardReport;
	}

	public void setGuardReport(String guardReport) {
		this.guardReport = guardReport;
	}

	public String getBarrageReport() {
		return barrageReport;
	}

	public void setBarrageReport(String barrageReport) {
		this.barrageReport = barrageReport;
	}

	public static String parseCmd(String cmd) {
		if (cmd.startsWith("DANMU_MSG")) {
			return "DANMU_MSG";
		}
		if (cmd.startsWith("SEND_GIFT")) {
			return "SEND_GIFT";
		}
		if (cmd.startsWith("GUARD_BUY")) {
			return "GUARD_BUY";
		}
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE")) {
//			return "SUPER_CHAT_MESSAGE";
//		}
//		if(cmd.startsWith("WELCOME")) {
//			return "WELCOME";
//		}
//		if(cmd.startsWith("WELCOME_GUARD")) {
//			return "WELCOME_GUARD";
//		}
//		if(cmd.startsWith("ROOM_RANK")) {
//			return "ROOM_RANK";
//		}
//		if(cmd.startsWith("ROOM_REAL_TIME_MESSAGE_UPDATE")) {
//			return "ROOM_REAL_TIME_MESSAGE_UPDATE";
//		}
//		if(cmd.startsWith("WARNING")) {
//			return "WARNING";
//		}
//		if(cmd.startsWith("LIVE")) {
//			return "LIVE";
//		}
//		if(cmd.startsWith("PREPARING")) {
//			return "PREPARING";
//		}

//		if(cmd.startsWith("SPECIAL_GIFT")) {
//			return "SPECIAL_GIFT";
//		}
//		if(cmd.startsWith("USER_TOAST_MSG")) {
//			return "USER_TOAST_MSG";
//		}
//		if(cmd.startsWith("NOTICE_MSG")) {
//			return "NOTICE_MSG";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_START")) {
//			return "ANCHOR_LOT_START";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_END")) {
//			return "ANCHOR_LOT_END";
//		}

//		
//		if(cmd.startsWith("COMBO_SEND")) {
//			return "COMBO_SEND";
//		}
//		if(cmd.startsWith("GUARD_LOTTERY_START")) {
//			return "GUARD_LOTTERY_START";
//		}
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE_JPN")) {
//			return "SUPER_CHAT_MESSAGE_JPN";
//		}
//		if(cmd.startsWith("SUPER_CHAT_MESSAGE_DELETE")) {
//			return "SUPER_CHAT_MESSAGE_DELETE";
//		}
//		if(cmd.startsWith("ENTRY_EFFECT")) {
//			return "ENTRY_EFFECT";
//		}
//		if(cmd.startsWith("ACTIVITY_BANNER_UPDATE_V2")) {
//			return "ACTIVITY_BANNER_UPDATE_V2";
//		}
//		if(cmd.startsWith("ROOM_CHANGE")) {
//			return "ROOM_CHANGE";
//		}
//		if(cmd.startsWith("new_anchor_reward")) {
//			return "new_anchor_reward";
//		}
//		if(cmd.startsWith("HOUR_RANK_AWARDS")) {
//			return "HOUR_RANK_AWARDS";
//		}
//		if(cmd.startsWith("WISH_BOTTLE")) {
//			return "WISH_BOTTLE";
//		}
//		if(cmd.startsWith("RAFFLE_START")) {
//			return "RAFFLE_START";
//		}
//		if(cmd.startsWith("RAFFLE_END")) {
//			return "RAFFLE_END";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_AWARD")) {
//			return "ANCHOR_LOT_AWARD";
//		}
//		if(cmd.startsWith("ANCHOR_NORMAL_NOTIFY")) {
//			return "ANCHOR_NORMAL_NOTIFY";
//		}
//		if(cmd.startsWith("WEEK_STAR_CLOCK")) {
//			return "WEEK_STAR_CLOCK";
//		}
//		if(cmd.startsWith("ROOM_BOX_MASTER")) {
//			return "ROOM_BOX_MASTER";
//		}
//		if(cmd.startsWith("ROOM_SKIN_MSG")) {
//			return "ROOM_SKIN_MSG";
//		}
//		if(cmd.startsWith("SYS_GIFT")) {
//			return "SYS_GIFT";
//		}
//		if(cmd.startsWith("ACTIVITY_MATCH_GIFT")) {
//			return "ACTIVITY_MATCH_GIFT";
//		}
//		if(cmd.startsWith("PK_BATTLE_ENTRANCE")) {
//			return "PK_BATTLE_ENTRANCE";
//		}
//		if(cmd.startsWith("PK_START")) {
//			return "PK_START";
//		}	
//		if(cmd.startsWith("PK_PRE")) {
//			return "PK_PRE";
//		}
//		if(cmd.startsWith("PK_MATCH")) {
//			return "PK_MATCH";
//		}
//		if(cmd.startsWith("PK_CLICK_AGAIN")) {
//			return "PK_CLICK_AGAIN";
//		}
//		if(cmd.startsWith("PK_MIC_END")) {
//			return "PK_MIC_END";
//		}
//		if(cmd.startsWith("PK_PROCESS")) {
//			return "PK_PROCESS";
//		}
//		if(cmd.startsWith("PK_SETTLE")) {
//			return "PK_SETTLE";
//		}
//		if(cmd.startsWith("PK_END")) {
//			return "PK_END";
//		}
//		if(cmd.startsWith("SYS_MSG")) {
//			return "SYS_MSG";
//		}
//		if(cmd.startsWith("GUARD_MSG")) {
//			return "GUARD_MSG";
//		}
//		if(cmd.startsWith("HOT_ROOM_NOTIFY")) {
//			return "HOT_ROOM_NOTIFY";
//		}
//		if(cmd.startsWith("room_admin_entrance")) {
//			return "room_admin_entrance";
//		}
//		if(cmd.startsWith("ROOM_ADMINS")) {
//			return "ROOM_ADMINS";
//		}
//		if(cmd.startsWith("ROOM_SHIELD")) {
//			return "ROOM_SHIELD";
//		}
//		if(cmd.startsWith("ROOM_SILENT_ON")) {
//			return "ROOM_SILENT_ON";
//		}
//		if(cmd.startsWith("ROOM_SILENT_OFF")) {
//			return "ROOM_SILENT_OFF";
//		}
//		if(cmd.startsWith("ANCHOR_LOT_CHECKSTATUS")) {
//			return "ANCHOR_LOT_CHECKSTATUS";
//		}
//		if(cmd.startsWith("VOICE_JOIN_ROOM_COUNT_INFO")) {
//			return "VOICE_JOIN_ROOM_COUNT_INFO";
//		}
//		if(cmd.startsWith("VOICE_JOIN_LIST")) {
//			return "VOICE_JOIN_LIST";
//		}
//		if(cmd.startsWith("")) {
//			return "";
//		}

		return cmd;
	}
}
