package com.zhangzz.service;

import com.zhangzz.pojo.bo.SubmitOrderBO;

/**
 * 订单服务层接口
 * @author zhangzz
 * @date 2020/2/22 12:21
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return 订单id
     */
    public String  createOrder(SubmitOrderBO submitOrderBO);
}
