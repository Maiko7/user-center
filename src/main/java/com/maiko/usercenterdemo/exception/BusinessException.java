package com.maiko.usercenterdemo.exception;

import com.maiko.usercenterdemo.common.ErrorCode;

/**
 * 自定义异常类
 * @author: Maiko7
 * @create: 2023-11-14-18:30
 */


/**
 * 提供更多信息： 通过 BusinessException 类，你可以传递自定义的错误码、描述信息，以及异常的详细消息。
 * 这样，当捕获到异常时，你能够获取更多关于业务出错原因的信息，便于排查问题。
 *
 * 与 ErrorCode 结合使用： 使用 ErrorCode 作为参数，允许你在抛出异常时使用预定义的错误码，
 * 提高了异常信息的一致性。这样在异常处理时，可以根据错误码来进行统一的处理逻辑。
 */
public class BusinessException extends RuntimeException{

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
