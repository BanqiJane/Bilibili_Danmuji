package xyz.acproject.danmuji.entity.server_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Conf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:39
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4877957980247956836L;
	private Short business_id;
	private String group;
	private List<HostServer> host_list;
	private Short max_delay;
	private Short refresh_rate;
	private Short refresh_row_factor;
	private String token;
	
	
}
