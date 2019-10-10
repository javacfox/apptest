package com.game.itstar.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.entity.*;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.*;
import com.game.itstar.response.ResException;
import com.game.itstar.service.UserService;
import com.game.itstar.utile.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.game.itstar.entity.Menu;

/**
 * @Author 朱斌
 * @Date 2019/9/24  14:54
 * @Desc 版本：1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${redis_key.user.expiration}")
    private Long userExpiration;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private CommonRepository commonRepository;
    @Value("${DEFAULT_MENUId}")
    private String defaultMenuId;
    @Autowired
    private MenuRepository menuRepository;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public String register(User user) {

        // 判断用户是否已经注册
        Integer count = userRepository.countByPhoneNumber(user.getPhoneNumber());
        if (count > 0) {
            throw new ResException("该手机号已经注册,请直接登录!");
        }

        //判断用户名唯一
        Integer count1 = userRepository.countByUserName(user.getUserName());
        if (count1 > 0) {
            throw new ResException("该用户名已经被使用,请重新输入!");
        }

        // 判断邮箱是否已经注册
        Integer count2 = userRepository.countByEmail(user.getEmail());
        if (count2 > 0) {
            throw new ResException("该邮箱已经被注册,请重核对!");
        }

        // 注册步骤
        String password = Helpers.isEmptyString(user.getPassword()) ? AttributeUtil.PASSWORD : user.getPassword();
        String password1 = Helpers.isEmptyString(user.getPassword1()) ? AttributeUtil.PASSWORD : user.getPassword1();
        byte[] salt = MD5ShaUtil.generateSalt(AttributeUtil.SALT_SIZE);
        byte[] hashPassword = MD5ShaUtil.sha1(password.getBytes(), salt, AttributeUtil.HASH_INTERATIONS);
        byte[] hashPassword1 = MD5ShaUtil.sha1(password1.getBytes(), salt, AttributeUtil.HASH_INTERATIONS);
        password = Encodes.encodeHex(hashPassword);
        password1 = Encodes.encodeHex(hashPassword1);
        if (!password.equals(password1)) {
            throw new ResException("登录密码和确认密不一致!");
        }

        user.setPassword(password);
        user.setPassword1(password1);
        user.setSalt(Encodes.encodeHex(salt));
        user.setActive(true);
        userRepository.save(user);
        setRoleMenu(user.getId(), user.getType());

        return "注册成功!";
    }

    /**
     * 登录
     *
     * @param request
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public Object login(HttpServletRequest request, String loginName, String password) {
        Helpers.requireNonNull("用户名或密码为空!", loginName, password);

        User user = userRepository.findByUserName(loginName);

        if (!Helpers.isNotNullAndEmpty(user)) {
            throw new ResException("该用户不存在");
        } else {
            if (!user.getActive()) {
                throw new ResException("该用户失效");
            } else if (!loginName.equals(user.getUserName())
                    && !validate(password, user.getPassword(), user.getSalt())) {
                throw new ResException("账号或密码错误");
            }
        }

        String key = CacheUtil.getUserKey(user.getId());
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", user.getId());

        redisTemplate.opsForValue().set(key, session.getId());
        redisTemplate.expire(key, userExpiration, TimeUnit.SECONDS);

        // 菜单
        List<Menu> menuList = menuService.getMenu(user.getId());
        return menuList;
    }

    /**
     * 修改密码
     *
     * @param m
     * @return
     */
    @Override
    @Transactional
    public String changePassword(Map<String, String> m) {
        // 通过邮箱找已经绑定的用户
        User user = userRepository.findByEmail(m.get("email"));
        if (user == null) {
            throw new ResException("该邮箱尚未注册,不能使用该邮箱找回密码!");
        }

        // 修改密码步骤
        String password = Helpers.isEmptyString(user.getPassword()) ? AttributeUtil.PASSWORD : user.getPassword();
        String password1 = Helpers.isEmptyString(user.getPassword1()) ? AttributeUtil.PASSWORD : user.getPassword1();
        byte[] salt = MD5ShaUtil.generateSalt(AttributeUtil.SALT_SIZE);
        byte[] hashPassword = MD5ShaUtil.sha1(password.getBytes(), salt, AttributeUtil.HASH_INTERATIONS);
        byte[] hashPassword1 = MD5ShaUtil.sha1(password1.getBytes(), salt, AttributeUtil.HASH_INTERATIONS);
        password = Encodes.encodeHex(hashPassword);
        password1 = Encodes.encodeHex(hashPassword1);
        if (!password.equals(password1)) {
            throw new ResException("登录密码和确认密不一致!");
        }

        user.setPassword(password);
        user.setPassword1(password1);
        user.setSalt(Encodes.encodeHex(salt));
        user.setActive(true);
        commonRepository.merge(user);
        setRoleMenu(user.getId(), user.getType());

        return "修改密码成功!";
    }

    /**
     * 检验密码的正确性
     *
     * @param plainPassword
     * @param password
     * @param salt
     * @return
     */
    private Boolean validate(String plainPassword, String password, String salt) {
        byte[] hashPassword = MD5ShaUtil.sha1(plainPassword.getBytes(),
                Encodes.decodeHex(salt), MD5ShaUtil.HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(hashPassword));
    }

    /**
     * 获取登录用户信息，redis缓存
     *
     * @return 用户信息
     */
    public User getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loginUserId");
        if (userId == null) {
            return null;
        }
        String key = CacheUtil.getUserKey(userId);
        Object userObj = redisTemplate.opsForValue().get(key);
        if (userObj == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(userObj.toString(), User.class);
        } catch (Exception ex) {
            return null;
        }
    }

    @Transactional
    public void setRoleMenu(Integer userId, Integer type) {
        List<Menu> menuList = (List<Menu>) menuRepository.findAll();
        if (menuList.size() == 0) {
            throw new ResException("目前没有权限,请配置权限!");
        }

        // 根据注册用户类型查角色
        Role role = roleRepository.findByType(type);
        UserRole userRole = new UserRole();
        RoleMenu roleMenu = new RoleMenu();
        if (role == null) {
            Role role1 = new Role();
            //默认角色权限配置
            if (type.equals(RegisterType.USER.getValue())) {
                role1.setRoleName(ConstantProperties.DEFAULT_ROLENAME);
                role1.setType(ConstantProperties.DEFAULT_ROLECODE);
                roleRepository.save(role1);

                // 用户角色关联关系
                userRole.setRoleId(role1.getId());
                userRole.setUserId(userId);
                userRoleRepository.save(userRole);

                // 角色权限关联关系
                List<Integer> listIds = Arrays.asList(defaultMenuId.split(",")).stream()
                        .map(s -> Integer.valueOf(s.trim())).collect(Collectors.toList());

                for (Integer menuId : listIds) {
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(role1.getId());
                    roleMenuRepository.save(roleMenu);
                }
            } else if (RegisterType.ADMIN.getValue().equals(type)) {
                role1.setRoleName(ConstantProperties.ADMIN_ROLENAME);
                role1.setType(ConstantProperties.ADMIN_ROLECODE);
                roleRepository.save(role1);

                // 用户角色关联关系
                userRole.setRoleId(role1.getId());
                userRole.setUserId(userId);
                userRoleRepository.save(userRole);

                // 角色权限关联关系
                for (Menu menu : menuList) {
                    roleMenu.setMenuId(menu.getId());
                    roleMenu.setRoleId(role1.getId());
                    roleMenuRepository.save(roleMenu);
                }
            }
        } else {
            // 配置用户角色关联关系
            userRole.setRoleId(role.getId());
            userRole.setUserId(userId);
            userRoleRepository.save(userRole);

            // 配置角色权限关联关系
            for (Menu menu : menuList) {
                roleMenu.setMenuId(menu.getId());
                roleMenu.setRoleId(role.getId());
                roleMenuRepository.save(roleMenu);
            }
        }
    }


}
