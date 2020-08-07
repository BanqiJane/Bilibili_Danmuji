package xyz.acproject.danmuji.service;

import xyz.acproject.danmuji.conf.CenterSetConf;

public interface SetService {
	void init();
	void changeSet(CenterSetConf centerSetConf);
	void connectSet(CenterSetConf centerSetConf);
	void holdSet(CenterSetConf centerSetConf);
	void quit();
}
