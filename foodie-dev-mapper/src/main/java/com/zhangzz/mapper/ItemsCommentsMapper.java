package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangzz.pojo.ItemsComments;
import com.zhangzz.pojo.vo.MyCommentVO;
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
public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    /**
     * 保存用户评论
     * @param map
     */
    public void saveComments(Map<String, Object> map);

    /**
     * 查询我的评价列表
     * @param page
     * @param map
     * @return
     */
    public IPage<MyCommentVO> queryMyComments(Page<?> page, @Param("paramsMap") Map<String, Object> map);

}