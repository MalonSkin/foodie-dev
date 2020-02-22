package com.zhangzz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangzz.pojo.OrderItems;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangzz
 */
@Mapper
@Repository
public interface OrderItemsMapper extends BaseMapper<OrderItems> {

}