package com.zhangzz.enums;

/**
 * 性别 枚举
 * @author zhangzz
 * @date 2020/2/8 15:06
 */
public enum Sex {

    /** 性别 女 */
    woman(0,"女"),
    /** 性别 男 */
    man(1,"男"),
    /** 性别 保密 */
    secret(2, "保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
