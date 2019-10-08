package com.game.itstar.response;

/**
 * 创建时间：2019/1/28 9:49
 * 版本：1.0
 * 描述：操作结果枚举
 */
public enum ResStatus {
    SUCCESS(200, "操作成功", Boolean.TRUE),
    BAD_REQUEST(400, "参数错误",Boolean.FALSE),
    NEED_LOGIN(401,"用户未登陆",Boolean.FALSE),
    FAILED(500, "操作失败", Boolean.FALSE);

    private int code;
    private String msg;
    private boolean success;

    ResStatus(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
