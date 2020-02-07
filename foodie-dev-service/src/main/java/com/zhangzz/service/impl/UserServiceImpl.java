package com.zhangzz.service.impl;

import com.zhangzz.mapper.UsersMapper;
import com.zhangzz.pojo.Users;
import com.zhangzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangzz
 * @date 2020/2/7 21:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    @Override
    public Users getUserInfo(String id) {
        return usersMapper.selectById(id);
    }

}
