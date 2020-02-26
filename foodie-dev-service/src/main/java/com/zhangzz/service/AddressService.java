package com.zhangzz.service;

import com.zhangzz.pojo.UserAddress;
import com.zhangzz.pojo.bo.AddressBO;

import java.util.List;

/**
 * 用户收货地址 服务层接口
 * @author zhangzz
 * @date 2020/2/26 20:19
 */
public interface AddressService {

    /**
     * 根据用户id查询用户的收货地址列表
     * @param userId 用户id
     * @return 用户的收货地址列表
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO 用户新增或修改地址的BO
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO 用户新增或修改地址的BO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     * @param userId 用户id
     * @param addressId 地址id
     */
    public void deleteUserAddress(String userId, String addressId);

}
