package com.game.itstar.service;

import com.game.itstar.base.util.PageBean;
import com.game.itstar.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author 朱斌
 * @Date 2019/9/24  15:56
 * @Desc
 */
public interface UserService {
    String register(User user);

    Object login(HttpServletRequest request, String loginName, String password);

    String changePassword(Map<String, String> m);
}
