package xyz.acproject.danmuji.entity.room_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RoomInit
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:13
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInit {
	private Long room_id;
	private Integer short_id;
	private Long uid;
	private Short need_p2p;
	private Boolean is_hidden;
	private Boolean is_portrait;
	//0为不直播  1为直播中 2为轮播
	private Short live_status;
	private Short hidden_till;
	private Short lock_till;
	private Boolean encrypted;
	private Boolean pwd_verified;
	private Long live_time;
	private Short room_shield;
	private Short is_sp;
	private Short special_type;

	
}
