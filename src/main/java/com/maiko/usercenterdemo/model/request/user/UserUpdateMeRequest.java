package com.maiko.usercenterdemo.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端更新用户个人信息请求参数
 * @author: Maiko7
 * @create: 2023-11-16-17:14
 */
@Data
public class UserUpdateMeRequest implements Serializable {

    private static final long serialVersionUID = -857623895184085754L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private String gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
