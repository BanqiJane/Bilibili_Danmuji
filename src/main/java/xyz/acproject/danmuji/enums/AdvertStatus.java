package xyz.acproject.danmuji.enums;

/**
 * @ClassName AdvertStatus
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:05
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
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
