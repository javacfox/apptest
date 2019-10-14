package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.RoleCriteria;
import com.game.itstar.entity.Role;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @Author 朱斌
 * @Date 2019/9/26  9:25
 * @Desc 角色管理
 */
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object get(@PathVariable Integer id) {
        return ResEntity.success(roleService.get(id));
    }

    /**
     * 角色列表
     *
     * @param roleCriteria
     * @return
     */
    @GetMapping("")
    public Object findByPage(RoleCriteria roleCriteria) {
        PageBean pageBean = super.getPageBean();
        roleService.findByPage(pageBean, roleCriteria);
        return ResEntity.success(pageBean);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody Role role) {
        return ResEntity.success(roleService.create(role));
    }

    /**
     * 修改角色
     *
     * @param id
     * @param role
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public Object update(@Valid @PathVariable Integer id, @RequestBody Role role) {
        return ResEntity.success(roleService.update(id, role));
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @DeleteMapping("")
    public Object delete(@RequestBody Collection<Integer> ids) {
        return ResEntity.success(roleService.delete(ids));
    }

    /**
     * 角色权限设置
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/{roleId:\\d+}/role-menu")
    public Object setUserRole(@Valid @PathVariable Integer roleId, @RequestBody Collection<Integer> menuIds) {
        return ResEntity.success(roleService.setRolePermission(roleId, menuIds));
    }
}
