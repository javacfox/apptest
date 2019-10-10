package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.entity.User;
import com.game.itstar.response.ResEntity;
import com.game.itstar.response.ResException;
import com.game.itstar.response.ResStatus;
import com.game.itstar.service.serviceImpl.EmailServiceImpl;
import com.game.itstar.service.serviceImpl.UserServiceImpl;
import com.game.itstar.utile.Validator;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author 朱斌
 * @Date 2019/9/24  14:01
 * @Desc 用户管理
 */
@RestController
@RequestMapping("/api/auth")
public class UserController extends BaseController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmailServiceImpl emailService;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Object register(@Validated @RequestBody User user, HttpServletResponse response) {
        try {
            Validator validator = Validator.create()
                    .notEmpty("用户名", user.getUserName())
                    .notEmpty("密码", user.getPassword())
                    .notEmpty("确认密码", user.getPassword1())
                    .notEmpty("验证码", user.getCode())
                    .email(user.getEmail());

            if (!validator.valid()) {
                response.setStatus(ResStatus.BAD_REQUEST.getCode());
                return ResEntity.failed(new ResException(validator.getMessage()), ResStatus.BAD_REQUEST);
            }
            if (emailService.checkSMSCode(user.getEmail(), user.getCode())) {
                return ResEntity.success(userService.register(user));
            }
            return ResEntity.failed(new ResException("验证码错误"), ResStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(ResStatus.FAILED.getCode());
            return ResEntity.failed(e);
        }
    }

    /**
     * 登录
     *
     * @param request
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Object login(HttpServletRequest request, @ApiParam("账号") @RequestParam String loginName,
                        @ApiParam("密码") @RequestParam String password) {
        try {
            return ResEntity.success(userService.login(request, loginName, password));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }

    /**
     * 安全退出
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/logout")
    public Object logout(HttpSession session) {
        session.invalidate();
        return ResEntity.success(session.getId());
    }

    /**
     * 发送邮箱验证码
     *
     * @param
     * @return
     */
    @PostMapping("/send_code")
    public Object sendSMSCode(@RequestBody Map<String, String> m) {
        try {
            String sender = m.get("email");
            Validator validator = Validator.create()
                    .notNull("邮箱", sender).email(sender);
            if (!validator.valid()) {
                return ResEntity.failed(new ResException(validator.getMessage()), ResStatus.BAD_REQUEST);
            }
            return ResEntity.success(emailService.sendHtmlEmailCZ(sender));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }



}
