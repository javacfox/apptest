package com.game.itstar.service;

import com.game.itstar.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/9/24  15:56
 * @Desc
 */
public interface UserService {
    String register(User user);

    Object login(HttpServletRequest request, String loginName, String password);
}
