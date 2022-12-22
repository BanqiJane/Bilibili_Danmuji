package xyz.acproject.danmuji.entity.high_level_danmu;

import lombok.Data;
import xyz.acproject.danmuji.entity.danmu_data.Barrage;

import java.io.Serializable;

/**
 * @author BanqiJane
 * @ClassName Hbarrage
 * @Description TODO
 * @date 2020年9月4日 下午12:17:17
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
public class Hbarrage implements Serializable, Cloneable {
    /**
     *
     */
    private static Hbarrage hbarrage = new Hbarrage();
    private static final long serialVersionUID = -699907643016533390L;
    // 用户uid() 位置info[2][0]
    private Long uid;
    // 用户名称() 位置info[2][1]
    private String uname;
    // 弹幕 ()位置info[1]
    private String msg;
    // 弹幕发送时间 ()位置info[0][4]
    private Long timestamp;
    // 是否为房管( 0否 1是)位置 info[2][2] 0也是可以是主播 注意
    private Short manager;
    // 是否为老爷 (0否 1是)位置 info[2][3]
    private Short vip;
    // 是否为年费老爷 (0否 1是)位置 info[2][4]
    private Short svip;
    // 勋章等级() 位置info[3][0] 或者[]
    private Short medal_level;
    // 勋章名称() 位置info[3][1] 或者[]
    private String medal_name;
    // 勋章归属主播()位置info[3][2] 或者[]
    private String medal_anchor;
    // 勋章归宿房间号()位置info[3][3] 或者[]
    private Long medal_room;
    // 用户等级位置info[4][0]
    private Short ulevel;
    // 用户本房间舰队身份(0非舰队，1总督，2提督，3舰长)位置info[7]
    private Short uguard;


    private Hbarrage() {
        super();
        // TODO 自动生成的构造函数存根
    }

    public static Hbarrage getHbarrage() {
        try {
            return (Hbarrage) hbarrage.clone();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
//			e.printStackTrace();
        }
        return new Hbarrage();
    }

    public static Hbarrage getHbarrage(Long uid, String uname, String msg, Long timestamp, Short manager, Short vip, Short svip,
                                       Short medal_level, String medal_name, String medal_anchor, Long medal_room, Short ulevel, Short uguard) {
        try {
            Hbarrage h = (Hbarrage) hbarrage.clone();
            h.setUid(uid);
            h.setUname(uname);
            h.setMsg(msg);
            h.setTimestamp(timestamp);
            h.setManager(manager);
            h.setVip(svip);
            h.setSvip(svip);
            h.setMedal_level(medal_level);
            h.setMedal_name(medal_name);
            h.setMedal_anchor(medal_anchor);
            h.setMedal_room(medal_room);
            h.setUlevel(ulevel);
            h.setUguard(uguard);
            return h;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return new Hbarrage();
    }

    public static Hbarrage copyHbarrage(Barrage barrage) {
        try {
            Hbarrage h = (Hbarrage) hbarrage.clone();
            h.setUid(barrage.getUid());
            h.setUname(barrage.getUname());
            h.setMsg(barrage.getMsg());
            h.setTimestamp(barrage.getTimestamp());
            h.setManager(barrage.getManager());
            h.setVip(barrage.getVip());
            h.setSvip(barrage.getSvip());
            h.setMedal_level(barrage.getMedal_level());
            h.setMedal_name(barrage.getMedal_name());
            h.setMedal_anchor(barrage.getMedal_anchor());
            h.setMedal_room(barrage.getMedal_room());
            h.setUlevel(barrage.getUlevel());
            h.setUguard(barrage.getUguard());
            return h;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return new Hbarrage();
    }


}
