package com.zhangzz.service;

import com.zhangzz.pojo.OrderStatus;
import com.zhangzz.pojo.bo.SubmitOrderBO;
import com.zhangzz.pojo.vo.MerchantOrdersVO;

/**
 * 订单服务层接口
 * @author zhangzz
 * @date 2020/2/22 12:21
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return 订单VO
     */
    public MerchantOrdersVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId     订单id
     * @param orderStatus 订单状态
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 根据订单id查询订单状态
     * @param orderId 订单id
     * @return 订单状态
     */
    public OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    public void closeOrder();
}
