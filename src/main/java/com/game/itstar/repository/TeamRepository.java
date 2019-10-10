package com.game.itstar.repository;

import com.game.itstar.entity.Team;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author 朱斌
 * @Date 2019/10/8  11:20
 * @Desc 战队
 */
public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team getById(Integer id);
}
