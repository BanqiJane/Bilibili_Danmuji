package xyz.acproject.danmuji.entity;

import java.io.Serializable;
import java.util.List;

public class Conf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4877957980247956836L;
	private String host;
	private List<HostServer> host_server_list;
	private Integer max_delay;
	private Integer port;
	private Integer refresh_rate;
	private Integer refresh_row_factor;
	private List<HostServerIp> server_list;
	private String token;
	public Conf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public Conf(String host, List<HostServer> host_server_list, Integer max_delay, Integer port, Integer refresh_rate,
			Integer refresh_row_factor, List<HostServerIp> server_list, String token) {
		super();
		this.host = host;
		this.host_server_list = host_server_list;
		this.max_delay = max_delay;
		this.port = port;
		this.refresh_rate = refresh_rate;
		this.refresh_row_factor = refresh_row_factor;
		this.server_list = server_list;
		this.token = token;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public List<HostServer> getHost_server_list() {
		return host_server_list;
	}
	public void setHost_server_list(List<HostServer> host_server_list) {
		this.host_server_list = host_server_list;
	}
	public Integer getMax_delay() {
		return max_delay;
	}
	public void setMax_delay(Integer max_delay) {
		this.max_delay = max_delay;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getRefresh_rate() {
		return refresh_rate;
	}
	public void setRefresh_rate(Integer refresh_rate) {
		this.refresh_rate = refresh_rate;
	}
	public Integer getRefresh_row_factor() {
		return refresh_row_factor;
	}
	public void setRefresh_row_factor(Integer refresh_row_factor) {
		this.refresh_row_factor = refresh_row_factor;
	}
	public List<HostServerIp> getServer_list() {
		return server_list;
	}
	public void setServer_list(List<HostServerIp> server_list) {
		this.server_list = server_list;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Conf [host=" + host + ", host_server_list=" + host_server_list + ", max_delay=" + max_delay + ", port="
				+ port + ", refresh_rate=" + refresh_rate + ", refresh_row_factor=" + refresh_row_factor
				+ ", server_list=" + server_list + ", token=" + token + "]";
	}
	
	
}
