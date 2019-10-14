package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.entity.Reward;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.RewardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  16:44
 * @Desc 奖励
 */
@RestController
@RequestMapping("/api/reward")
public class RewardController extends BaseController {
    @Autowired
    private RewardServiceImpl rewardService;

    @PostMapping("")
    public Object create(@RequestBody Reward reward, HttpServletRequest request) {
        try {
            return ResEntity.success(rewardService.create(reward, request));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }
}
