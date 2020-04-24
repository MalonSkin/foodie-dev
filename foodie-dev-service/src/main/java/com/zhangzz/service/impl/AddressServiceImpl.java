package com.zhangzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangzz.enums.YesOrNo;
import com.zhangzz.mapper.UserAddressMapper;
import com.zhangzz.pojo.UserAddress;
import com.zhangzz.pojo.bo.AddressBO;
import com.zhangzz.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhangzz
 * @date 2020/2/26 20:21
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryAll(String userId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAddress::getUserId, userId);
        return userAddressMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault = 0;
        // 判断当前用户是否存在地址，如果没有，则新增为默认地址
        List<UserAddress> list = this.queryAll(addressBO.getUserId());
        if (CollectionUtils.isEmpty(list)) {
            isDefault = 1;
        }
        // 保存到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);
        newAddress.setId(sid.nextShort());
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(newAddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateById(pendingAddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteUserAddress(String userId, String addressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAddress::getId, addressId).eq(UserAddress::getUserId, userId);
        userAddressMapper.delete(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public UserAddress queryUserAddress(String userId, String addressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAddress::getId, addressId).eq(UserAddress::getUserId, userId);
        return userAddressMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1.查找默认地址，设置为不默认
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getIsDefault, YesOrNo.YES.type);
        UserAddress ua = userAddressMapper.selectOne(queryWrapper);
        ua.setIsDefault(YesOrNo.NO.type);
        userAddressMapper.updateById(ua);
        // 2.根据地址ID修改默认地址
        queryWrapper.clear();
        queryWrapper.lambda()
                .eq(UserAddress::getId, addressId)
                .eq(UserAddress::getUserId, userId);
        UserAddress userAddress = userAddressMapper.selectOne(queryWrapper);
        userAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateById(userAddress);
    }
}
