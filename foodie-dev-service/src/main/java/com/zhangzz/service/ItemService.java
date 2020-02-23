package com.zhangzz.service;

import com.zhangzz.pojo.Items;
import com.zhangzz.pojo.ItemsImg;
import com.zhangzz.pojo.ItemsParam;
import com.zhangzz.pojo.ItemsSpec;
import com.zhangzz.pojo.vo.CommentLevelCountsVO;
import com.zhangzz.utils.PagedGridResult;

import java.util.List;

/**
 * 商品服务层接口
 * @author zhangzz
 * @date 2020/2/22 12:21
 */
public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param itemId 商品ID
     * @return 商品详情
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品ID查询商品图片列表
     * @param itemId 商品ID
     * @return 商品图片列表
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格列表
     * @param itemId 商品ID
     * @return 商品规格列表
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品参数
     * @param itemId 商品ID
     * @return 商品参数
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId 商品id
     * @return 评价统计
     */
    public CommentLevelCountsVO queryCommentsCounts(String itemId);

    /**
     * 根据商品id和商品等级查询商品评价（分页）
     * @param itemId 商品id
     * @param level  商品评价等级
     * @param page 当前页码
     * @param pageSize 每页分页数量
     * @return 商品评价列表
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Long page, Long pageSize);
}
