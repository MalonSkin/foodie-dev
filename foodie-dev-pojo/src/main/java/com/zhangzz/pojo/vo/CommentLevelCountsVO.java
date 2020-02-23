package com.zhangzz.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于展示商品评价数量的VO
 * @author zhangzz
 * @date 2020/2/23 17:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;

}
