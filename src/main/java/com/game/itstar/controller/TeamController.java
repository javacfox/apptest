package com.game.itstar.controller;

import com.game.itstar.entity.Team;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("")
    public Object create(@RequestBody Team team){
        return ResEntity.success(teamService.create(team));
    }

}
