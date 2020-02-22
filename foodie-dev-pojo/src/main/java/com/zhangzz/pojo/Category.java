package com.zhangzz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * category
 * @author zhangzz
 */
@Data
@TableName(value = "category")
public class Category implements Serializable {
    /**
     * 主键 分类id主键
     */
    private Integer id;

    /**
     * 分类名称 分类名称
     */
    private String name;

    /**
     * 分类类型 分类得类型，
     * 1:一级大分类
     * 2:二级分类
     * 3:三级小分类
     */
    private Integer type;

    /**
     * 父id 父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级
     */
    private Integer fatherId;

    /**
     * 图标 logo
     */
    private String logo;

    /**
     * 口号
     */
    private String slogan;

    /**
     * 分类图
     */
    private String catImage;

    /**
     * 背景颜色
     */
    private String bgColor;

    private static final long serialVersionUID = 1L;
}