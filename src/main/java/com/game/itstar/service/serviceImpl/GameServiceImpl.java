package com.game.itstar.service.serviceImpl;

import com.game.itstar.entity.Game;
import com.game.itstar.entity.User;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.GameRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/12  14:38
 * @Desc 比赛管理
 */
@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Game create(Game game, HttpServletRequest request) {
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户尚未登录!");
        }

        if (user.getUserType().equals(RegisterType.USER.getValue())) {
            throw new ResException("该用户不是管理员,无法创建比赛!");
        }

        return gameRepository.save(game);
    }
}
