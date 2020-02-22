package com.zhangzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangzz.mapper.CarouselMapper;
import com.zhangzz.pojo.Carousel;
import com.zhangzz.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangzz
 * @date 2020/2/22 12:24
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Carousel::getIsShow, isShow).orderByAsc(Carousel::getSort);
        return carouselMapper.selectList(queryWrapper);
    }
}
