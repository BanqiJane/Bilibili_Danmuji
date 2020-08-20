package xyz.acproject.danmuji.entity.view;

import java.io.Serializable;

public class RoomGift implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6372110568521825130L;
	private Integer id;
	private String name;
	//经工具过滤 0为银 1为金
	private Short coin_type;
	private String webp;
	
	
	public RoomGift() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	
	public RoomGift(Integer id, String name, Short coin_type, String webp) {
		super();
		this.id = id;
		this.name = name;
		this.coin_type = coin_type;
		this.webp = webp;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getCoin_type() {
		return coin_type;
	}
	public void setCoin_type(Short coin_type) {
		this.coin_type = coin_type;
	}
	public String getWebp() {
		return webp;
	}
	public void setWebp(String webp) {
		this.webp = webp;
	}
	@Override
	public String toString() {
		return "RoomGift [id=" + id + ", name=" + name + ", coin_type=" + coin_type + ", webp=" + webp + "]";
	}
	

}
