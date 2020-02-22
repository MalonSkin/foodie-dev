package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangzz.pojo.Category;
import com.zhangzz.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}