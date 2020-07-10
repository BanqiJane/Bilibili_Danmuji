package xyz.acproject.danmuji.enums;

public enum AdvertStatus {
	
	//顺序
	deafult(0),
	//随机
	random(1);
	
	
	
	
	
	
	private int code;
	
	
	private AdvertStatus(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
