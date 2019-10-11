package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.AdmissionCriteria;
import com.game.itstar.entity.Admission;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.AdmissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：朱斌
 * 创建时间：2019/10/10 10:28
 * 版本：1.0
 * 描述：审核申请
 */
@RestController
@RequestMapping("api/admission")
public class AdmissionController extends BaseController {

    @Autowired
    AdmissionServiceImpl admissionService;

    /**
     * 战队分页信息
     *
     * @param admissionCriteria
     * @return
     */
    @GetMapping("")
    public Object findByPage(AdmissionCriteria admissionCriteria,HttpServletRequest request) {
        PageBean pageBean = super.getPageBean();
        admissionService.findByPage(pageBean, admissionCriteria,request);
        return ResEntity.success(pageBean);
    }

    /**
     * 申请加入战队
     *
     * @param admission
     * @param request
     * @return
     */
    @PostMapping("")
    public Object applyTeam(@Validated @RequestBody Admission admission, HttpServletRequest request) {
        return ResEntity.success(admissionService.applyTeam(admission, request));
    }

    /**
     * 审核申请
     *
     * @param id
     * @param admission
     * @return
     */
    @PutMapping("/{id}")
    public Object audit(@PathVariable Integer id, @RequestBody Admission admission,HttpServletRequest request) {
        return ResEntity.success(admissionService.auditTeam(id, admission,request));
    }
}
