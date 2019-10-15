package com.game.itstar.service;

import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.GameCriteria;
import com.game.itstar.entity.Game;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author 朱斌
 * @Date 2019/10/12  14:38
 * @Desc
 */
public interface GameService {
    Game create(Game game, HttpServletRequest request);

    void findByPage(PageBean pageBean, GameCriteria gameCriteria);

    Map<String,Object> findOne(Integer id);
}
