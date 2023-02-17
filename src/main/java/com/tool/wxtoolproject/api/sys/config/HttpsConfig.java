package com.tool.wxtoolproject.api.sys.config;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 采用Undertow作为服务器,支持Https服务配置
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/8/15 18:59
 * @since jdk1.8
 */
@Configuration
public class HttpsConfig {


    // 如果没有使用默认值80
    @Value("${http.port:80}")
    Integer httpPort;

    // 正常启用的https端口 如443
    @Value("${server.port}")
    Integer httpsPort;


    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        // Connector监听的http的端口号
        connector.setPort(httpPort);
        connector.setSecure(true);
        // 监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }
}
