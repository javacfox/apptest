package com.game.itstar.utile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc String工具类
 */
public class StringUtil {
    private static final String FILTER_SQL_INJECT = "'# and # exec # insert # select # delete # # update # count # * # % # chr # mid # master # truncate # char # declare #;# or #,";
    private static final String[] INJECT_STRING_ARRAY = "'# and # exec # insert # select # delete # # update # count # * # % # chr # mid # master # truncate # char # declare #;# or #,".split("#");

    public StringUtil() {
    }

    public static String onlyLetterAndDigital(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String filterSQLCondition(String condition) {
        if (condition != null && !condition.equals("")) {
            String[] var1 = INJECT_STRING_ARRAY;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                String s = var1[var3];
                condition = condition.replace(s, "");
            }

            return condition;
        } else {
            return "";
        }
    }

    public static String subStart(String str, int length) {
        return str.length() > length ? str.substring(0, length) : str;
    }

    public static String substring(String str, int startIndex, int endIndex) {
        startIndex = startIndex < 0 ? 0 : startIndex;
        endIndex = endIndex > str.length() ? str.length() : endIndex;
        return startIndex < endIndex ? "" : str.substring(startIndex, endIndex);
    }

    public static String substr(String str, int startIndex, int length) {
        return substring(str, startIndex, startIndex + length);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     * 当用户提供明文密码是
     *
     * @param password 用户输入密码
     */
//    public static Map<String, String> entryptPassword(String password) {
//        Map<String, String> map = new HashMap<>();
//        password = isEmptyString(password) ? AttributeUtil.PASSWORD : password;
//        byte[] salt = MD5ShaUtil.generateSalt(AttributeUtil.SALT_SIZE);
//        byte[] hashPassword = MD5ShaUtil.sha1(password.getBytes(), salt, AttributeUtil.HASH_INTERATIONS);
//        password = Encodes.encodeHex(hashPassword);
//        map.put("password", password);
//        map.put("enCodeSalt", Encodes.encodeHex(salt));
//        return map;
//    }
//
}
