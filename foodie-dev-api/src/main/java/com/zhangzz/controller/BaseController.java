package com.zhangzz.controller;

import com.zhangzz.pojo.Orders;
import com.zhangzz.service.center.MyOrdersService;
import com.zhangzz.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangzz
 * @date 2020/2/23 19:00
 */
@ApiIgnore
@Controller
public class BaseController {

    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;
    @Autowired
    public MyOrdersService myOrdersService;

    /** 默认页码为 1 */
    public static final Long DEFAULT_PAGE = 1L;
    /** 默认每页显示条数为 20 */
    public static final Long DEFAULT_PAGE_SIZE = 20L;
    /** 商品评价每页显示条数为 10 */
    public static final Long COMMON_PAGE_SIZE = 10L;

    /** 购物车 cookie */
    public static final String FOODIE_SHOPCART = "shopcart";

    /** 用户token */
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /** 支付中心的调用地址 */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /** 微信支付成功 -> 支付中心 -> 天天吃货平台 -> 回调通知的URL */
    public static final String PAY_RETURN_URL = "http://6vze9h.natappfree.cc/orders/notifyMerchantOrderPaid";

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @param userId
     * @param orderId
     * @return
     */
    protected IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }
        return IMOOCJSONResult.ok(order);
    }

}
