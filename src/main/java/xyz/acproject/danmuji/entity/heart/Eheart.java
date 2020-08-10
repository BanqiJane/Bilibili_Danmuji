package xyz.acproject.danmuji.entity.heart;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @ClassName Eheart
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:47
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class Eheart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3856379095269205708L;
	private Short heartbeat_interval;
	private Short patch_status = 1;
	private String[] reason;
	private String secret_key;
	private Short[] secret_rule;
	private Long timestamp;
	public Eheart() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Eheart(Short heartbeat_interval, Short patch_status, String[] reason, String secret_key, Short[] secret_rule,
			Long timestamp) {
		super();
		this.heartbeat_interval = heartbeat_interval;
		this.patch_status = patch_status;
		this.reason = reason;
		this.secret_key = secret_key;
		this.secret_rule = secret_rule;
		this.timestamp = timestamp;
	}
	
	public Short getHeartbeat_interval() {
		return heartbeat_interval;
	}
	public void setHeartbeat_interval(Short heartbeat_interval) {
		this.heartbeat_interval = heartbeat_interval;
	}
	public Short getPatch_status() {
		return patch_status;
	}
	public void setPatch_status(Short patch_status) {
		this.patch_status = patch_status;
	}
	public String[] getReason() {
		return reason;
	}
	public void setReason(String[] reason) {
		this.reason = reason;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public Short[] getSecret_rule() {
		return secret_rule;
	}
	public void setSecret_rule(Short[] secret_rule) {
		this.secret_rule = secret_rule;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Eheart [heartbeat_interval=" + heartbeat_interval + ", patch_status=" + patch_status + ", reason="
				+ Arrays.toString(reason) + ", secret_key=" + secret_key + ", secret_rule="
				+ Arrays.toString(secret_rule) + ", timestamp=" + timestamp + "]";
	}
	
	
}
