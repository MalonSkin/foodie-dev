package com.zhangzz.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.zhangzz.enums.YesOrNo;
import com.zhangzz.mapper.ItemsCommentsMapper;
import com.zhangzz.mapper.OrderItemsMapper;
import com.zhangzz.mapper.OrderStatusMapper;
import com.zhangzz.mapper.OrdersMapper;
import com.zhangzz.pojo.OrderItems;
import com.zhangzz.pojo.OrderStatus;
import com.zhangzz.pojo.Orders;
import com.zhangzz.pojo.bo.center.OrderItemsCommentBO;
import com.zhangzz.pojo.vo.MyCommentVO;
import com.zhangzz.pojo.vo.MyOrdersVO;
import com.zhangzz.service.center.MyCommentsService;
import com.zhangzz.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 * @date 2020/4/6 下午12:27
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        QueryWrapper<OrderItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(OrderItems::getOrderId, orderId);
        return orderItemsMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1.保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapper.saveComments(map);
        // 2.修改订单表为已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateById(order);
        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateById(orderStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyComments(String userId, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("userId", userId);
        Page<MyCommentVO> queryPage = new Page<>(page, pageSize);
        IPage<MyCommentVO> iPage = itemsCommentsMapper.queryMyComments(queryPage, paramsMap);
        return new PagedGridResult().fromPage(iPage);
    }
}
