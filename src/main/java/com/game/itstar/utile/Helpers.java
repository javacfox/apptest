package com.game.itstar.utile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.itstar.response.ResException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class Helpers {
    public static boolean isNotEmptyAndZero(Object str) {
        return str != null && !str.toString().trim().equals("") && Integer.valueOf(str.toString().trim()).intValue() != 0;
    }

    /**
     * 判断传入参数是否为空,空字符串""或"null"或"<null> 为了兼容ios的空获取到<null>字符串
     *
     * @param s 待判断参数
     * @return true 空 <br>
     * false 非空
     */
    public static boolean isEmptyString(Object s) {
        return (s == null) || (s.toString().trim().length() == 0) || s.toString().trim().equalsIgnoreCase("null")
                || s.toString().trim().equalsIgnoreCase("<null>");
    }

    public static boolean isNotNullAndEmpty(Object str) {
        return str != null && !str.toString().trim().equals("");
    }

    /**
     * 拼接字符串
     *
     * @param var1 集合
     * @param var2 分隔符
     * @return
     */
    public static String join(Collection var1, String var2) {
        StringBuilder var3 = new StringBuilder();

        for (Iterator var4 = var1.iterator(); var4.hasNext(); var3.append(var4.next())) {
            if (var3.length() != 0) {
                var3.append(var2);
            }
        }

        return var3.toString();
    }

    public static <T> T getToEntity(String str, TypeReference<T> typeReference) {
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return om.readValue(str, typeReference);
        } catch (IOException ex) {
            throw new RuntimeException("解析出错");
        }
    }

    public static <T> T getToEntity(String str, T t) {
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return (T) om.readValue(str, t.getClass());
        } catch (IOException ex) {
            throw new RuntimeException("解析出错");
        }
    }

    public static void requireNonNull(String message, Object... args) {
        boolean flag = Arrays.stream(args).anyMatch(Helpers::isNull);

        if (flag) {
            throw new ResException(message);
        }
    }

    private static boolean isNull(Object obj) {
        //普通非空
        if (obj == null)
            return true;

        //字符串
        if (obj instanceof String) {
            return obj.toString().trim().equals("");
        }

        //集合,大小不为空
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        //Map,键值对为为空
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        //不满足条件
        return false;
    }
}
