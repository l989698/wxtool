package com.tool.wxtoolproject.api.sys.config;

import com.tool.wxtoolproject.api.sys.interceptors.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 拦截器注入
     *
     * @return
     */
    @Bean
    public AuthorizationInterceptor getAccessInterceptor() {
        System.out.println("注入了AuthorizationInterceptor");
        return new AuthorizationInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAccessInterceptor())
                .addPathPatterns("/**");

    }
}
