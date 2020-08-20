package xyz.acproject.danmuji.returnJson;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

public class WsPackage implements Serializable,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4807973278850564054L;
	private static WsPackage wsPackage = new WsPackage();
	private String cmd;
	private Short status;
	private Object result;
	
	
	public WsPackage() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public static WsPackage getWsPackage() {
		try {
			return (WsPackage) wsPackage.clone();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new WsPackage();
	}
	public static WsPackage getWsPackage(String cmd,Short status,Object result) {
		try {
			WsPackage ws = (WsPackage) wsPackage.clone();
			ws.setCmd(cmd);
			ws.setStatus(status);
			ws.setResult(result);
			return ws;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new WsPackage();
	}
	public static String toJson(String cmd,Short status,Object result) {
		try {
			WsPackage ws = (WsPackage) wsPackage.clone();
			ws.setCmd(cmd);
			ws.setStatus(status);
			ws.setResult(result);
			return FastJsonUtils.toJson(ws);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "";
	}
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
