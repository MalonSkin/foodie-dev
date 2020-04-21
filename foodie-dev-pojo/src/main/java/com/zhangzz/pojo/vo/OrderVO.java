package com.zhangzz.pojo.vo;

import com.zhangzz.pojo.bo.ShopCartBO;

import java.util.List;

public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopCartBO> toBeRemovedShopCartList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }

    public List<ShopCartBO> getToBeRemovedShopCartList() {
        return toBeRemovedShopCartList;
    }

    public void setToBeRemovedShopCartList(List<ShopCartBO> toBeRemovedShopCartList) {
        this.toBeRemovedShopCartList = toBeRemovedShopCartList;
    }
}