package com.zhangzz.service;

import com.zhangzz.pojo.Users;

/**
 * @author zhangzz
 * @date 2020/2/7 21:36
 */
public interface UserService {

    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return 用户对象
     */
    Users getUserInfo(String id);
}
