package com.zhangzz.controller;

import com.zhangzz.pojo.Users;
import com.zhangzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangzz
 * @date 2020/2/7 17:03
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public Object getUserInfo(String id) {
        Users user = userService.getUserInfo(id);
        return user;
    }

}
