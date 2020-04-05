package com.zhangzz.exception;

import com.zhangzz.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 自定义异常处理器
 * @author zhangzz
 * @date 2020/4/5 下午10:51
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 上传文件超过2MB，捕获异常 MaxUploadSizeExceededException
     * @param e
     * @return
     */
    @ExceptionHandler
    public IMOOCJSONResult handleMaxUploadFile(MaxUploadSizeExceededException e) {
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过2MB,请压缩图片或者降低图片质量");
    }

}
