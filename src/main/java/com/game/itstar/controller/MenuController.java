package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.entity.User;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.MenuServiceImpl;
import com.game.itstar.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/9/26  10:42
 * @Desc 权限
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuServiceImpl permissionService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 获取菜单
     *
     * @return
     */
    @GetMapping("")
    public Object getMenu(HttpServletRequest request) {
        try {
            User user = userService.getAuthUser(request);
            if (user == null) {
                return ResEntity.failed(new Exception("该用户未登录!请确认!"));
            }
            return ResEntity.success(permissionService.getMenu(user.getId()));
        } catch (Exception ex) {
            return ResEntity.failed(ex);
        }
    }
}
