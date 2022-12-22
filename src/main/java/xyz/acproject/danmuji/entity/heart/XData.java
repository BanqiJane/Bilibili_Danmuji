package xyz.acproject.danmuji.entity.heart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class XData {
	private long[] id;
	private String device;
	private Long ets;
	private String benchmark;
	private Short time;
	private Long ts;
	private String ua ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";
	private short[] secret_rule;

	//以下是额外参数
	private Boolean error;
	private long startTime;

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

	public XData startTime(long startTime){
		this.startTime = startTime;
		return this;
	}

}
