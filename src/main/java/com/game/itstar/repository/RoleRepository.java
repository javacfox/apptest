package com.game.itstar.repository;

import com.game.itstar.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @Author 朱斌
 * @Date 2019/9/26  9:34
 * @Desc
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getRoleById(Integer id);

    Integer deleteAllByIdIn(Collection<Integer> ids);

    Role findByType(Integer type);
}
