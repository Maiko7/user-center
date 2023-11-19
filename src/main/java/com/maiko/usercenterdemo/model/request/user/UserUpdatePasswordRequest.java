package com.maiko.usercenterdemo.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端更新用户密码请求参数
 * @author: Maiko7
 * @create: 2023-11-16-17:26
 */
@Data
public class UserUpdatePasswordRequest implements Serializable {

    private static final long serialVersionUID = 2460547193759866328L;

    /**
     * 原密码
     */
    private String userPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
