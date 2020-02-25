package com.zhangzz.controller;

import com.zhangzz.pojo.bo.ShopCartBO;
import com.zhangzz.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangzz
 * @date 2020/2/23 21:03
 */
@Api(value = "购物车接口", tags = {"购物车接口相关的Api"})
@RestController
@RequestMapping("/shopcart")
public class ShopCartController extends BaseController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId, @RequestBody ShopCartBO shopCartBO) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        System.out.println(shopCartBO);
        // TODO 前端用户在登录的情况下，添加商品到购物车，同时会在后端同步购物车到Redis缓存

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId, @RequestBody String itemSpecId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return IMOOCJSONResult.ok();
    }

}
