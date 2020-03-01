package com.zhangzz.controller;

import com.zhangzz.enums.OrderStatusEnum;
import com.zhangzz.enums.PayMethod;
import com.zhangzz.pojo.OrderStatus;
import com.zhangzz.pojo.bo.SubmitOrderBO;
import com.zhangzz.pojo.vo.MerchantOrdersVO;
import com.zhangzz.service.OrderService;
import com.zhangzz.utils.CookieUtils;
import com.zhangzz.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangzz
 * @date 2020/2/23 21:03
 */
@Api(value = "订单相关接口", tags = {"订单相关的Api接口"})
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO) {
        if (!PayMethod.WEIXIN.type.equals(submitOrderBO.getPayMethod()) &&
                !PayMethod.ALIPAY.type.equals(submitOrderBO.getPayMethod())) {
            return IMOOCJSONResult.errorMsg("支付方式不支持");
        }

        // 1.创建订单
        MerchantOrdersVO merchantOrdersVO = orderService.createOrder(submitOrderBO);
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);

        // 为了方便测试购买，所以所有的支付金额都改成1分钱
        merchantOrdersVO.setAmount(1);

        // 2.创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合Redis之后，完善购物车中的已结算商品的清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, IMOOCJSONResult.class);
        IMOOCJSONResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != HttpStatus.OK.value()) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }

        return IMOOCJSONResult.ok(merchantOrdersVO.getMerchantOrderId());
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }

}
