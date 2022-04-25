package xyz.acproject.danmuji.service;

import xyz.acproject.danmuji.conf.CenterSetConf;

/**
 * @ClassName SetService
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:33
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public interface SetService {
	void init();

	void changeSet(CenterSetConf centerSetConf);
	void changeSet(CenterSetConf centerSetConf,boolean check);
	void connectSet(CenterSetConf centerSetConf);
	void holdSet(CenterSetConf centerSetConf);
	void quit();
}
