package xyz.acproject.danmuji.entity.room_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CheckTx
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:03
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTx implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6849509148214688485L;
	private Long room_id;
	private String gift_name;
	private Short time;
	

	
}
