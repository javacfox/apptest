package com.game.itstar.service;

import com.game.itstar.entity.Game;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  14:38
 * @Desc
 */
public interface GameService {
    Game create(Game game, HttpServletRequest request);
}
