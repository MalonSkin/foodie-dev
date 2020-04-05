package com.zhangzz.controller.center;

import com.google.common.collect.Maps;
import com.zhangzz.controller.BaseController;
import com.zhangzz.pojo.Users;
import com.zhangzz.pojo.bo.center.CenterUserBO;
import com.zhangzz.resource.FileUpload;
import com.zhangzz.service.center.CenterUserService;
import com.zhangzz.utils.CookieUtils;
import com.zhangzz.utils.DateUtil;
import com.zhangzz.utils.IMOOCJSONResult;
import com.zhangzz.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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

    private static final Logger LOG = LoggerFactory.getLogger(CenterUserController.class);

    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file) {
        // 在路径上为每个用户增加一个userId,用于区分不同用户的上传
        String uploadPath = fileUpload.getImageUserFaceLocation() + File.separator + userId;
        String newFileName = "";
        // 开始文件上传
        if (file != null) {
            // 获得文件上传的文件名称
            String filename = file.getOriginalFilename();
            if (StringUtils.isNotBlank(filename)) {
                // 获取文件后缀名
                String suffix = filename.substring(filename.lastIndexOf(".") + 1);
                if (!StringUtils.equalsAnyIgnoreCase(suffix, "png", "jpg", "jpeg")) {
                    return IMOOCJSONResult.errorMsg("图片格式不正确");
                }
                // 文件名称重组
                newFileName = "face-" + userId + "." + suffix;
                // 上传头像的最终保存位置
                String finalFacePath = uploadPath + File.separator + newFileName;
                File outFile = new File(finalFacePath);
                if (!outFile.getParentFile().exists()) {
                    // 创建文件夹
                    outFile.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(outFile);
                } catch (IOException e) {
                    LOG.error("用户头像保存失败", e);
                    return IMOOCJSONResult.errorMsg("用户头像保存失败");
                }
            }
        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        // 更新用户头像到数据库,由于浏览器能存在缓存，所以这里需要加上时间戳保证页面的图片能及时更新
        String faceUrl = fileUpload.getImageServerUrl() + "/" + userId + "/" + newFileName + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users userResult = centerUserService.updateUserFace(userId, faceUrl);
        setNullProperty(userResult);
        // 设置cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌token，会整合进Redis，分布式会话

        return IMOOCJSONResult.ok();
    }

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
