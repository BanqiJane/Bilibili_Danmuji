package xyz.acproject.danmuji.entity.server_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName HostServer
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:43
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostServer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8555167206959414211L;
	private String host;
	private Integer port;
	private Integer ws_port;
	private Integer wss_port;

}
