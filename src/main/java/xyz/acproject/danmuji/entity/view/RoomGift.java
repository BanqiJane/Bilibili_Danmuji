package xyz.acproject.danmuji.entity.view;

import com.alibaba.fastjson.annotation.JSONField;
import xyz.acproject.danmuji.serialize.CoinTypeDeserializer;

import java.io.Serializable;

public class RoomGift implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6372110568521825130L;
	private Integer id;
	private String name;
	private Integer price;
	private Integer type;
	private Integer effect;
	//类型不同 不给他反序列化了
	@JSONField(deserializeUsing = CoinTypeDeserializer.class)
	private Short coin_type;
	private Integer stay_time;
	private String desc;
	private String rule;
	private String rights;
	private String img_basic;
	private String img_dynamic;
	private String frame_animation;
	private String gif;
	private String webp;
	private Integer limit_Integererval;
	private Integer gift_type;
	private Integer combo_resources_id;
	private Integer max_send_limit;
	private Integer goods_id;
	private Integer effect_id;
	
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getEffect() {
		return effect;
	}

	public void setEffect(Integer effect) {
		this.effect = effect;
	}

	public Short getCoin_type() {
		return coin_type;
	}

	public void setCoin_type(Short coin_type) {
		this.coin_type = coin_type;
	}

	public Integer getStay_time() {
		return stay_time;
	}

	public void setStay_time(Integer stay_time) {
		this.stay_time = stay_time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getImg_basic() {
		return img_basic;
	}

	public void setImg_basic(String img_basic) {
		this.img_basic = img_basic;
	}

	public String getImg_dynamic() {
		return img_dynamic;
	}

	public void setImg_dynamic(String img_dynamic) {
		this.img_dynamic = img_dynamic;
	}

	public String getFrame_animation() {
		return frame_animation;
	}

	public void setFrame_animation(String frame_animation) {
		this.frame_animation = frame_animation;
	}

	public String getGif() {
		return gif;
	}

	public void setGif(String gif) {
		this.gif = gif;
	}

	public String getWebp() {
		return webp;
	}

	public void setWebp(String webp) {
		this.webp = webp;
	}

	public Integer getLimit_Integererval() {
		return limit_Integererval;
	}

	public void setLimit_Integererval(Integer limit_Integererval) {
		this.limit_Integererval = limit_Integererval;
	}

	public Integer getGift_type() {
		return gift_type;
	}

	public void setGift_type(Integer gift_type) {
		this.gift_type = gift_type;
	}

	public Integer getCombo_resources_id() {
		return combo_resources_id;
	}

	public void setCombo_resources_id(Integer combo_resources_id) {
		this.combo_resources_id = combo_resources_id;
	}

	public Integer getMax_send_limit() {
		return max_send_limit;
	}

	public void setMax_send_limit(Integer max_send_limit) {
		this.max_send_limit = max_send_limit;
	}

	public Integer getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}

	public Integer getEffect_id() {
		return effect_id;
	}

	public void setEffect_id(Integer effect_id) {
		this.effect_id = effect_id;
	}

	@Override
	public String toString() {
		return "RoomGift [id=" + id + ", name=" + name + ", coin_type=" + coin_type + ", webp=" + webp + "]";
	}

}
