package com.tradeshift.thirdparty.samples.springboot.config;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletContainerConfig {

    @Bean
    public EmbeddedServletContainerCustomizer customizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    customizeJetty((JettyEmbeddedServletContainerFactory) container);
                }
            }

            /**
             * Increase default request and response header size limit for embedded jetty servlet container
             *
             * @param jetty
             */
            private void customizeJetty(JettyEmbeddedServletContainerFactory jetty) {
                jetty.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        for (Connector connector : server.getConnectors()) {
                            if (connector instanceof ServerConnector) {
                                HttpConnectionFactory connectionFactory = ((ServerConnector) connector)
                                        .getConnectionFactory(HttpConnectionFactory.class);
                                connectionFactory.getHttpConfiguration()
                                        .setRequestHeaderSize(16 * 1024);
                                connectionFactory.getHttpConfiguration()
                                        .setResponseHeaderSize(16 * 1024);
                            }
                        }
                    }
                });
            }
        };

    }
}
