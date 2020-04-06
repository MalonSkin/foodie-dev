package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzz.pojo.OrderStatus;
import com.zhangzz.pojo.Orders;
import com.zhangzz.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 */
@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 查询用户订单
     * @param page
     * @param map
     * @return
     */
    public IPage<MyOrdersVO> queryMyOrders(Page<?> page, @Param("paramsMap") Map<String, Object> map);

    /**
     * 查询订单状态数量
     * @param map
     * @return
     */
    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询用户订单动向
     * @param page
     * @param map
     * @return
     */
    public IPage<OrderStatus> getMyOrderTrend(Page<?> page, @Param("paramsMap") Map<String, Object> map);

}