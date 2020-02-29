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

}