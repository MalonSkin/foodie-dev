package com.zhangzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * springboot启动类
 * @author zhangzz
 * @date 2020/2/7 16:45
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = {"com.zhangzz", "org.n3r.idworker"})
//@EnableRedisHttpSession // 开启使用Redis作为SpringSession
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
