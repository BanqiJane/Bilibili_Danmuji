package xyz.acproject.danmuji.entity.server_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.utils.FastJsonUtils;

import java.io.Serializable;

/**
 * @ClassName HostServerIp
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:50
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostServerIp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5416269632134092211L;
	private String Host;
	private Integer port;
}
