package com.zhangzz.controller.center;

import com.google.common.collect.Maps;
import com.zhangzz.controller.BaseController;
import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.center.CenterUserBO;
import com.zhangzz.service.center.CenterUserService;
import com.zhangzz.utils.CookieUtils;
import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 * @date 2020/3/8 22:19
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("/userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }
        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(userResult);
        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌token，会整合进Redis，分布式会话

        return IMOOCJSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = Maps.newHashMap();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
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

}
