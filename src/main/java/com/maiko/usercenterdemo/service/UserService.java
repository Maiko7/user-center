package com.maiko.usercenterdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maiko.usercenterdemo.model.domain.User;
import com.maiko.usercenterdemo.model.request.UserSearchRequest;
import com.maiko.usercenterdemo.model.request.UserUpdatePasswordRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Maiko7
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-11-14 16:03:14
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @param planetCode
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);


    /**
     * 用户登陆
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @return  脱敏后的信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户注销
     * @param request
     * @return
     */
    int userlogout(HttpServletRequest request);

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 查询用户
     * @param userSearchRequest
     * @return
     */
    List<User> searchUsers(UserSearchRequest userSearchRequest);

    /**
     * 修改密码
     *
     * @param updatePasswordRequest
     * @param request
     */
    boolean updateUserPassword(UserUpdatePasswordRequest updatePasswordRequest, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);


}
