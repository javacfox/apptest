package com.game.itstar.repository;

import com.game.itstar.entity.Reward;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/14  15:21
 * @Desc
 */
public interface RewardRepository extends CrudRepository<Reward, Integer> {
    List<Reward> findByGameIdIn(List<Integer> gameIds);

    Reward getByGameId(Integer id);
}
