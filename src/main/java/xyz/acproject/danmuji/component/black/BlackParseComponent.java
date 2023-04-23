package xyz.acproject.danmuji.component.black;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.auto_reply.AutoReply;
import xyz.acproject.danmuji.entity.danmu_data.Gift;
import xyz.acproject.danmuji.entity.danmu_data.Interact;
import xyz.acproject.danmuji.http.HttpOtherData;

import java.util.stream.Collectors;

/**
 * @author Admin
 * @ClassName BlackParseComponent
 * @Description TODO
 * @date 2023/1/30 17:09
 * @Copyright:2023
 */
@Component
public class BlackParseComponent {

    private static Logger LOGGER = LogManager.getLogger(BlackParseComponent.class);
    public boolean autoReplay_parse(AutoReply autoReply) {
        //全局开启
        if(PublicDataConf.centerSetConf.getBlack()!=null) {
            if (PublicDataConf.centerSetConf.getBlack().isAuto_reply() || PublicDataConf.centerSetConf.getBlack().isAll()) {
                return this.parse(autoReply);
            }
        }else{
            LOGGER.error("黑名单配置为空");
        }
        return true;
    }


    public boolean interact_parse(Interact interact) {
        //全局开启
        //1欢迎 2关注
//        if (interact.getMsg_type() == 1) {
//
//        } else if (interact.getMsg_type() == 2) {
//
//        }
        if(PublicDataConf.centerSetConf.getBlack()!=null) {
            if (PublicDataConf.centerSetConf.getBlack().isThank_follow() || PublicDataConf.centerSetConf.getBlack().isThank_welcome() || PublicDataConf.centerSetConf.getBlack().isAll()) {
                return this.parse(interact);
            }
        }else{
            LOGGER.error("黑名单配置为空");
        }
        return true;
    }

    public boolean gift_parse(Gift gift) {
        //全局开启
        if(PublicDataConf.centerSetConf.getBlack()!=null) {
            if (PublicDataConf.centerSetConf.getBlack().isThank_gift() || PublicDataConf.centerSetConf.getBlack().isAll()) {
                return this.parse(gift);
            }
        }else{
            LOGGER.error("黑名单配置为空");
        }
        return true;
    }


    public <T> boolean global_parse(T t) {
        //全局开启
        if(PublicDataConf.centerSetConf.getBlack()!=null) {
            if (PublicDataConf.centerSetConf.getBlack().isAll()) {
                return this.parse(t);
            }
        }
        return true;
    }


    public <T> boolean parse(T t) {
        if(PublicDataConf.centerSetConf.getBlack()==null)return true;
        boolean nameFlag = true;
        boolean uidFlag = true;
        //名称规则
        String name = "";
        for (String s : PublicDataConf.centerSetConf.getBlack().getNames()) {
            if (t instanceof AutoReply) {   //自动回复
                AutoReply autoReply = (AutoReply) t;
                name = autoReply.getName();
            } else if (t instanceof Interact) {  //关注感谢 或者 欢迎进入 取决于msg_type
                Interact interact = (Interact) t;
                name = interact.getUname();
            } else if (t instanceof Gift) {  //感谢礼物
                Gift gift = (Gift) t;
                name = gift.getUname();
            }
            //判断
            String replaced_s = s.replace("%", "");
            //非模糊模式
            if(!PublicDataConf.centerSetConf.getBlack().isFuzzy_query()) {
                if (s.startsWith("%") && s.endsWith("%")) {
                    if (StringUtils.contains(name, replaced_s)) {
                        nameFlag = false;
                        break;
                    }
                } else if (s.startsWith("%")) {
                    if (StringUtils.endsWith(name, replaced_s)) {
                        nameFlag = false;
                        break;
                    }
                } else if (s.endsWith("%")) {
                    if (StringUtils.startsWith(name, replaced_s)) {
                        nameFlag = false;
                        break;
                    }
                } else {
                    if (replaced_s.equals(name)) {
                        nameFlag = false;
                        break;
                    }
                }
            }else{
                if(StringUtils.contains(name, replaced_s)){
                    nameFlag = false;
                    break;
                }
            }
        }
        String uid = "";
        //uid规则
        for (String s : PublicDataConf.centerSetConf.getBlack().getUids()) {
            if (t instanceof AutoReply) {   //自动回复
                AutoReply autoReply = (AutoReply) t;
                uid = autoReply.getUid() + "";
            } else if (t instanceof Interact) {  //关注感谢 或者 欢迎进入 取决于msg_type
                Interact interact = (Interact) t;
                uid = interact.getUid() + "";
            } else if (t instanceof Gift) {  //感谢礼物
                Gift gift = (Gift) t;
                uid = gift.getUid() + "";
            }
            //直接eq
            if (s.equals(uid)) {
                uidFlag = false;
                break;
            }
        }
        boolean totalFlag = nameFlag && uidFlag;
        if(!totalFlag){
            LOGGER.info("黑名单过滤姬拦截到了一条数据：{} - {}",t.getClass().getName(),t);
        }
        return totalFlag;
    }
}
