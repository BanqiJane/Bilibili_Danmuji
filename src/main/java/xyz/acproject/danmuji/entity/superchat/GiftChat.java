package xyz.acproject.danmuji.entity.superchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName GiftChat
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:26:57
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftChat implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1794734764847323793L;
	private Integer num;
	private Integer gift_id;
	private String gift_name;

}
