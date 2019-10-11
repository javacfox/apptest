package com.game.itstar.repository;

import com.game.itstar.entity.UserTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/9  8:55
 * @Desc 用户战队关联关系
 */
public interface UserTeamRepository extends CrudRepository<UserTeam, Integer> {
    boolean existsByTeamIdAndUserId(Integer teamId, Integer id);

    UserTeam findByUserIdAndTeamId(Integer id,Integer teamId);
}
