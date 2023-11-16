package com.maiko.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端新增用户请求参数
 * @author: Maiko7
 * @create: 2023-11-16-9:22
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 5681064758718871956L;
    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 星球编号
     */
    private String planetCode;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private String userRole;


}
