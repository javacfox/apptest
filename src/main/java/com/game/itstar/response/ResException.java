package com.game.itstar.response;

/**
 * 创建时间：2019/1/28 11:03
 * 版本：1.0
 * 描述：异常类
 */
public class ResException extends RuntimeException {
    private final ResStatus status;

    public ResException(ResStatus status) {
        super(status.getMsg());
        this.status = status;
    }

    public ResException(String message, ResStatus status) {
        super(message);
        this.status = status;
    }

    public ResException(String message) {
        super(message);
        this.status = ResStatus.FAILED;
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Throwable ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        } else {
            return new RuntimeException(ex);
        }
    }
}
