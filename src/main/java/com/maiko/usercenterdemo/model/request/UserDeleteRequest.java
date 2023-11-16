package com.maiko.usercenterdemo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端删除用户请求参数
 * @author: Maiko7
 * @create: 2023-11-16-16:38
 */
@Data
public class UserDeleteRequest implements Serializable {

    private static final long serialVersionUID = -6640869872736249125L;

    /**
     * id
     */
    private Long id;

}
