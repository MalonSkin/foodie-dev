package com.zhangzz.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户中心，我的订单列表VO
 * @author zhangzz
 * @date 2020/4/5 下午11:59
 */
@Data
public class MyOrdersVO {

    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer orderStatus;
    private Integer isComment;

    private List<MySubOrderItemVO> subOrderItemList;

}
