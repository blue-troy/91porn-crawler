package com.bluetroy.crawler91.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-19
 * Time: 上午10:04
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
