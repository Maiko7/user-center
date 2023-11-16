package com.maiko.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端查询用户请求参数
 *
 * @author: Maiko7
 * @create: 2023-11-16-11:03
 */
@Data
public class UserSearchRequest implements Serializable {

    private static final long serialVersionUID = -4315071089481157148L;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 性别
     */
    private Integer gender;


    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态  0 - 正常  1 - 注销  2 - 封号
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;
}
