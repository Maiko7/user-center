package com.maiko.usercenterdemo.controller;

/**
 * @author: Maiko7
 * @create: 2023-11-14-16:13
 */

import com.maiko.usercenterdemo.common.BaseResponse;
import com.maiko.usercenterdemo.common.ErrorCode;
import com.maiko.usercenterdemo.exception.BusinessException;
import com.maiko.usercenterdemo.exception.ThrowUtils;
import com.maiko.usercenterdemo.model.domain.User;
import com.maiko.usercenterdemo.model.request.user.*;
import com.maiko.usercenterdemo.service.UserService;
import com.maiko.usercenterdemo.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.maiko.usercenterdemo.constant.UserConstant.ADMIN_ROLE;
import static com.maiko.usercenterdemo.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 你这下面大错特错！！！
 * @RestController("/user")
 * @RestController 注解用于标识一个类是 RESTful 风格的控制器，它相当于 @Controller 和 @ResponseBody 的组合
 * 它告诉 Spring MVC 框架将返回的数据直接写入 HTTP 响应体中，而不是视图解析器渲染为视图。
 * 这意味着返回的数据会以原始的形式（例如 JSON 或 XML）发送到客户端，而不是渲染为 HTML 视图。
 *
 *
 * 在这个例子中，hello 方法返回一个字符串，这个字符串会被视图解析器解析为实际的视图路径，最终渲染成 HTML 页面。
 * @Controller
 * public class MyController {
 *
 *     @RequestMapping("/hello")
 *     public String hello() {
 *         return "hello"; // 视图解析器会渲染为 "hello.jsp" 或 "hello.html"，具体取决于配置
 *     }
 * }
 *
 * 在这个例子中，hello 方法返回的字符串 "hello" 会直接成为 HTTP 响应的内容，
 * 而不会被视图解析器处理。如果你在浏览器中访问 "/hello"，你将看到 "hello" 字符串而不是渲染后的 HTML 页面。
 * @RestController
 * public class MyRestController {
 *
 *     @RequestMapping("/hello")
 *     public String hello() {
 *         return "hello"; // 返回的字符串直接作为 HTTP 响应体内容，不会经过视图解析器
 *     }
 * }
 *
 * @RequestMapping 注解用于定义请求映射
 *
 * 所以是下面这种！！
 */

@RestController
@RequestMapping("/user")
//@Api(tags="用户管理")
/**
 * 下面这段是跨域请求，如果你前后端地址不一致就可以打开下面这个
 * @CrossOrigin(origins = "http://user.kongshier.top/", allowCredentials = "true")
 * @CrossOrigin 注解被用于允许来自 "http://user.kongshier.top/" 域的跨域请求，并允许发送身份验证凭据（例如 cookies）。
 */
public class UserController {

    /**
     * @Resource是JavaEE的注解，而@Autowired是Spring的注解
     * @Resource是按照名称进行注入的，而@Autowired是按照类型进行注入的
     */
    @Resource
    private UserService userService;

//    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(userId);
    }


//    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    /**
     * public BaseResponse<User> userLogin 和 public User userLogin
     * 你自己看看哪个返回给前端比较友好？一个是只返回user，另一个是返回user和状态码和信息
     */
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 先判断参数是否合理,别上来整密码过长,拿到参数先校验参数是否为空。
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        /**
         * 你去看stroe的userController的返回。 return new JsonResult<User>(OK, data);
         * return new JsonResult<User>(OK, data); 你觉得这样好看吗？是不是鱼皮的更优雅一点，直接把一整个封装
         * 其实就是返回BaseResponse，但是你直接返回太难看了。我做个封装
         */
        return ResultUtils.success(user);
    }
    @PostMapping("/logout")
//    @ApiOperation(value = "退出登录")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        int logoutResult = userService.userlogout(request);
        return ResultUtils.success(logoutResult);
    }

    @GetMapping("/current")
//    @ApiOperation(value = "获取当前用户")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        // 获取登录态
        User resultUser = userService.getLoginUser(request);
        return ResultUtils.success(resultUser);
    }

    @PostMapping("/add")
//    @ApiOperation(value = "新增用户")
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        /**
         * 如果是之前的话就是
         * if (result == null) {
         *  throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
         * }
         * 他相当于把这一整个语句封装起来了。他的异常类和返回类都用了工具类去封装太优雅了
         */
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }


    @GetMapping("/search")
//    @ApiOperation(value = "查询用户")
    public BaseResponse<List<User>> searchUsers(UserSearchRequest userSearchRequest, HttpServletRequest request) {
        // 校验是不是管理员
        /**
         * 下面这一句相当于
         * if (!isAdmin(request)) {
         *             throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
         *         }
         */
        ThrowUtils.throwIf(!isAdmin(request), ErrorCode.NO_AUTH_ERROR, "无权限");
        /**
         * if (userSearchRequest == null) {
         *             throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
         *         }
         */
        ThrowUtils.throwIf(ObjectUtils.isEmpty(userSearchRequest),ErrorCode.NO_AUTH_ERROR);
        List<User> users = userService.searchUsers(userSearchRequest);
        return ResultUtils.success(users);
    }

    @PostMapping("/delete")
//    @ApiOperation(value = "删除用户")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        /**
         * 它这里为什么还要搞一个getId <= 0其实就是为了后续取id的时候有东西取
         * 和UserServiceImpl的一样
         * if (user == null || user.getId() == null) {
         *             throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
         *         }
         *         Long userId = user.getId();
         *    它这里判断user.getId()==null也是为了后续取的时候，有东西可以取
         */
        if (userDeleteRequest == null || userDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        boolean removeUser = userService.removeById(userDeleteRequest.getId());
        //ThrowUtils.throwIf(!removeUser, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(removeUser);
    }

    @PostMapping("/update")
//    @ApiOperation(value = "管理员更新用户")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        /**
         * 对应的SQL语句
         * UPDATE user SET column1 = value1, column2 = value2, ... WHERE id = ?
         */
        boolean result = userService.updateById(user);
        //ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    @PostMapping("/update/me")
    //@ApiOperation(value = "用户更新信息")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMeRequest userUpdateMeRequest, HttpServletRequest request) {
        if (userUpdateMeRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateMeRequest, user);
        /**
         * 其实和上面管理员更新用户一样，你要根据id来更新的话你要获取id
         * 但是你userUpdateMeRequest没有id，怎么办呢？通过request去拿到当前用户
         * 然后根据当前用户拿出id传给user
         */
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        //ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    @PostMapping("/update/password")
    @ApiOperation(value = "用户密码更改")
    public BaseResponse<Boolean> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
        }
        boolean result = userService.updateUserPassword(userUpdatePasswordRequest, request);
        //ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        Integer userRole = user.getUserRole();
        if (user == null || !user.getUserRole().equals(ADMIN_ROLE)) {
            return false;
        }
        return true;
    }
}
