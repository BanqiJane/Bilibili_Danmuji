package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

import java.io.Serializable;

/**
 * @ClassName ThankGiftRuleSet
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:19
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThankGiftRuleSet extends OpenSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4838306525238441431L;
	/**
	* 礼物名称
	*/
	@JSONField(name = "gift_name")
	private String gift_name;
	//条件码 0数量  1银瓜子 2金瓜子
	private short status = 0;
	//对应的数量或者瓜子数
	private int num = 0;


	@Override
	public int hashCode() {
		return this.gift_name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		ThankGiftRuleSet thankGiftRuleSet = (ThankGiftRuleSet) obj;
		return this.gift_name.equals(thankGiftRuleSet.getGift_name());
	}
}
