package xyz.acproject.danmuji.controller;


import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

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
