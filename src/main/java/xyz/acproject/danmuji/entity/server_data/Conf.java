package xyz.acproject.danmuji.entity.server_data;

import java.io.Serializable;
import java.util.List;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName Conf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:23:39
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
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
	public Conf() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Conf(Short business_id, String group, List<HostServer> host_list, Short max_delay, Short refresh_rate,
			Short refresh_row_factor, String token) {
		super();
		this.business_id = business_id;
		this.group = group;
		this.host_list = host_list;
		this.max_delay = max_delay;
		this.refresh_rate = refresh_rate;
		this.refresh_row_factor = refresh_row_factor;
		this.token = token;
	}
	public Short getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(Short business_id) {
		this.business_id = business_id;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<HostServer> getHost_list() {
		return host_list;
	}
	public void setHost_list(List<HostServer> host_list) {
		this.host_list = host_list;
	}
	public Short getMax_delay() {
		return max_delay;
	}
	public void setMax_delay(Short max_delay) {
		this.max_delay = max_delay;
	}
	public Short getRefresh_rate() {
		return refresh_rate;
	}
	public void setRefresh_rate(Short refresh_rate) {
		this.refresh_rate = refresh_rate;
	}
	public Short getRefresh_row_factor() {
		return refresh_row_factor;
	}
	public void setRefresh_row_factor(Short refresh_row_factor) {
		this.refresh_row_factor = refresh_row_factor;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Conf [business_id=" + business_id + ", group=" + group + ", host_list=" + host_list
				+ ", max_delay=" + max_delay + ", refresh_rate=" + refresh_rate + ", refresh_row_factor="
				+ refresh_row_factor + ", token=" + token + "]";
	}
	
	public String toJson() {
		return FastJsonUtils.toJson(new Conf(business_id, group, host_list, max_delay, refresh_rate, refresh_row_factor, token));
	}
	
	
}
