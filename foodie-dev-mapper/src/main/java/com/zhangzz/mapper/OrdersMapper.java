package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @param map
     * @param page
     * @return
     */
    public IPage<MyOrdersVO> queryMyOrders(Page<?> page, @Param("paramsMap") Map<String, Object> map);

}