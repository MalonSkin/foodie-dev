package com.zhangzz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangzz.pojo.ItemsComments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangzz
 */
@Mapper
@Repository
public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

}