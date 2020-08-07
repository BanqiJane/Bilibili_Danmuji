package xyz.acproject.danmuji.service;

public interface ClientService {
	void startConnService(long roomid) throws Exception;
	void reConnService() throws Exception;
	boolean closeConnService();
}
