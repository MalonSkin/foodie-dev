package com.zhangzz.pojo.vo;

import lombok.Data;

/**
 * 三级分类VO
 * @author zhangzz
 * @date 2020/2/22 20:44
 */
@Data
public class SubCategoryVO {

    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
