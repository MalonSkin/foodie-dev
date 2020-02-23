package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzz.pojo.Items;
import com.zhangzz.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhangzz
 */
@Mapper
@Repository
public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * 查询商品评价详情
     * @param page     分页对象
     * @param paramsMap 参数Map
     * @return 商品评价VO 分页对象
     */
    public IPage<ItemCommentVO> queryItemComments(Page<?> page, @Param("paramsMap") Map<String, Object> paramsMap);

}