package xyz.acproject.danmuji.component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import xyz.acproject.danmuji.http.HttpOtherData;

@Component
public class ServerAddressComponent implements ApplicationListener<WebServerInitializedEvent>{
	private int serverPort;
	public String getAddress() {
		InetAddress address = null;
		try {
			address=InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "http://"+ address.getHostAddress() +":"+this.serverPort;
	}
	/**
	 * @return
	 */
	public String getRemoteAddress() {
		return "http://"+ HttpOtherData.httpGetIp() +":"+this.serverPort;
	}
	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		// TODO 自动生成的方法存根
		this.serverPort = event.getWebServer().getPort();
	}
	
}
