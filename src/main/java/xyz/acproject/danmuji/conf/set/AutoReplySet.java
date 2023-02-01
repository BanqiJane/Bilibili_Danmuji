package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

import java.io.Serializable;
import java.util.HashSet;

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
	
}
