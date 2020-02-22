package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangzz.pojo.Category;
import com.zhangzz.pojo.vo.CategoryVO;
import com.zhangzz.pojo.vo.NewItemsVO;
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
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询子分类列表
     * @param rootCatId 节点分类ID
     * @return 分类列表
     */
    public List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param paramsMap 参数map
     * @return 商品列表
     */
    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> paramsMap);

}