package com.zhangzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot启动类
 * @author zhangzz
 * @date 2020/2/7 16:45
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zhangzz", "org.n3r.idworker"}) // 扫描所有包以及相关的组件包
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
