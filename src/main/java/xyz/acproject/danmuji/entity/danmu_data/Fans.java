package xyz.acproject.danmuji.entity.danmu_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Fans
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:21
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -418643615266356797L;
	//房间id
	private Long roomid;
	//粉丝数
	private Long fans;
	//以英文为红色警告？？？ 暂且不知道是干什么用的
	private Short red_notice;

}
