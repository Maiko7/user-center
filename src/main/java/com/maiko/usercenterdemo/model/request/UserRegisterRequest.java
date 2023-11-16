package com.maiko.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端注册请求参数
 * @author: Maiko7
 * @create: 2023-11-15-20:18
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -6863327650618266067L;

    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     * 星球编号
     */
    private String planetCode;

}
