package xyz.acproject.danmuji.entity.room_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.utils.FastJsonUtils;

import java.io.Serializable;

/**
 * @ClassName Room
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:08
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480650792452371498L;
	//真实房间号
	private String roomid;
	//主播uid
	private String uid;
	//主播公告
	private String content;
	//公告发布时间
	private String time;
	//不知道什么状态
	private String statue;
	//主播名称
	private String uname;

}
