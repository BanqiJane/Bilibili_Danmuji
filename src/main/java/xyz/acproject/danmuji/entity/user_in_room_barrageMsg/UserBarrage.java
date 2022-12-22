package xyz.acproject.danmuji.entity.user_in_room_barrageMsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;

/**
 * @ClassName UserBarrage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:24
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBarrage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 985041146105605410L;
	private Short mode;
	private Long color;
	private Short length = 20;
	private Long room_id = PublicDataConf.ROOMID;
}
