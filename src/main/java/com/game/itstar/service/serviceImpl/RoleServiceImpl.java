package com.game.itstar.service.serviceImpl;

import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.RoleCriteria;
import com.game.itstar.entity.Role;
import com.game.itstar.entity.RoleMenu;
import com.game.itstar.repository.RoleMenuRepository;
import com.game.itstar.repository.RoleRepository;
import com.game.itstar.repository.UserRoleRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.RoleService;
import com.game.itstar.utile.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author 朱斌
 * @Date 2019/9/26  9:29
 * @Desc
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CommonRepository commonRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private MenuServiceImpl menuService;


    private HashMap<Integer, List<Integer>> permissionParentIds = new HashMap<>();

    /**
     * 获取角色详细信息
     *
     * @param id
     * @return
     */
    @Override
    public Role get(Integer id) {
        return roleRepository.getRoleById(id);
    }

    /**
     * 角色列表
     *
     * @param pageBean
     * @param roleCriteria
     */
    @Override
    public void findByPage(PageBean pageBean, RoleCriteria roleCriteria) {
        pageBean.setEntityName("Role r");
        pageBean.setSelect("select r");
        commonRepository.findByPage(pageBean, roleCriteria);

        List<Role> data = pageBean.getData();

        List<Map> maps = new ArrayList();

        data.stream().forEach(x -> {
            Map<String, Object> obj = new HashMap<>();
            List<Integer> permissionIdList = getPermissionIds(x.getId());
            obj.put("permissionIdList", permissionIdList);
            obj.put("id", x.getId());
            obj.put("roleName", x.getRoleName());
            maps.add(obj);
        });

        //使用抽取的集合作为分页数据
        pageBean.setData(null);//先清除
        pageBean.setData(maps);
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role update(Integer id, Role role) {
        role.setId(id);
        return commonRepository.merge(role);
    }

    @Override
    @Transactional
    public Integer delete(Collection<Integer> ids) {
        //  删除对应的关联数据
        userRoleRepository.deleteAllByRoleIdIn(ids);
        roleMenuRepository.deleteAllByRoleIdIn(ids);
        Integer count = roleRepository.deleteAllByIdIn(ids);
        return count;
    }

    @Override
    @Transactional
    public List<RoleMenu> setRolePermission(Integer roleId, Collection<Integer> menuIds) {
        Role role = get(roleId);
        if (role == null) {
            throw new ResException("角色不存在!请确认!");
        }

        // 查询已经配置的权限
        List<Integer> olePermissionIds = roleMenuRepository.findAllByRoleId(roleId);

        //  删除之前配置的权限数据
        roleMenuRepository.deleteAllByMenuIdInAndRoleId(olePermissionIds, roleId);

        //  储存菜单层级关联关系
        List<Integer> parentIds = menuService.getParentId(menuIds);
        if (parentIds != null && parentIds.size() > 0) {
            menuIds.addAll(parentIds);
            permissionParentIds.put(roleId, parentIds);
        }

        // 角色权限配置
        List<RoleMenu> rolePermissionList = new ArrayList<>();
        if (Helpers.isNotNullAndEmpty(roleId)) {
            menuIds.stream()
                    .filter(x -> menuIds.size() > 0)
                    .distinct()
                    .forEach(x -> {
                        RoleMenu rolePermission = new RoleMenu();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setMenuId(x);
                        roleMenuRepository.save(rolePermission);
                        rolePermissionList.add(rolePermission);
                    });
        }

        List<Integer> ids = new ArrayList<>();
        for (RoleMenu rolePermission : rolePermissionList) {
            ids.add(rolePermission.getId());
        }

        return rolePermissionList;
    }

    /**
     * 获取权限id集合
     *
     * @param roleId
     * @return
     */
    private List<Integer> getPermissionIds(Integer roleId) {
        List<Integer> permissionIds = roleMenuRepository.findAllByRoleId(roleId);

        List<Integer> permissionId = permissionParentIds.get(roleId);
        if (permissionId != null && permissionId.size() > 0) {
            permissionIds.removeAll(permissionId);
        }

        return permissionIds;
    }

}
