package xyz.acproject.danmuji.entity.superchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName MedalInfo
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:06
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedalInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8288034568124823988L;
	//勋章头图标id 如果没有就0 如果有就指定id
	private Long icon_id;
	//不知道什么id
	private Long target_id;
	//特殊？？？不知道什么
	private String special;
	//勋章所属主播名字
	private String anchor_uname;
	//勋章所属主播房间
	private String anchor_roomid;
	//勋章等级
	private Short medal_level;
	//勋章名字
	private String medal_name;
	//勋章颜色
	private String medal_color;

	private Integer is_lighted = 0;

	private Short guard_level = 0;

}
