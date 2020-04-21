package com.zhangzz.controller;

import com.google.common.collect.Lists;
import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.ShopCartBO;
import com.zhangzz.pojo.bo.UserBO;
import com.zhangzz.service.UserService;
import com.zhangzz.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通行验证
 * @author zhangzz
 * @date 2020/2/7 17:03
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        // 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        // 判断用户名是否存在
        boolean isExist = userService.usernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public IMOOCJSONResult register(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        // 判断用户名密码是否为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        // 判断用户名是否存在
        boolean isExist = userService.usernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        // 密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6位");
        }
        // 确认两次密码是否一致
        if (!StringUtils.equals(password, confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次密码不一致");
        }
        // 实现注册
        Users user = userService.createUser(userBO);
        setNullProperty(user);
        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        // TODO 生成用户token，存入Redis会话
        // 同步购物车数据
        syncShopCartData(user.getId());
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        // 判断用户名密码是否为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        // 实现登录
        Users user = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (user == null) {
            return IMOOCJSONResult.errorMsg("用户名或者密码错误");
        }
        setNullProperty(user);
        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // TODO 生成用户token，存入Redis会话
        // 同步购物车数据
        syncShopCartData(user.getId());
        return IMOOCJSONResult.ok(user);
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void syncShopCartData(String userId) {
        // 1.redis中无数据，cookie购物车为空则不处理，不为空则直接放入redis
        // 2.redis中有数据，cookie购物车为空则直接把redis中的购物车覆盖到本地cookie
        //      如果cookie中的购物车不为空，而cookie中的某个商品在redis中存在则以cookie中的为主，删除redis中的，
        //      把cookie中的商品直接覆盖redis中（参考京东）
        // 3.同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
        // 从redis中获取购物车
        String shopCartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        // 从cookie中获取购物车
        String shopCartJsonCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);
        if (StringUtils.isBlank(shopCartJsonRedis)) {
            // redis为空，cookie不为空，直接把cookie中的购物车放入redis
            if (StringUtils.isNotBlank(shopCartJsonCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopCartJsonCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopCartJsonCookie)) {
                List<ShopCartBO> shopCartListRedis = JsonUtils.jsonToList(shopCartJsonRedis, ShopCartBO.class);
                List<ShopCartBO> shopCartListCookie = JsonUtils.jsonToList(shopCartJsonCookie, ShopCartBO.class);
                // 定义一个带待删除的List
                List<ShopCartBO> pendingDelList = Lists.newArrayList();
                for (ShopCartBO redisShopCart : shopCartListRedis) {
                    String redisSpecId = redisShopCart.getSpecId();
                    for (ShopCartBO cookieShopCart : shopCartListCookie) {
                        String cookieSpecId = cookieShopCart.getSpecId();
                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量不累加，参考京东
                            redisShopCart.setBuyCounts(cookieShopCart.getBuyCounts());
                            // 把cookieShopCart放入待删除集合中，用于最后的删除合并
                            pendingDelList.add(cookieShopCart);
                        }
                    }
                }
                // 从现有cookie中删除对应的覆盖过的商品数据
                shopCartListCookie.removeAll(pendingDelList);
                // 合并两个list
                shopCartListRedis.addAll(shopCartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopCartJsonRedis, true);
            }
        }
    }

    private void setNullProperty(Users user) {
        user.setBirthday(null);
        user.setCreatedTime(null);
        user.setEmail(null);
        user.setMobile(null);
        user.setPassword(null);
        user.setRealname(null);
        user.setUpdatedTime(null);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId) {
        // 清除用户相关的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

}
