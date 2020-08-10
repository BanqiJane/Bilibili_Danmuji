package xyz.acproject.danmuji.entity.server_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName HostServerIp
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:50
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HostServerIp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5416269632134092211L;
	private String Host;
	private Integer port;
	public HostServerIp() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public HostServerIp(String host, Integer port) {
		super();
		Host = host;
		this.port = port;
	}
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "HostServerIp [Host=" + Host + ", port=" + port + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new HostServerIp(Host,port));
	}
}
