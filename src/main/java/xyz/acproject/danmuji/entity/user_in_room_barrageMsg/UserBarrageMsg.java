package xyz.acproject.danmuji.entity.user_in_room_barrageMsg;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserBarrageMsg
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:30
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBarrageMsg {
	private Short bubble;
	private UserBarrage danmu;
	private String uname_color;

}
