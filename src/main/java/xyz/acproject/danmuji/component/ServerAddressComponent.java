package xyz.acproject.danmuji.component;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.http.HttpOtherData;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerAddressComponent implements ApplicationListener<WebServerInitializedEvent>{
	private int serverPort;
	
	
	public int getPort() {
		return this.serverPort;
	}
	public String getAddress() {
		InetAddress address = null;
		String addressStr = "";
		try {
			address=InetAddress.getLocalHost();
			addressStr = address.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			addressStr = "获取失败";
		}
		return "http://"+ addressStr +":"+this.serverPort;
	}

	public String getLocalAddress() {
		return "http://localhost:" +this.serverPort;
	}
	/**
	 * @return
	 */
	public String getRemoteAddress() {
		String ip = HttpOtherData.httpGetIp();
		if(PublicDataConf.centerSetConf.getPrivacy().is_open()){
			ip = "隐私模式禁止获取对公ip";
		}
		return "http://"+ ip +":"+this.serverPort;
	}
	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		// TODO 自动生成的方法存根
		this.serverPort = event.getWebServer().getPort();
	}
	
}
