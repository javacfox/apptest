package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.entity.User;
import com.game.itstar.response.ResEntity;
import com.game.itstar.response.ResStatus;
import com.game.itstar.service.serviceImpl.UserServiceImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Object register(@Validated @RequestBody User user, HttpServletResponse response) {
        try {
            return ResEntity.success(userService.register(user));
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
}
