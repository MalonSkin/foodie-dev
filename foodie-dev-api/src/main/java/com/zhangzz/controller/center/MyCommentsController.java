package com.zhangzz.controller.center;

import com.zhangzz.controller.BaseController;
import com.zhangzz.enums.YesOrNo;
import com.zhangzz.pojo.Orders;
import com.zhangzz.pojo.bo.center.OrderItemsCommentBO;
import com.zhangzz.service.center.MyCommentsService;
import com.zhangzz.service.center.MyOrdersService;
import com.zhangzz.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangzz
 * @date 2020/4/6 上午12:18
 */
@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询待评价列表", notes = "查询待评价列表", httpMethod = "POST")
    @PostMapping("/pending")
    public IMOOCJSONResult pending(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true) @RequestParam String orderId
    ) {
        // 判断用户和订单是否关联
        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否已经评价，评价过了就不在继续
        Orders myOrder = (Orders) checkResult.getData();
        if (myOrder.getIsComment().equals(YesOrNo.YES.type)) {
            return IMOOCJSONResult.errorMsg("该笔订单已经评价");
        }
        return IMOOCJSONResult.ok(myCommentsService.queryPendingComment(orderId));
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public IMOOCJSONResult saveList(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true) @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList
            ) {
        // 判断用户和订单是否关联
        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (CollectionUtils.isEmpty(commentList)) {
            return IMOOCJSONResult.errorMsg("评论内容不能为空");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
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
        return IMOOCJSONResult.ok(myCommentsService.queryMyComments(userId, page, pageSize));
    }

}
