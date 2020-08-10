package xyz.acproject.danmuji.entity.server_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName HostServer
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:43
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HostServer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8555167206959414211L;
	private String host;
	private Integer port;
	private Integer ws_port;
	private Integer wss_port;
	public HostServer() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public HostServer(String host, Integer port, Integer ws_port, Integer wss_port) {
		super();
		this.host = host;
		this.port = port;
		this.ws_port = ws_port;
		this.wss_port = wss_port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getWs_port() {
		return ws_port;
	}
	public void setWs_port(Integer ws_port) {
		this.ws_port = ws_port;
	}
	public Integer getWss_port() {
		return wss_port;
	}
	public void setWss_port(Integer wss_port) {
		this.wss_port = wss_port;
	}
	@Override
	public String toString() {
		return "HostServer [host=" + host + ", port=" + port + ", ws_port=" + ws_port + ", wss_port=" + wss_port + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new HostServer(host,port,ws_port,wss_port));
	}
}
