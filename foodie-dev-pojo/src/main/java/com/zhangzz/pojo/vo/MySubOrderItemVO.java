package com.zhangzz.pojo.vo;

import lombok.Data;

/**
 * 用户中心，我的订单列表嵌套商品VO
 * @author zhangzz
 * @date 2020/4/6 上午12:01
 */
@Data
public class MySubOrderItemVO {

    private String itemId;
    private String itemName;
    private String itemImg;
    private String itemSpecId;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;

}
