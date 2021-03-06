package com.zhangzz.controller.center;

import com.zhangzz.controller.BaseController;
import com.zhangzz.pojo.Orders;
import com.zhangzz.service.center.MyOrdersService;
import com.zhangzz.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangzz
 * @date 2020/4/6 上午12:18
 */
@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public IMOOCJSONResult statusCounts(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        return IMOOCJSONResult.ok(myOrdersService.getOrderStatusCounts(userId));
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public IMOOCJSONResult trend(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页码", required = false) @RequestParam Long page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Long pageSize
    ) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        return IMOOCJSONResult.ok(myOrdersService.getMyOrderTrend(userId, page, pageSize));
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false) @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "当前页码", required = false) @RequestParam Long page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false) @RequestParam Long pageSize
    ) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        return IMOOCJSONResult.ok(myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize));
    }

    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId) throws Exception {
        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId
    ) {
        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }
        boolean result = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return IMOOCJSONResult.errorMsg("订单确认收货失败！");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId
    ) {
        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }
        boolean result = myOrdersService.deleteOrder(userId, orderId);
        if (!result) {
            return IMOOCJSONResult.errorMsg("订单删除失败！");
        }
        return IMOOCJSONResult.ok();
    }

}
