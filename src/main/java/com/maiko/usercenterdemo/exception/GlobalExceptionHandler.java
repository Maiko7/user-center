package com.maiko.usercenterdemo.exception;

import com.maiko.usercenterdemo.common.BaseResponse;
import com.maiko.usercenterdemo.common.ErrorCode;
import com.maiko.usercenterdemo.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author: Maiko7
 * @create: 2023-11-16-18:52
 */

// @RestControllerAdvice 注解将其标记为全局异常处理器
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获BusinessException异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("businessException:" +e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    /**
     * 捕获RuntimeException异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
