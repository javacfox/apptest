package com.game.itstar.service.serviceImpl;

import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.GameCriteria;
import com.game.itstar.entity.Game;
import com.game.itstar.entity.User;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.GameRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.GameService;
import com.game.itstar.utile.DateExtendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author 朱斌
 * @Date 2019/10/12  14:38
 * @Desc 比赛管理
 */
@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommonRepository commonRepository;

    /**
     * 新增比赛
     *
     * @param game
     * @param request
     * @return
     */
    @Override
    public Game create(Game game, HttpServletRequest request) {
        User user = userService.getAuthUser(request);
        if (user == null) {
            throw new ResException("该用户尚未登录!");
        }

        if (user.getUserType().equals(RegisterType.USER.getValue())) {
            throw new ResException("该用户不是管理员,无法创建比赛!");
        }

        return gameRepository.save(game);
    }

    /**
     * 分页查询比赛相关信息
     *
     * @param pageBean
     * @param gameCriteria
     */
    @Override
    public void findByPage(PageBean pageBean, GameCriteria gameCriteria) {
        pageBean.setEntityName("Game g");
        pageBean.setSelect("select g");
        commonRepository.findByPage(pageBean, gameCriteria);

        List<Game> gameList = pageBean.getData();

        List<Map> maps = new ArrayList();

        gameList.stream().forEach(x -> {
            Map<String, Object> map = new HashMap<>();

            Date begin = x == null ? null : x.getBeginAt();
            Date beginAt = DateExtendUtil.dateToDate(begin, DateExtendUtil.TIME_NO_SECOND);

            map.put("id", x == null ? null : x.getId());//比赛id
            map.put("name", x == null ? null : x.getName());//比赛名字
            map.put("type", x == null ? null : x.getType());//比赛类型 1-和平精英 2-王者荣耀 3-英雄联盟
            map.put("division", x == null ? null : x.getDivision());// 赛区 1-QQ赛区 2-微信赛区
            map.put("model", x == null ? null : x.getModel());// 比赛模式 1-战队模式 2-个人模式
            map.put("beginAt", beginAt);//开赛时间

            maps.add(map);
        });

        //使用抽取的集合作为分页数据
        pageBean.setData(null);//先清除
        pageBean.setData(maps);
    }
}
