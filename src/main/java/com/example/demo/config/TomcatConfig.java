package com.example.demo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class TomcatConfig {
    /**
     * 解决地址栏有特殊付号{}
     * @return
     */
    @Bean
    public TomcatServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((Connector connector) -> {
            connector.setProperty("relaxedPathChars", "\"#<>[\\]^`{|}");
            connector.setProperty("relaxedQueryChars", "\"#<>[\\]^`{|}");
        });
        return factory;
    }
}
