package com.game.itstar.service.serviceImpl;


import com.game.itstar.entity.Menu;
import com.game.itstar.repository.MenuRepository;
import com.game.itstar.repository.RoleMenuRepository;
import com.game.itstar.repository.UserRoleRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 朱斌
 * @Date 2019/9/26  10:56
 * @Desc
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Override
    public List<Menu> getMenu(Integer userId) {
        if (userId == null) {
            throw new ResException("该用户不存在,请确认!");
        }
//        User user = userService.getAuthUser(request);

        // 多种角色
        List<Integer> roleIds = userRoleRepository.findAllByUserId(userId);

        if (roleIds != null && roleIds.size() != 0) {
            List<Integer> permissionIds = roleMenuRepository.findMenuIdByRoleIdIn(roleIds);

            // 相对应的权限
            List<Menu> menus = menuRepository
                    .findAllByIdIn(permissionIds);

            if (menus.size() == 0) {
                throw new ResException("还未配置权限，请联系管理员!");
            }

            //  1.处理存储集合
            for (Menu menu : menus) {
                if (menu.getIsMoudle()) {
                    List<Menu> childList = menus.stream()
                            .filter(x -> menu.getId()
                                    .equals(x.getParentId())).collect(Collectors.toList());
                    menu.setChildren(childList);
                }
            }

            //  2.返回顶层数据
            return menus.stream().filter(x -> x.getParentId().equals(0)
                    && x.getIsMoudle()).collect(Collectors.toList());
        } else {
            throw new ResException("还未配置权限，请联系管理员!");
        }
    }

    public List<Integer> getParentId(Collection<Integer> ids) {
        return menuRepository.findParentIdByIdIn(ids).stream()
                .filter(x -> x != 0)
                .distinct().collect(Collectors.toList());
    }
}
