package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.common.ErrorCode;
import com.maiko.usercenterdemo.exception.BusinessException;
import com.maiko.usercenterdemo.mapper.UserMapper;
import com.maiko.usercenterdemo.model.domain.User;
import com.maiko.usercenterdemo.model.request.user.UserSearchRequest;
import com.maiko.usercenterdemo.model.request.user.UserUpdatePasswordRequest;
import com.maiko.usercenterdemo.service.UserService;
import com.maiko.usercenterdemo.utils.PasswordEncoder;
import com.maiko.usercenterdemo.utils.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.maiko.usercenterdemo.constant.UserConstant.*;

/**
* @author Maiko7
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-14 16:03:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

//    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 账号长度不小于4位
        if (userAccount.length() < USERACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度小于4位");
        }
        // 密码不小于8位
        if (userPassword.length() < PASSWORD_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }
        // 星球编码长度不小于5位
        if (planetCode.length() < PLANTE_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号小于5位");
        }
        // 账号不能包含特殊字符
        if (!RegexUtils.isUserAccountInvalid(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含特殊字符");
        }
        // 密码和校验密码不一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        // 账户名称不能重复，查询数据库当中是否存在相同名称的账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号名称已存在");
        }
        // 编号不能重复
            // 你这里不new的话就连上面的条件都用上了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户编号已存在");
        }
        // 对密码进行加密
        String encryptPassword = PasswordEncoder.encode(userPassword);
        // 将数据插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存数据库失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能为空");
        }
        // 账号长度不小于4位
        if (userAccount.length() < USERACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度小于4位");
        }
        // 密码不小于8位
        if (userPassword.length() < PASSWORD_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于8位");
        }
        // 账号不能包含特殊字符
        if (!RegexUtils.isUserAccountInvalid(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含特殊字符");
        }
        /**
         * 走到这里说明账号和密码没问题
         */

//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("userAccount", userAccount);
//        queryWrapper.eq("userPassword", userPassword);
//        User user = userMapper.selectOne(queryWrapper);

//        User user = userMapper.findByUserAccount(userAccount);
//        if (user == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在");
//        }
//        String encryptPassword = user.getUserPassword();
//        if(!PasswordEncoder.matches(encryptPassword, userPassword)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不正确");
//        }

        // 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在或密码不正确");
        }
        // 用户信息脱敏
        User safetyUser = getSafetyUser(user);
        // 用户登陆成功，将登陆态设置到session当中
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);


        /**
         * 你看黑马就知道这里有问题
         */
