package com.zhangzz.pojo.bo;

import lombok.Data;
import lombok.ToString;

/**
 * 用于创建订单的BO对象
 * @author zhangzz
 * @date 2020/2/29 13:23
 */
@Data
@ToString
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

}
