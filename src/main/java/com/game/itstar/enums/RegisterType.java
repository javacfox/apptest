package com.game.itstar.enums;

/**
 * @Author 朱斌
 * @Date 2019/9/27  17:28
 * @Desc 注册用户类型
 */
public enum RegisterType {
    USER(1, "普通用户"),
    ADMIN(2, "管理员用户"),
    SUPER_ADMIN(99, "管理员用户");

    private Integer value;
    private String text;

    RegisterType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }
}
