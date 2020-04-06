package com.zhangzz.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.zhangzz.enums.OrderStatusEnum;
import com.zhangzz.enums.YesOrNo;
import com.zhangzz.mapper.OrderStatusMapper;
import com.zhangzz.mapper.OrdersMapper;
import com.zhangzz.pojo.OrderStatus;
import com.zhangzz.pojo.Orders;
import com.zhangzz.pojo.vo.ItemCommentVO;
import com.zhangzz.pojo.vo.MyOrdersVO;
import com.zhangzz.pojo.vo.OrderStatusCountsVO;
import com.zhangzz.service.center.MyOrdersService;
import com.zhangzz.utils.PagedGridResult;
import io.swagger.annotations.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzz
 * @date 2020/4/6 上午12:07
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("userId", userId);
        if (orderStatus != null) {
            paramsMap.put("orderStatus", orderStatus);
        }
        Page<MyOrdersVO> queryPage = new Page<>(page, pageSize);
        IPage<MyOrdersVO> iPage = ordersMapper.queryMyOrders(queryPage, paramsMap);
        return new PagedGridResult().fromPage(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());
        UpdateWrapper<OrderStatus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(OrderStatus::getOrderId, orderId)
                .eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.update(updateOrder, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Orders queryMyOrder(String userId, String orderId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Orders::getId, orderId)
                .eq(Orders::getUserId, userId)
                .eq(Orders::getIsDelete, YesOrNo.NO.type);
        return ordersMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());
        UpdateWrapper<OrderStatus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(OrderStatus::getOrderId, orderId)
                .eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAIT_RECEIVE.type);
        int result = orderStatusMapper.update(updateOrder, updateWrapper);
        return result == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(Orders::getId, orderId)
                .eq(Orders::getUserId, userId);
        int result = ordersMapper.update(updateOrder, updateWrapper);
        return result == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapper.getMyOrderStatusCounts(map);

        return new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts, waitReceiveCounts, waitCommentCounts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult getMyOrderTrend(String userId, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("userId", userId);
        Page<OrderStatus> queryPage = new Page<>(page, pageSize);
        IPage<OrderStatus> iPage = ordersMapper.getMyOrderTrend(queryPage, paramsMap);
        return new PagedGridResult().fromPage(iPage);
    }
}
