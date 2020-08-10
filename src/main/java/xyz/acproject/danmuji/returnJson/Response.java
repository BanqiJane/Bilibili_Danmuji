package xyz.acproject.danmuji.returnJson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

;

/**
 * @ClassName Response
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:14
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 * @param <T>
 */
public class Response<T> {
	private String code;
	private String msg;
	private Object result;
	private Timestamp timestamp;
	public Response() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public Response(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public Response(String code, String msg,Timestamp timestamp) {
		super();
		this.code = code;
		this.msg = msg;
		this.timestamp=timestamp;
	}
	
	public Response(String code, String msg, Object result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	
	public Response(String code, String msg, Object result,Timestamp timestamp) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
		this.timestamp=timestamp;
	}
	
	public Response(ResponseCode code, Object result) {
		super();
		this.code = code.getCode();
		this.msg = code.getCnMsg();
		this.result = result;
	}
	
	public Response(ResponseCode code, Object result,Timestamp timestamp) {
		super();
		this.code = code.getCode();
		this.msg = code.getCnMsg();
		this.result = result;
		this.timestamp=timestamp;
	}
	public Response(ResponseCode code, String msg, Object result,Timestamp timestamp) {
		super();
		this.code = code.getCode();
		this.msg = code.getCnMsg();
		this.result = result;
		this.timestamp=timestamp;
	}
	@SuppressWarnings("rawtypes")
	public static Response success(Object result,HttpServletRequest request) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String times = dateFormat.format(calendar.getTime());
		Timestamp timestamp = Timestamp.valueOf(times.toString());
		return new Response(ResponseCode.normal,"",result,timestamp);
	}
	@SuppressWarnings("rawtypes")
	public static Response error(HttpServletRequest request) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String times = dateFormat.format(calendar.getTime());
		Timestamp timestamp = Timestamp.valueOf(times.toString());
		return new Response(ResponseCode.syserror,"",timestamp);
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", msg=" + msg + ", result=" + result + ", timestamp=" + timestamp +  "]";
	}

}
