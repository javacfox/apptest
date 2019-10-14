package com.game.itstar.service;

import com.game.itstar.base.util.PageBean;
import com.game.itstar.entity.Reward;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  16:46
 * @Desc
 */
public interface RewardService {
    Reward create(Reward reward, HttpServletRequest request);
}
