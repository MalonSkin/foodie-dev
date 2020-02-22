package com.zhangzz.service;

import com.zhangzz.pojo.Carousel;

import java.util.List;

/**
 * 轮播图服务层接口
 * @author zhangzz
 * @date 2020/2/22 12:21
 */
public interface CarouselService {

    /**
     * 查询所有的轮播图列表
     * @param isShow 是否展示
     * @return 轮播图列表
     */
    public List<Carousel> queryAll(Integer isShow);
}
