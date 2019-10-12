package com.game.itstar.service;

import com.game.itstar.entity.Apply;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  15:29
 * @Desc
 */
public interface ApplyService {
    Apply create(Apply apply, HttpServletRequest request);
}
