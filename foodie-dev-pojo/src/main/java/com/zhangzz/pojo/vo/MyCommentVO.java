package com.zhangzz.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangzz
 * @date 2020/4/6 下午1:17
 */
@Data
public class MyCommentVO {

    private String commentId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String itemName;
    private String specName;
    private String itemImg;

}
