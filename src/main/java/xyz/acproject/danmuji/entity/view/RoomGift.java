package xyz.acproject.danmuji.entity.view;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.serialize.CoinTypeDeserializer;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

	public RoomGift(Integer id, String name, Short coin_type, String webp) {
		super();
		this.id = id;
		this.name = name;
		this.coin_type = coin_type;
		this.webp = webp;
	}

	@Override
	public String toString() {
		return "RoomGift [id=" + id + ", name=" + name + ", coin_type=" + coin_type + ", webp=" + webp + "]";
	}

}
