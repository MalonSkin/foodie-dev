package com.zhangzz.enums;

/**
 * 是否 枚举
 * @author zhangzz
 * @date 2020/2/8 15:06
 */
public enum YesOrNo {

    /** 否 */
    NO(0,"否"),
    /** 是 */
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
