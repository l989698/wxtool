package com.tool.wxtoolproject;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = "com.tool.wxtoolproject.api.user.dao")
@SpringBootApplication
public class WxToolProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WxToolProjectApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        System.out.println("项目启动成功! 访问地址://localhost:" + environment.getProperty("local.server.port"));
    }

}
