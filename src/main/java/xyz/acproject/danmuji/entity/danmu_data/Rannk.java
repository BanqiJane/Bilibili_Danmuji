package xyz.acproject.danmuji.entity.danmu_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Rannk
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:41
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rannk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2809400486616994730L;
	private Long roomid;
	private String rank_desc;
	private String color;
	private String h5_url;
	private String web_url;
	private Long timestamp;
}
