package com.game.itstar.utile;

/**
 * @Author 朱斌
 * @Date 2019/4/19  14:34
 * @Desc 初始化系统常量管理
 */
public class ConstantProperties {
    /**
     * 默认角色
     */
    public static final Integer DEFAULT_ROLECODE = 0;

    /**
     * 默认角色Name
     */
    public static final String DEFAULT_ROLENAME = "默认角色";

    /**
     * 系统管理员角色Name
     */
    public static final String ADMIN_ROLENAME = "系统管理员";

    /**
     * 超级管理员
     */
    public static final String SUPER_ADMIN_ROLECODE = "SuperAdmin";

    /**
     * 管理员类型
     */
    public static final Integer ADMIN_ROLECODE = 1;

    /**
     * 申请加入战队验证类型 -- 1 不需要审核
     */
    public static final Integer NO_VALIDATION_REQUIRED = 1;

    /**
     * 申请加入战队验证类型 -- 2 需要审核
     */
    public static final Integer VALIDATION_REQUIRED = 2;

    /**
     * 申请加入战队邀请码长度-- 不需要审核
     */
    public static final int TEAM_CODE_LENGTH = 20;


    /**
     * 申请加入战队邀请码长度-- 需要审核
     */
    public static final int TEAM_AUDIT_CODE_LENGTH = 15;

    public static final String TEAM_AUDIT_CODE_AGO = "audit";

}
