package com.game.itstar.enums;

/**
 * @Author 朱斌
 * @Date 2019/10/9  8:52
 * @Desc
 */
public enum TeamType {
    PLAYERS(0, "队员"),
    CAPTAIN(1, "队长");

    private Integer value;
    private String text;

    TeamType(Integer value, String text) {
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
