package com.maiko.usercenterdemo.constant;

/**
 * 用户常量类
 * @author: Maiko7
 * @create: 2023-11-15-9:32
 */
public class UserConstant {

    /**
     * 盐值
     */
    public static final String SALT = "password:maiko";

    /**
     * 用户登录态键
     */
    public static final String USER_LOGIN_STATE = "login:state";

    /**
     * 密码长度
     */
    public static final int PASSWORD_LENGTH = 8;

    /**
     * 账号长度
     */
    public static final int USERACCOUNT_LENGTH = 4;

    /**
     * 星球编号长度
     */
    public static final int PLANTE_LENGTH = 5;
    /**
     * 管理员权限  admin
     */
    public static final int ADMIN_ROLE = 1;

    /**
     * 默认权限
     */
    public static final int DEFAULT_ROLE = 0;

}
