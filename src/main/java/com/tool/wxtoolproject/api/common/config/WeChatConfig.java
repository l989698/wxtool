package com.tool.wxtoolproject.api.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "we-chat")
public class WeChatConfig {
    private String sessionHost;
    private String appId;
    private String secret;
    private String grantType;
}
