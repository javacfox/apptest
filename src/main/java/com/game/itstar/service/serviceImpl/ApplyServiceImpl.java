package com.game.itstar.service.serviceImpl;

import com.game.itstar.entity.Apply;
import com.game.itstar.entity.User;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.ApplyRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  15:28
 * @Desc 比赛报名赛程
 */
@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private UserServiceImpl userService;

    /**
     * 新增比赛报名赛程
     *
     * @param apply
     * @param request
     * @return
     */
    @Override
    public Apply create(Apply apply, HttpServletRequest request) {
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户尚未登录!");
        }

        if (user.getUserType().equals(RegisterType.USER.getValue())) {
            throw new ResException("该用户不是管理员,无法创建比赛!");
        }

        return applyRepository.save(apply);
    }
}
