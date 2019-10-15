package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.GameCriteria;
import com.game.itstar.entity.Game;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.GameServiceImpl;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  13:53
 * @Desc 比赛管理
 */
@RestController
@RequestMapping("/api/game")
public class GameController extends BaseController {
    @Autowired
    private GameServiceImpl gameService;

    /**
     * 新增比赛
     *
     * @param game
     * @param request
     * @return
     */
    @PostMapping("")
    public Object create(Game game, HttpServletRequest request) {
        try {
            return ResEntity.success(gameService.create(game, request));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }

    /**
     * 比赛列表--用户可以见
     *
     * @param gameCriteria
     * @return
     */
    @GetMapping("")
    public Object findByPage(GameCriteria gameCriteria) {
        PageBean pageBean = super.getPageBean();
        gameService.findByPage(pageBean, gameCriteria);
        return ResEntity.success(pageBean);
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable Integer id) {
        try {
            return ResEntity.success(gameService.findOne(id));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }
}
