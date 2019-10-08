package com.game.itstar.repository;

import com.game.itstar.entity.RoleMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/9/26  10:23
 * @Desc
 */
public interface RoleMenuRepository extends CrudRepository<RoleMenu, Integer> {
    Integer deleteAllByRoleIdIn(Collection<Integer> ids);

    @Query("select r.menuId from RoleMenu r where r.roleId =:roleId")
    List<Integer> findAllByRoleId(Integer roleId);

    Integer deleteAllByMenuIdInAndRoleId(Collection<Integer> olePermissionIds, Integer roleId);

    @Query("select r.menuId from RoleMenu r where r.roleId in:roleIds")
    List<Integer> findMenuIdByRoleIdIn(@Param("roleIds") List<Integer> roleIds);
}
