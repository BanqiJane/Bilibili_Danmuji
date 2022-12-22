package xyz.acproject.danmuji.entity.auto_reply;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AutoReply
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:59
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
public class AutoReply implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static AutoReply autoReply = new AutoReply();
	private static final long serialVersionUID = -4026920122195895200L;
	private Long uid;
	private String name;
	private String barrage;
	
	public AutoReply() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public static AutoReply getAutoReply() {
		try {
			return (AutoReply) autoReply.clone();
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new AutoReply();
	}
	
	public static AutoReply getAutoReply(Long uid,String name,String barrage) {
		try {
			AutoReply ar = (AutoReply) autoReply.clone();
			ar.setUid(uid);
			ar.setName(name);
			ar.setBarrage(barrage);
			return ar;
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new AutoReply();
	}

	
}
