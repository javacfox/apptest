package com.game.itstar.utile;

import java.util.Random;

/**
 * @Author 朱斌
 * @Date 2019/4/17  15:35
 * @Desc 随机字符串生成工具类
 */
public class RandCodeUtil {
    public static final Integer NUMBER = 1;
    public static final Integer MIX = 2;
    private static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";//去掉了1,0,i,o几个容易混淆的字符
    private static final String NUMBER_CODES = "1234567890";//纯数字

    /**
     * 生成随机验证码
     *
     * @param verifySize
     * @param type
     * @return
     */
    public static String generateVerifyCode(Integer verifySize, Integer type) {
        if (type == NUMBER) {
            return generateVerifyCode(verifySize, type, NUMBER_CODES);
        }
        return generateVerifyCode(verifySize, type, VERIFY_CODES);
    }

    public static String generateVerifyCode(Integer verifySize, Integer type, String sources) {
        if (sources == null || sources.length() == 0) {
            if (type == NUMBER) {
                sources = NUMBER_CODES;
            } else {
                sources = VERIFY_CODES;
            }
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }
}
