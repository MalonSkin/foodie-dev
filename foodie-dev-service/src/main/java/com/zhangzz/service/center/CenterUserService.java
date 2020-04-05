package com.zhangzz.service.center;

import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.center.CenterUserBO;

/**
 * @author zhangzz
 * @date 2020/2/7 21:36
 */
public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户对象
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId 用户id
     * @param centerUserBO
     * @return 用户对象
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像跟新
     * @param userId 用户id
     * @param faceUrl 头像地址
     * @return
     */
    Users updateUserFace(String userId, String faceUrl);

}
