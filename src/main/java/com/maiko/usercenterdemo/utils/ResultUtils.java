package com.maiko.usercenterdemo.utils;

import com.maiko.usercenterdemo.common.BaseResponse;
import com.maiko.usercenterdemo.common.ErrorCode;

/**
 * 返回工具类
 * @author: Maiko7
 * @create: 2023-11-14-19:26
 */


/**
 * 虽然在某些情况下，直接返回数据对象也是可行的，但通过引入 BaseResponse，使得整个项目的返回风格更加统一，
 * 提高了代码的可维护性和可读性。同时，这种设计也有助于后续对返回结构的扩展和修改。
 *
 * 标准化返回结构： 使用 BaseResponse 可以定义一个标准的返回结构，使得前后端协作更加一致和易于理解。
 * 这种标准化的结构有助于统一处理返回结果的逻辑，包括错误处理、状态码的解释等。
 *
 * 更灵活的错误处理： 通过 BaseResponse，可以更灵活地处理错误信息。例如，在错误发生时，你可以通过BaseResponse
 * 返回更详细的错误信息，而不仅仅是简单的状态码。这对于前端或其他调用方来说，能够更清晰地了解发生了什么问题。
 */
public class ResultUtils {

    /**
     * 成功
     *
     * 你去看controller的loginUser，它传的是一个user，也就是说传的是一个对象给前端。response记得吗
     *
     * @param data  这个T data 就是user就是一个对象
     * @param <T>  你由这里你也看的出来这个T是一个参数
     * @return  BaseResponse<T> 返回的是这个，不是T
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "success");
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * 失败 - 消息和描述
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败 - 描述无消息
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }
}