//        Map<String, Object> userMap = BeanUtil.beanToMap(safetyUser);
//        // 生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
//        String token = IdUtil.simpleUUID();
//        String tokenKey = LOGIN_USER_KEY + token;
//        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
//        stringRedisTemplate.opsForValue().set(tokenKey, JSONUtil.toJsonStr(safetyUser));
        return safetyUser;
    }

    @Override
    public int userlogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        /**
         * User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
         *         return user;
         *
         *       你知道你错在哪吗？你返回的是脱敏的用户，而我要获取当前用户的所有信息
         */
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        Long userId = user.getId();
        /**
         * 你看它这里就是走的数据库，如果你会用缓存，这里就可以走缓存直接查出来
         * 这算不算一个优化点呢？
         */
        // todo 走缓存
        User currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        // 你直接返回就草率了
        return currentUser;

    }

    @Override
    public List<User> searchUsers(UserSearchRequest userSearchRequest) {
        String username = userSearchRequest.getUsername();
        String userAccount = userSearchRequest.getUserAccount();
        Integer gender = userSearchRequest.getGender();
        String phone = userSearchRequest.getPhone();
        String email = userSearchRequest.getEmail();
        Integer userStatus = userSearchRequest.getUserStatus();
        Integer userRole = userSearchRequest.getUserRole();
        Date createTime = userSearchRequest.getCreateTime();
        Date updateTime = userSearchRequest.getUpdateTime();
        String planetCode = userSearchRequest.getPlanetCode();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        /**
         * 使用 = 运算符（等于）进行精确匹配时，通常适用于数值类型的字段
         * 对于字符串类型的字段，一般使用 LIKE 运算符进行模糊匹配
         */
        // username
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        /**
         * 下面是拿userAccount和username去写的一个sql
         * SELECT * FROM your_table_name
         * WHERE userAccount LIKE '%john%'
         *   AND gender = 'male'
         */
        // userAccount
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        // gender
        if (gender != null) {
            queryWrapper.eq("gender", gender);
        }
        // phone
        if (StringUtils.isNotBlank(phone)) {
            queryWrapper.like("phone", phone);
        }
        // email
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        // userStatus
        if (userStatus != null) {
            queryWrapper.eq("userStatus", userStatus);
        }

        if (userRole != null) {
            queryWrapper.eq("userRole", userRole);
        }

        if (StringUtils.isNotBlank(planetCode)) {
            queryWrapper.eq("planetCode", planetCode);
        }

        if (updateTime != null) {
            queryWrapper.like("updateTime", updateTime);
        }
        if (createTime != null) {
            queryWrapper.like("createTime", createTime);
        }
        /**
         * 不能把所有的信息都暴露出来 脱敏
         *
         * 根据你提供的代码片段，首先使用 userService 对象调用 list(queryWrapper) 方法
         * 来获取符合条件的用户列表 userList。然后，通过流式操作对 userList 进行处理，使用
         * map() 方法将每个用户对象传递给 userService 的 getSafetyUser() 方法，得到安全用户对象，
         * 并将其收集到一个新的列表 users 中。
         *
         * 总结：
         * 1. 将流中的每个元素进行转换
         * 2. 对每个元素执行某种操作或者将其转换为另一种类型
         *
         * map() 方法用于将流中的每个元素进行转换，生成一个新的流。你可以使用 map() 方法
         * 来对每个元素执行某种操作或者将其转换为另一种类型。通常情况下，你会传递一个函数或者方法引用
         * 给 map() 方法，用于对每个元素进行转换。
         */
        List<User> userList = userService.list(queryWrapper);
        List<User> users = userList.stream().map(userService::getSafetyUser).collect(Collectors.toList());
        return users;
    }

    @Override
    public boolean updateUserPassword(UserUpdatePasswordRequest updatePasswordRequest, HttpServletRequest request) {
        if (updatePasswordRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = getLoginUser(request);
        Long userId = loginUser.getId();
        if (userId < 0 || userId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "不存在该用户");
        }
        User user = new User();
        BeanUtils.copyProperties(updatePasswordRequest, user);
        user.setId(loginUser.getId());

        // 使用MD5加密新密码
        String encryptPassword = PasswordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setUserPassword(encryptPassword);
        if (encryptPassword.equals(updatePasswordRequest.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "修改密码不能和原密码相同");
        }
        boolean result = this.updateById(user);
        //ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return result;
    }


    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    public User getSafetyUser(User originUser) {
        // 上来先判断参数 记住
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();

        // 通常，返回用户ID是常见的，通常是为了识别目的。只要不暴露任何敏感细节，它就不是敏感信息。
        /**
         * 你看获取当前用户的时候，获取的是脱敏的用户，他获取完整用户
         * 就是根据id去获取的，所以你说脱敏的时候要不要id。没id你怎么获取
         * 完整用户
         */
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        //  用户的电话号码通常被认为是敏感的。如果有必要，你可能考虑只返回最后几位数字以进行识别。在处理电话号码时，始终尊重用户的隐私。
//        safetyUser.setPhone(originUser.getPhone());
        // 与电话号码类似，电子邮件地址通常是敏感的。如果有必要，你可能考虑只返回电子邮件的一部分以进行识别。
//        safetyUser.setEmail(originUser.getEmail());
        // 用户状态异常还是正常，也可以返回。你应该看过
        safetyUser.setUserStatus(originUser.getUserStatus());
        // 通常可以安全返回，可以提供关于用户帐户创建或上次更新的有用信息。
        safetyUser.setCreateTime(originUser.getCreateTime());
        // 你想你手机app是不是展示你是学生而不是老师，这就是role可以返回
        safetyUser.setUserRole(originUser.getUserRole());
        // 鱼皮星球的编号 是不是也返回了
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }
}




