package com.game.itstar.controller;

import com.game.itstar.entity.Team;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        try {
            return ResEntity.success(teamService.create(team, request));
        } catch (Exception ex) {
            return ResEntity.failed(ex);
        }
    }

    /**
     * 生成二维码
     *
     * @param team
     * @param type 1-不需要审核 2-需要审核
     * @return
     */
//    @PutMapping("/qrcode")
//    public Object createQRcode(@RequestBody Team team, Integer type, HttpServletResponse response) {
//        try {
//            return ResEntity.success(teamService.createQRcode(team, type, response));
//        } catch (Exception ex) {
//            return ResEntity.failed(ex);
//        }
//    }

    /**
     * 查找一条
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object findOne(@PathVariable Integer id) {
        return ResEntity.success(teamService.findOne(id));
    }

    /**
     * 生成二维码
     *
     * @param id
     * @param type 1-不需要审核 2-需要审核
     * @return
     */
    @PutMapping("/code/{id}")
    public void createCode(@PathVariable Integer id, Integer type) {
        try {
            teamService.inviteIdCode(id, type);
        } catch (Exception ex) {
            ResEntity.failed(ex);
        }
    }
}
