package com.zhangzz.controller;

import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.UserBO;
import com.zhangzz.service.UserService;
import com.zhangzz.utils.CookieUtils;
import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import com.zhangzz.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return IMOOCJSONResult.ok(user);
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

        // TODO 用户退出登录，需要清空后无车
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

}
