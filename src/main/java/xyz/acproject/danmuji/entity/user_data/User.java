package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:39
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4638128918041411710L;
	private Long uid;
	private String uname;
	private String face;
	private Long silver;
	private Integer gold;
	private Integer achieve;
	private Short vip;
	private Short svip;
	private Short user_level;
	private Short user_next_level;
	private Long user_Integerimacy;
	private Long user_next_Integerimacy;
	private String user_level_rank;
	private Short user_charged;
	private Integer billCoin;
	
	
}
