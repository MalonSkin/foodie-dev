package com.zhangzz.service.center;

import com.zhangzz.pojo.OrderItems;
import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.center.CenterUserBO;
import com.zhangzz.pojo.bo.center.OrderItemsCommentBO;
import com.zhangzz.utils.PagedGridResult;

import java.util.List;

/**
 * @author zhangzz
 * @date 2020/2/7 21:36
 */
public interface MyCommentsService {

    /**
     * 根据订单ID查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId, Long page, Long pageSize);
}
