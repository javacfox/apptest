package com.game.itstar.utile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 作者：鄢超超
 * 创建时间：2019/6/18 8:46
 * 版本：1.0
 * 描述：加密及解密
 */
public class EncryptUtil {
    //参考 https://blog.csdn.net/armys/article/details/79213631

    //编码方式
    private static final String CODE_TYPE = "UTF-8";
    //加密方式 AES或者DES
    private static final String ENCRYPT_TYPE = "AES";
    //此类型 加密内容,密钥必须为16字节的倍数位,否则抛异常,需要字节补全再进行加密
    private static final String AES_TYPE = "AES/CBC/NoPadding";
    //偏移量: AES 为16bytes. DES 为8bytes
    private static final String VI = "zhubin**..123456";
    //私钥
    private static final String KEY = "zhubin**..123456";

    //解密
    public static String decrypt(String content) {
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ENCRYPT_TYPE);
            Cipher cipher = Cipher.getInstance(AES_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(VI.getBytes()));
            byte[] decode = new BASE64Decoder().decodeBuffer(content);
            byte[] bytes = cipher.doFinal(decode);

            return new String(bytes, CODE_TYPE);
        } catch (Exception e) {
            System.out.println("exception:" + e.toString());
        }
        return null;
    }

    /**
     * 转16的倍数进制
     */
    private static byte[] parseByte(String content) throws UnsupportedEncodingException {
        byte[] bytes = content.getBytes(CODE_TYPE);
        int length = bytes.length;
        int len = length % 16 == 0 ? length : length / 16 + 16;
        byte[] result = new byte[len];
        System.arraycopy(bytes, 0, result, 0, length);
        int begin = length;
        while (begin < len) {
            result[begin] = 0;
            begin++;
        }
        return result;
    }
}
