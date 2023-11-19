package com.maiko.usercenterdemo.common;

/**
 * 枚举错误码
 * @author: Maiko7
 * @create: 2023-11-14-18:40
 */
public enum ErrorCode {

    SUCCESS(0, "成功", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN_ERROR(40100, "未登录", ""),
    /**
     * 无权限表示用户已经登陆了，但是没有权限访问
     */
    NO_AUTH_ERROR(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    OPERATION_ERROR(50001, "操作失败", "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String message;

    /**
     * 描述信息（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
