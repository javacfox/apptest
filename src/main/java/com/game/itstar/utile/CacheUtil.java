package com.game.itstar.utile;

public class CacheUtil {
    private static final String PREFIX = "base:";

    /**
     * 用户Session信息
     */
    public static String getUserKey(Integer userId) {
        return PREFIX + "loginUser: " + userId;
    }

}
