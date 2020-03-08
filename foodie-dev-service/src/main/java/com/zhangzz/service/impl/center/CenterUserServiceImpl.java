package com.zhangzz.service.impl.center;

import com.zhangzz.mapper.UsersMapper;
import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.center.CenterUserBO;
import com.zhangzz.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangzz
 * @date 2020/3/8 22:22
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectById(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateById(updateUser);
        return queryUserInfo(userId);
    }
}
