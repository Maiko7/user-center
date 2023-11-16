package com.maiko.usercenterdemo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前端的通用的
 * @author: Maiko7
 * @create: 2023-11-14-19:21
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3278778224873771174L;

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
