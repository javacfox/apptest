package com.game.itstar.service.serviceImpl;

import com.game.itstar.entity.Reward;
import com.game.itstar.entity.User;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.RewardRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  16:45
 * @Desc
 */
@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RewardRepository rewardRepository;

    @Override
    public Reward create(Reward reward, HttpServletRequest request) {
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户尚未登录!");
        }

        if (user.getUserType().equals(RegisterType.USER.getValue())) {
            throw new ResException("该用户不是管理员,无法创建比赛!");
        }

        return rewardRepository.save(reward);
    }
}
