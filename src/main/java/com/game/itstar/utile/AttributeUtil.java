package com.game.itstar.utile;

/**
 * @Author 朱斌
 * @Date 2019/9/25  8:50
 * @Desc 常量属性
 */
public class AttributeUtil implements java.io.Serializable {
    //系统生成密码常量 begin
    public static final int HASH_INTERATIONS=1024;
    public static final int SALT_SIZE=8;
    public static final String PASSWORD_LEVEL_1 = "1";//系统密码策略复杂级别1
    public static final String PASSWORD_LEVEL_2 = "2";//系统密码策略复杂级别2
    public static final String PASSWORD_LEVEL_3 = "3";//系统密码策略复杂级别3
    public static final String PASSWORD="123456";//系统默认初始密码，复杂级别为1
    public static final String PASSWORD2="12345678a";//系统默认初始密码，复杂级别为2
    public static final String PASSWORD3="12345678a+";//系统默认初始密码，复杂级别为3
    public static final String PASSWORD_DESCRIBE = "密码至少为6位数字或字母";
    public static final String PASSWORD2_DESCRIBE = "密码至少为8位以上(含8位)的数字+字母组合";
    public static final String PASSWORD3_DESCRIBE = "密码至少为8位以上(含8位)的数字+字母+特殊字符组合";



}
