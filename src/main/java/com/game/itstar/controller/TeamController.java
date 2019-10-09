package com.game.itstar.controller;

import com.game.itstar.entity.Team;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/9/29  14:46
 * @Desc 战队管理
 */
@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamServiceImpl teamService;

    /**
     * 创建新战队
     *
     * @param team
     * @param request
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody Team team, HttpServletRequest request) {
        return ResEntity.success(teamService.create(team, request));
    }

}
