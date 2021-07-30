package xyz.acproject.danmuji.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName DanmuWebsocket
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:21:44
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Controller
@ServerEndpoint("/danmu/sub")
public class DanmuWebsocket {
	private Logger LOGGER = LogManager.getLogger(DanmuWebsocket.class);
	private static CopyOnWriteArraySet<DanmuWebsocket> webSocketServers = new CopyOnWriteArraySet<>();
	private Session session;
	
	@OnOpen
	public void onOpen(Session session) {
		this.session=session;
		webSocketServers.add(this);
	}
	
	@OnClose
	public void onClose() {
		webSocketServers.remove(this);
	}
	
	@OnMessage
	public void onMessage(String message) throws IOException {
		//反向发送 23333333333 (滑稽
		for(DanmuWebsocket danmuWebsocket:webSocketServers) {
			danmuWebsocket.session.getBasicRemote().sendText(message);
		}
	}
	
	
	@OnError
	public void onError(Session session,Throwable error) {
		LOGGER.error(error);
	}

	public void sendMessage(String message) throws IOException {
		for(DanmuWebsocket danmuWebsocket:webSocketServers) {
			synchronized (danmuWebsocket.session) {
				danmuWebsocket.session.getBasicRemote().sendText(message);
			}
			
		}
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
