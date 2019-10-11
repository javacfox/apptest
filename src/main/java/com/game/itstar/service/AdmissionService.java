package com.game.itstar.service;

import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.AdmissionCriteria;
import com.game.itstar.entity.Admission;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/11  13:14
 * @Desc
 */
public interface AdmissionService {
    void findByPage(PageBean pageBean, AdmissionCriteria admissionCriteria, HttpServletRequest request);

    Admission applyTeam(Admission admission, HttpServletRequest request);

    Admission auditTeam(Integer id, Admission admission, HttpServletRequest request);
}
