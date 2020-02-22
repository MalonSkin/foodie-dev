package com.zhangzz.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 最新商品VO
 * @author zhangzz
 * @date 2020/2/22 23:59
 */
@Data
public class NewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;

}
