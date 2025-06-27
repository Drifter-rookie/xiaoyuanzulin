package com.idleItem.tradeSystem.controller;


import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.UserService;
import com.idleItem.tradeSystem.utils.JwtUtils;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 注册账号
     * @param user
     * @return
     */
    @PostMapping("sign-in")
    public ResultVo signIn(@RequestBody User user) {
        System.out.println(user);
        user.setSignInTime(new Timestamp(System.currentTimeMillis()));
        if (user.getAvatar() == null || "".equals(user.getAvatar())) {
            user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        }
        if (userService.userSignIn(user)) {
            return ResultVo.success(user);
        }
        return ResultVo.fail(ErrorMsg.REGISTER_ERROR);
    }
    /**
     * 登录，不安全，可伪造id，后期改进
     *
     * @param accountNumber
     * @param userPassword
     * @param response
     * @return
     */
    @RequestMapping("login")
    public ResultVo login(@RequestParam("accountNumber") @NotEmpty @NotNull String accountNumber,
                          @RequestParam("userPassword") @NotEmpty @NotNull String userPassword,
                          HttpServletResponse response) {
        User user = userService.userLogin(accountNumber, userPassword);
        System.out.println("登录：" + user);
        if (null == user) {
            return ResultVo.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
        if(user.getUserStatus()!=null&&user.getUserStatus().equals((byte) 1)){
            return ResultVo.fail(ErrorMsg.ACCOUNT_Ban);
        }
        
        // 生git clean -fdx成JWT令牌
        String token = jwtUtils.generateToken(user.getNickname(), user.getId(), "USER");
        
        // 创建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);
        
        // 仍然保留之前的Cookie（可选）
        Cookie cookie = new Cookie("shUserId", String.valueOf(user.getId()));
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        
        // 设置JWT令牌到响应头
        response.setHeader("Authorization", "Bearer " + token);
        
        return ResultVo.success(result);
    }
    /**
     * 退出登录
     *
     * @param shUserId
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public ResultVo logout(@CookieValue("shUserId")
                           @NotNull(message = "登录异常 请重新登录")
                           @NotEmpty(message = "登录异常 请重新登录") String shUserId, HttpServletResponse response) {
        Cookie cookie = new Cookie("shUserId", shUserId);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResultVo.success();
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    public ResultVo getOneUser(@CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
                               @NotEmpty(message = "登录异常 请重新登录")
                                       String id) {
        return ResultVo.success(userService.getUser(Long.valueOf(id)));
    }

    @GetMapping("userinfo")
    public ResultVo getuser(String id){
        return ResultVo.success(userService.getUser(Long.valueOf(id)));
    }

    /**
     * 修改用户公开信息
     * @param id
     * @param user
     * @return
     */
    @PostMapping("/info")
    public ResultVo updateUserPublicInfo(@CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
                                     @NotEmpty(message = "登录异常 请重新登录")
                                             String id, @RequestBody  User user) {
        user.setId(Long.valueOf(id));
        if (userService.updateUserInfo(user)) {
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }


    /**
     * 修改密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @GetMapping("/password")
    public ResultVo updateUserPassword(@CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
                                       @NotEmpty(message = "登录异常 请重新登录") String id,
                                       @RequestParam("oldPassword") @NotEmpty @NotNull String oldPassword,
                                       @RequestParam("newPassword") @NotEmpty @NotNull String newPassword) {
        if (userService.updatePassword(newPassword,oldPassword,Long.valueOf(id))) {
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.PASSWORD_RESET_ERROR);
    }
    @GetMapping("updateMessageStatus")
    public ResultVo updateMassageStatus(@NotEmpty(message = "登录异常 请重新登录") String id){
        if(userService.updatemessagestatus(Long.valueOf(id)))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("updateMessageStatus1")
    public ResultVo updateMassageStatus1(@NotEmpty(message = "登录异常 请重新登录") String id){
        if(userService.updatemessagestatus1(Long.valueOf(id)))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
