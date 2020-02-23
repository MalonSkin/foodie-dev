package com.zhangzz.pojo.vo;

import lombok.Data;

/**
 * 用于展示商品搜索列表结果的VO
 * @author zhangzz
 * @date 2020/2/23 19:47
 */
@Data
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;

}
