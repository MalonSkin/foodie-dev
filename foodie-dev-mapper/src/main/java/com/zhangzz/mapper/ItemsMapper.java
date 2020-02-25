package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzz.pojo.Items;
import com.zhangzz.pojo.vo.ItemCommentVO;
import com.zhangzz.pojo.vo.SearchItemsVO;
import com.zhangzz.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 */
@Mapper
@Repository
public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * 查询商品评价详情
     * @param page      分页对象
     * @param paramsMap 参数Map
     * @return 商品评价VO 分页对象
     */
    public IPage<ItemCommentVO> queryItemComments(Page<?> page, @Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 搜索商品
     * @param page      分页对象
     * @param paramsMap 参数Map
     * @return 商品搜索结果VO 分页对象
     */
    public IPage<SearchItemsVO> searchItems(Page<?> page, @Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据商品分类id搜索商品列表
     * @param page      分页对象
     * @param paramsMap 参数Map
     * @return 商品搜索结果VO 分页对象
     */
    public IPage<SearchItemsVO> searchItemsByThirdCat(Page<?> page, @Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据规格id集合查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIdsList 规格id集合
     * @return 最新的购物车中商品数据
     */
    public List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
}