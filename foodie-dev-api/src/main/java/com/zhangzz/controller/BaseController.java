package com.zhangzz.controller;

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

    /** 默认页码为 1 */
    public static final Long DEFAULT_PAGE = 1L;
    /** 默认每页显示条数为 20 */
    public static final Long DEFAULT_PAGE_SIZE = 20L;
    /** 商品评价每页显示条数为 10 */
    public static final Long COMMENT_PAGE_SIZE = 10L;

    /** 购物车 cookie */
    public static final String FOODIE_SHOPCART = "shopcart";

    /** 支付中心的调用地址 */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /** 微信支付成功 -> 支付中心 -> 天天吃货平台 -> 回调通知的URL */
    public static final String PAY_RETURN_URL = "http://6vze9h.natappfree.cc/orders/notifyMerchantOrderPaid";

}
