package xyz.acproject.danmuji.entity.heart;


public class XData {
	private long[] id;
	private String device;
	private Long ets;
	private String benchmark;
	private Short time;
	private Long ts;
	private String ua ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";
	private short[] secret_rule;
	private Boolean error;
	public XData() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public XData(long[] id, String device, Long ets, String benchmark, Short time, Long ts, short[] secret_rule, Boolean error) {
		super();
		this.id = id;
		this.device = device;
		this.ets = ets;
		this.benchmark = benchmark;
		this.time = time;
		this.ts = ts;
		this.secret_rule = secret_rule;
		this.error = error;
	}

	public long[] getId() {
		return id;
	}
	public void setId(long[] id) {
		this.id = id;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Long getEts() {
		return ets;
	}
	public void setEts(Long ets) {
		this.ets = ets;
	}
	public String getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}
	public Short getTime() {
		return time;
	}
	public void setTime(Short time) {
		this.time = time;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public short[] getSecret_rule() {
		return secret_rule;
	}
	public void setSecret_rule(short[] secret_rule) {
		this.secret_rule = secret_rule;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
}
