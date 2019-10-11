package com.game.itstar.repository;

import com.game.itstar.entity.Team;
import com.game.itstar.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/8  11:20
 * @Desc 战队
 */
public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team getById(Integer id);

    boolean existsByTeamCode(String code);

    Team findByTeamCode(String code);

    List<Team> findAllByIdIn(List<Integer> teamIds);
}
