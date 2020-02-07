package com.zhangzz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动类
 * @author zhangzz
 * @date 2020/2/7 16:45
 */
@MapperScan("com.zhangzz.mapper")
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
