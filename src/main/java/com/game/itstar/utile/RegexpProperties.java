package com.game.itstar.utile;

/**
 * @Author 朱斌
 * @Date 2019/9/24  16:50
 * @Desc 正则表达式格式
 */
public class RegexpProperties {
    public static final String MOBILE_PATTERN = "^$|0?(13|14|15|18|17)[0-9]{9}$";
    public static final String EMAIL_PATTERN = "^$|(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
    public static final String FAX_PATTERN = "^$|(\\d{3,4}-)?\\d{7,8}$";
    public static final String CREDIT_CODE_PATTERN = "^$|[a-zA-Z0-9]{18}$";
}
