package com.zhangzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangzz.enums.CommentLevel;
import com.zhangzz.enums.YesOrNo;
import com.zhangzz.mapper.*;
import com.zhangzz.pojo.*;
import com.zhangzz.pojo.vo.CommentLevelCountsVO;
import com.zhangzz.pojo.vo.ItemCommentVO;
import com.zhangzz.pojo.vo.SearchItemsVO;
import com.zhangzz.pojo.vo.ShopCartVO;
import com.zhangzz.service.ItemService;
import com.zhangzz.utils.DesensitizationUtil;
import com.zhangzz.utils.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 * @date 2020/2/23 14:18
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectById(itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemImgList(String itemId) {
        QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ItemsImg::getItemId, itemId);
        return itemsImgMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        QueryWrapper<ItemsSpec> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ItemsSpec::getItemId, itemId);
        return itemsSpecMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParam(String itemId) {
        QueryWrapper<ItemsParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ItemsParam::getItemId, itemId);
        return itemsParamMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public CommentLevelCountsVO queryCommentsCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        return new CommentLevelCountsVO(totalCounts, goodCounts, normalCounts, badCounts);
    }

    /**
     * 根据商品id和评价等级统计记录数
     * @param itemId 商品id
     * @param level  评价等级
     * @return 记录数
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        QueryWrapper<ItemsComments> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ItemsComments::getItemId, itemId)
                .eq(ItemsComments::getCommentLevel, level);
        return itemsCommentsMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult queryPagedComments(String itemId, Integer level, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("itemId", itemId);
        paramsMap.put("level", level);
        Page<ItemCommentVO> queryPage = new Page<>(page, pageSize);
        IPage<ItemCommentVO> iPage = itemsMapper.queryItemComments(queryPage, paramsMap);
        for (ItemCommentVO vo : iPage.getRecords()) {
            // 用户名脱敏
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return new PagedGridResult().fromPage(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItems(String keywords, String sort, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("keywords", keywords);
        paramsMap.put("sort", sort);
        Page<ItemCommentVO> queryPage = new Page<>(page, pageSize);
        IPage<SearchItemsVO> iPage = itemsMapper.searchItems(queryPage, paramsMap);
        return new PagedGridResult().fromPage(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Long page, Long pageSize) {
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("catId", catId);
        paramsMap.put("sort", sort);
        Page<ItemCommentVO> queryPage = new Page<>(page, pageSize);
        IPage<SearchItemsVO> iPage = itemsMapper.searchItemsByThirdCat(queryPage, paramsMap);
        return new PagedGridResult().fromPage(iPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        List<String> specIdsList = Lists.newArrayList(StringUtils.split(specIds, ","));
        return itemsMapper.queryItemsBySpecIds(specIdsList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectById(specId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public String queryItemMainImgById(String itemId) {
        QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ItemsImg::getItemId, itemId)
                .eq(ItemsImg::getIsMain, YesOrNo.YES.type);
        ItemsImg itemImg = itemsImgMapper.selectOne(queryWrapper);
        return itemImg != null ? itemImg.getUrl() : "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        // 使用分布式锁 zookeeper Redis
        // lockUtil.getLock(); -- 加锁
        // 执行扣库存的逻辑
        // lockUtil.unLock(); -- 解锁

        // 目前单体应用使用数据库乐观锁
        int result = itemsMapper.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("创建订单失败，原因：库存不足");
        }
    }
}
