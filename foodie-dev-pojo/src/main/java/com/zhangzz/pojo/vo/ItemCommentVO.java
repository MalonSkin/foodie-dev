package com.zhangzz.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用于展示商品评价的VO
 * @author zhangzz
 * @date 2020/2/23 17:41
 */
@Data
public class ItemCommentVO {

    private String commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;

}
