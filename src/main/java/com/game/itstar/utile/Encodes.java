package com.game.itstar.utile;

import com.game.itstar.response.ResException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @Author 朱斌
 * @Date 2019/9/25  9:01
 * @Desc 封装各种格式的编码解码工具类.
 */
public class Encodes {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw ResException.unchecked(e);
        }
    }
}
