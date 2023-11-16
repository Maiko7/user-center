package com.maiko.usercenterdemo.utils;

/**
 * @author: Maiko7
 * @create: 2023-11-14-20:20
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 这一个正则类抽出来了校验手机、邮箱、验证码的格式
 * 它利用了字符串的matches匹配正则表达式，所以它还创建了一个正则类
 * @author 虎哥
 */
public class RegexUtils {
    /**
     * 是否是无效手机格式
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneInvalid(String phone){
        /**
         * 看见没有这就是那个正则类中的，电话号码的正则
         * 人家通过这个判断你的格式对不对有没有非法字符。
         */
        return mismatch(phone, RegexPatterns.PHONE_REGEX);
    }
    /**
     * 是否是无效邮箱格式
     * @param email 要校验的邮箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmailInvalid(String email){
        return mismatch(email, RegexPatterns.EMAIL_REGEX);
    }

    /**
     * 是否是无效验证码格式
     * @param code 要校验的验证码
     * @return true:符合，false：不符合
     */
    public static boolean isCodeInvalid(String code){
        return mismatch(code, RegexPatterns.VERIFY_CODE_REGEX);
    }

    /**
     * 是否包含特殊字符
     * @param code 要校验的账号
     * @return true:符合，false：不符合
     */
    public static boolean isUserAccountInvalid(String code){
        return mismatch(code, RegexPatterns.VALID_ACCOUNT_REGEX);
    }

    // 校验是否不符合正则格式
    private static boolean mismatch(String str, String regex){
        if (StringUtils.isBlank(str)) {
            return true;
        }
        /**
         * matches用于判断 str 是否符合指定的正则表达式 regex。它返回一个布尔值，
         * 如果字符串 str 符合正则表达式 regex，则返回 true，否则返回 false。
         * 这里就是要返回false，就是不符合的
         */
        return !str.matches(regex);
    }
}

