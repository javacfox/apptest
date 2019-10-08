package com.game.itstar.service;

import com.game.itstar.entity.Menu;

import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/9/26  10:56
 * @Desc
 */
public interface MenuService {
    List<Menu> getMenu(Integer userId);
}
