package xyz.acproject.danmuji.entity.room_data;

import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jane
 * @ClassName RoomBlock
 * @Description TODO
 * @date 2021/4/12 18:13
 * @Copyright:2021
 */
public class RoomBlock implements Serializable {
    private String admin_name;
    private Long adminid;
    private String block_end_time;
    private String ctime;
    private Long id;
    private String msg;
    private String msg_time;
    private Long roomid;
    private Short type;
    private Long uid;
    private String uname;
    private Long blockTimeStamp;
    private Long createTimeStamp;
    private Long msgTimeStamp;

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public Long getAdminid() {
        return adminid;
    }

    public void setAdminid(Long adminid) {
        this.adminid = adminid;
    }

    public String getBlock_end_time() {
        return block_end_time;
    }

    public void setBlock_end_time(String block_end_time) {
        this.block_end_time = block_end_time;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getBlockTimeStamp() {
        if(StringUtils.isNotBlank(getBlock_end_time())){
            try {
                Date now = JodaTimeUtils.parse(getBlock_end_time());
                return now.getTime();
            }catch (Exception e){
            }
        }
        return blockTimeStamp;
    }

    public void setBlockTimeStamp(Long blockTimeStamp) {
        this.blockTimeStamp = blockTimeStamp;
    }

    public Long getCreateTimeStamp() {
        if(StringUtils.isNotBlank(getCtime())){
            try {
                Date now = JodaTimeUtils.parse(getCtime());
                return now.getTime();
            }catch (Exception e){
            }
        }
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Long getMsgTimeStamp() {
        if(StringUtils.isNotBlank(getMsg_time())){
            try {
                Date now = JodaTimeUtils.parse(getMsg_time());
                return now.getTime();
            }catch (Exception e){
            }
        }
        return msgTimeStamp;
    }

    public void setMsgTimeStamp(Long msgTimeStamp) {
        this.msgTimeStamp = msgTimeStamp;
    }
}
