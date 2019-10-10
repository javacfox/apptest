package com.game.itstar.service;

import com.game.itstar.base.util.PageBean;
import com.game.itstar.entity.Team;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/9/29  14:48
 * @Desc
 */
public interface TeamService {
    Team create(Team team, HttpServletRequest request);

    Team findOne(Integer id);
}