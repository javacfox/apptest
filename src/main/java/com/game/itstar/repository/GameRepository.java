package com.game.itstar.repository;

import com.game.itstar.entity.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author 朱斌
 * @Date 2019/10/12  14:48
 * @Desc 比赛
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
    Game getById(Integer id);
}
