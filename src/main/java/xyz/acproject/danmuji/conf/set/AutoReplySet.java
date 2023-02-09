package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @ClassName AutoReplySet
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:02
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoReplySet extends OpenSetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 562887876061232840L;
	/**
	* 是否独特
	*/
	@JSONField(name = "is_accurate")
	private boolean is_accurate = false;
	/**
	* 关键字集合
	*/
	private HashSet<String> keywords;
	/**
	* 屏蔽字集合
	*/
	private HashSet<String> shields;
	/**
	* 发送弹幕体
	*/
	private String reply;


	public HashSet<String> getKeywords() {
		if(keywords!=null) {
			return keywords.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
		}
		return keywords;
	}

	public HashSet<String> getShields() {
		if(shields!=null) {
			return shields.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
		}
		return shields;
	}
}
