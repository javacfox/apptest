package com.game.itstar.service.serviceImpl;

import com.game.itstar.entity.Team;
import com.game.itstar.entity.UserTeam;
import com.game.itstar.repository.TeamRepository;
import com.game.itstar.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 朱斌
 * @Date 2019/9/29  14:48
 * @Desc 战队
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Team create(Team team) {
        team.setCount(1);
        teamRepository.save(team);

        // 绑定关联关系
        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(team.getId());

        return team;
    }
}
