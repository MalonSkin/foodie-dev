package com.zhangzz;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * ES配置类
 * @author zhangzz
 * @date 2020/5/23 18:31
 */
@Configuration
public class ESConfig {

    /**
     * 解决netty引起的issue
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
