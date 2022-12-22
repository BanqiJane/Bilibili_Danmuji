package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AutoReplySet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 562887876061232840L;
	/**
	* 是否开启
	*/
	@JSONField(name = "is_open")
	private boolean is_open = false;
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
