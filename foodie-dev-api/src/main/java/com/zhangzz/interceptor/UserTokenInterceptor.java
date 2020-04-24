package com.zhangzz.interceptor;

import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import com.zhangzz.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 用户token拦截器
 * @author zhangzz
 * @date 2020/4/24 23:31
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    /** 用户token */
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 拦截请求，在访问controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerUserId = request.getHeader("headerUserId");
        String headerUserToken = request.getHeader("headerUserToken");
        if (StringUtils.isAnyBlank(headerUserId, headerUserToken)) {
            returnErrorResponse(response, IMOOCJSONResult.errorMsg("请登录..."));
            return false;
        }
        String userToken = redisOperator.get(REDIS_USER_TOKEN + ":" + headerUserId);
        if (StringUtils.isBlank(userToken)) {
            returnErrorResponse(response, IMOOCJSONResult.errorMsg("账号在异地登录..."));
            return false;
        }
        if (!headerUserToken.equals(userToken)) {
            returnErrorResponse(response, IMOOCJSONResult.errorMsg("请登录..."));
            return false;
        }
        return true;
    }

    private void returnErrorResponse(HttpServletResponse response, IMOOCJSONResult result) {
        try (OutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            out.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
