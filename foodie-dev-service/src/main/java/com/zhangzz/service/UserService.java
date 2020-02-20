package com.zhangzz.service;

import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.UserBO;

/**
 * @author zhangzz
 * @date 2020/2/7 21:36
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 存在：true；不存在：false
     */
    boolean usernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO 用户对象BO
     * @return 用户对象
     */
    Users createUser(UserBO userBO);

    /**
     * 查询用户用于登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    Users queryUserForLogin(String username, String password);
}
