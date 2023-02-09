package xyz.acproject.danmuji.entity.danmu_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.entity.superchat.MedalInfo;

import java.io.Serializable;

/**
 * @ClassName Interact
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:37
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8108196758728766627L;
	private Long uid;
	private String uname;
	private String uname_color;
	private Integer[] identities;
	//1欢迎 2关注
	private Short msg_type;
	private Long roomid;
	private Long timestamp;
	private Long score;

	private MedalInfo fans_medal;

	
}
