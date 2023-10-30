package xyz.acproject.danmuji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Jane
 * @ClassName WebsockConfig
 * @Description TODO
 * @date 2021/8/11 23:59
 * @Copyright:2021
 */
@Configuration
public class WebsocketConfig {

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
//        return factory;
//    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
