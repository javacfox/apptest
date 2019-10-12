package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.entity.Apply;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.ApplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  15:27
 * @Desc 报名
 */
@RestController
@RequestMapping("/api/apply")
public class ApplyController extends BaseController {
    @Autowired
    private ApplyServiceImpl applyService;

    @PostMapping("")
    private Object create(@RequestBody Apply apply, HttpServletRequest request) {
        try {
            return ResEntity.success(applyService.create(apply, request));
        } catch (Exception e) {
            return ResEntity.failed(e);
        }
    }

}
