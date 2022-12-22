package xyz.acproject.danmuji.entity.danmu_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName BlockMessage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:16
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1958790578528421482L;
	//用户uid
	private Long uid;
	//用户名称
	private String uname;
	//谁封禁的  1房管 2主播
	private Short operator;

}
