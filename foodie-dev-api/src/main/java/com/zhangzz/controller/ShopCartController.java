package com.zhangzz.controller;

import com.google.common.collect.Lists;
import com.zhangzz.pojo.bo.ShopCartBO;
import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import com.zhangzz.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangzz
 * @date 2020/2/23 21:03
 */
@Api(value = "购物车接口", tags = {"购物车接口相关的Api"})
@RestController
@RequestMapping("/shopcart")
public class ShopCartController extends BaseController {

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId, @RequestBody ShopCartBO shopCartBO) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        // 前端用户在登录的情况下，添加商品到购物车，同时会在后端同步购物车到Redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopCartBO> shopCartList;
        if (StringUtils.isNotBlank(shopCartJson)) {
            // redis中已经存在购物车
            shopCartList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);
            // 判断购物车中是否存在已有商品，如果存在则counts累加
            boolean isHaving = false;
            for (ShopCartBO sc : shopCartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopCartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopCartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopCartList.add(shopCartBO);
            }
        } else {
            // redis中没有购物车
            shopCartList = Lists.newArrayList();
            // 直接添加到购物车中
            shopCartList.add(shopCartBO);
        }
        // 覆盖现有的redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId, @RequestParam String itemSpecId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除redis购物车中的商品
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shopCartJson)) {
            List<ShopCartBO> shopCartList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);
            // 判断购物车中是否存在商品，如果有的话删除
            for (ShopCartBO sc : shopCartList) {
                if (sc.getSpecId().equals(itemSpecId)) {
                    shopCartList.remove(sc);
                    break;
                }
            }
            // 覆盖现有redis中的购物车
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));
        }
        return IMOOCJSONResult.ok();
    }

}
