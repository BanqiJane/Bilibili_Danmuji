package xyz.acproject.danmuji.conf.set;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.component.ThreadComponent;
import xyz.acproject.danmuji.conf.PublicDataConf;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @ClassName AutoReplySetConf
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:06
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoReplySetConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6387301110915854706L;
	//是否开启
	@JSONField(name = "is_open")
	private boolean is_open = false;
	//是否直播有效
	@JSONField(name = "is_live_open")
	private boolean is_live_open =false;
	//间隔时间
	private short time = 3;
	//自动回复子对象集合
	private HashSet<AutoReplySet> autoReplySets;


	//方法区

	public void start(ThreadComponent threadComponent){
		if(StringUtils.isBlank(PublicDataConf.USERCOOKIE)){
			return;
		}
		if (is_live_open) {
			if (PublicDataConf.lIVE_STATUS != 1) {
				threadComponent.closeAutoReplyThread();
			} else {
				if (is_open) {
					threadComponent.startAutoReplyThread(this);
				} else {
					threadComponent.setAutoReplyThread(this);
					threadComponent.closeAutoReplyThread();
				}
			}
		} else {
			if (is_open) {
				threadComponent.startAutoReplyThread(this);
			} else {
				threadComponent.setAutoReplyThread(this);
				threadComponent.closeAutoReplyThread();
			}
		}
	}
}
