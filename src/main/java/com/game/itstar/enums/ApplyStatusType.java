package com.game.itstar.enums;

public enum ApplyStatusType {
    APPLY(0, "申请"),
    REFUSED(1, "拒绝"),
    THROUGH(2, "通过");

    private Integer value;
    private String text;

    ApplyStatusType(Integer value, String text) {
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
