package com.zhangzz.service;

import com.zhangzz.pojo.Category;
import com.zhangzz.pojo.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类层接口
 * @author zhangzz
 * @date 2020/2/22 12:21
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return 一级分类列表
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId 一级分类id
     * @return 子分类列表
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);
}
