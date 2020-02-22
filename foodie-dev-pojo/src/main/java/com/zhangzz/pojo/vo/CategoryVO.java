package com.zhangzz.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 二级分类VO
 * @author zhangzz
 * @date 2020/2/22 20:41
 */
@Data
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;
    /** 三级分类VO list */
    private List<SubCategoryVO> subCatList;

}
