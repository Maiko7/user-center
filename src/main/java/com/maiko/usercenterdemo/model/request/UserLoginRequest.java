package com.maiko.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端用户登陆请求参数
 * @author: Maiko7
 * @create: 2023-11-14-18:11
 */
@Data
/**
 * 这里犯了一个什么错误呢？你记住只要是model里面的全部给我序列化
 */
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -4328055719375950583L;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 密码
     */
    private String userPassword;



}
