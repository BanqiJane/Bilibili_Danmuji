package xyz.acproject.danmuji.entity.heart;

import java.io.Serializable;

public class SmallHeart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1815904721726060137L;
	private Short heartbeat_interval;
	private String secret_key;
	private short[] secret_rule;
	private Long timestamp;
	public SmallHeart() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public SmallHeart(Short heartbeat_interval, String secret_key, short[] secret_rule,
			Long timestamp) {
		super();
		this.heartbeat_interval = heartbeat_interval;
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
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public short[] getSecret_rule() {
		return secret_rule;
	}
	public void setSecret_rule(short[] secret_rule) {
		this.secret_rule = secret_rule;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
