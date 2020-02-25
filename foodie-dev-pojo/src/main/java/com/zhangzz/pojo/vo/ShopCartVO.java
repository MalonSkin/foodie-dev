package com.zhangzz.pojo.vo;

import lombok.Data;

/**
 * @author zhangzz
 * @date 2020/2/23 21:40
 */
@Data
public class ShopCartVO {

    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;

}
