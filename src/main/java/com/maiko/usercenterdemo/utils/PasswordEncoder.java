package com.maiko.usercenterdemo.utils;


import cn.hutool.core.util.RandomUtil;
import com.maiko.usercenterdemo.common.ErrorCode;
import com.maiko.usercenterdemo.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 校验密码是否相同工具类
 * @author: Maiko7
 * @create: 2023-11-15-9:03
 */
public class PasswordEncoder {

    public static String encode(String password) {
        // 生成盐
        // RandomUtil.randomString 获得一个随机的字符串（只包含数字和字符）
        String salt = RandomUtil.randomString(20);
        return encode(password, salt);
    }

    /**
     * 它这个和鱼皮生成盐有什么区别知道吗？
     * 它这里利用的是随机数生成一个盐值，而鱼皮是在常量定义了一个盐值
     * 加密生成都是一样的都是DigestUtils.md5DigestAsHex，就是盐值选用的不同
     */
    public static String encode(String password, String salt) {
        // 加密
        return salt + "@" + DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
    }


    public static Boolean matches(String encodePassword, String rawPassword) {
        if (StringUtils.isAnyBlank(encodePassword, rawPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        if (!encodePassword.contains("@")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码格式不正确");
        }
        String[] arr = encodePassword.split("@");
        // 获取盐值
        String salt = arr[0];
        // 比较
        return encodePassword.equals(encode(rawPassword, salt));
    }

}
