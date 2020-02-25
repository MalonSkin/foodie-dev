package com.zhangzz.pojo.bo;

import lombok.Data;
import lombok.ToString;

/**
 * 购物车BO
 * @author zhangzz
 * @date 2020/2/23 21:07
 */
@Data
@ToString
public class ShopCartBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;

}
