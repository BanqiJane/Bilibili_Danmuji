package xyz.acproject.danmuji.entity.danmu_data;

import java.io.Serializable;

import xyz.acproject.danmuji.utils.FastJsonUtils;

/**
 * @ClassName BlockMessage
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:22:16
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class BlockMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1958790578528421482L;
	//用户uid
	private Long uid;
	//用户名称
	private String uname;
	//谁封禁的  1房管 2主播
	private Short operator;
	public BlockMessage() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public BlockMessage(Long uid, String uname, Short operator) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.operator = operator;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Short getOperator() {
		return operator;
	}
	public void setOperator(Short operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "BlockMessage [uid=" + uid + ", uname=" + uname + ", operator=" + operator + "]";
	}
	public String toJson() {
		return FastJsonUtils.toJson(new BlockMessage(uid,uname,operator));
	}
}
