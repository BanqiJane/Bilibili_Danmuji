package xyz.acproject.danmuji.service;

/**
 * @ClassName ClientService
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:29:30
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public interface ClientService {
	void startConnService(long roomid) throws Exception;
	void reConnService() throws Exception;
	boolean closeConnService();
}
