package com.game.itstar.repository;

import com.game.itstar.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/9/26  9:35
 * @Desc
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    Integer deleteAllByRoleIdIn(Collection<Integer> ids);

    @Query("select u.roleId from UserRole u where u.userId =:userId")
    List<Integer> findAllByUserId(@Param("userId") Integer userId);
}
