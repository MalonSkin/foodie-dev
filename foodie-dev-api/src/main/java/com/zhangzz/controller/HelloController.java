package com.zhangzz.controller;

import com.zhangzz.mapper.UsersMapper;
import com.zhangzz.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangzz
 * @date 2020/2/7 17:03
 */
@RestController
public class HelloController {

    @Autowired
    private UsersMapper usersMapper;

    @GetMapping("/hello")
    public Object hello() {
        List<Users> users = usersMapper.selectList(null);
        users.forEach(System.out::println);
        return "用户数量为：" + users.size();
    }

}
