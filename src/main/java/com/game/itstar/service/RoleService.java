package com.game.itstar.service;


import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.RoleCriteria;
import com.game.itstar.entity.Role;
import com.game.itstar.entity.RoleMenu;

import java.util.Collection;
import java.util.List;

//import com.game.itstar.criteria.RoleCriteria;

/**
 * @Author 朱斌
 * @Date 2019/9/26  9:28
 * @Desc
 */
public interface RoleService {
    Role get(Integer id);

    void findByPage(PageBean pageBean, RoleCriteria roleCriteria);

    Role create(Role role);

    Role update(Integer id, Role role);

    Integer delete(Collection<Integer> ids);

    List<RoleMenu> setRolePermission(Integer roleId, Collection<Integer> permissionIds);

}
