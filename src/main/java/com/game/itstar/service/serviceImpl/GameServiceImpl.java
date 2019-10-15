package com.game.itstar.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.itstar.base.repository.CommonRepository;
import com.game.itstar.base.util.PageBean;
import com.game.itstar.criteria.GameCriteria;
import com.game.itstar.entity.Apply;
import com.game.itstar.entity.Game;
import com.game.itstar.entity.Reward;
import com.game.itstar.entity.User;
import com.game.itstar.enums.RegisterType;
import com.game.itstar.repository.ApplyRepository;
import com.game.itstar.repository.GameRepository;
import com.game.itstar.repository.RewardRepository;
import com.game.itstar.response.ResException;
import com.game.itstar.service.GameService;
import com.game.itstar.utile.DateExtendUtil;
import com.game.itstar.utile.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private RewardRepository rewardRepository;


    /**
     * 新增比赛 -- 管理员身份
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
     * 分页查询比赛相关信息 -- 所有用户
     *
     * @param pageBean
     * @param gameCriteria
     */
    @Override
    public void findByPage(PageBean pageBean, GameCriteria gameCriteria) {
        Integer status = gameCriteria.getStatus();
        String today = gameCriteria.getToday();
        Helpers.requireNonNull("缺少必要的参数,请联系管理员", status, today);
        List<Integer> gameIds = applyRepository.findByStatus(status);
        if (gameIds.size() == 0) {
            return;
        }
        gameCriteria.setIds(gameIds);

        pageBean.setEntityName("Game g");
        pageBean.setSelect("select g");
        commonRepository.findByPage(pageBean, gameCriteria);

        // 查找奖励
        List<Reward> rewards = rewardRepository.findByGameIdIn(gameIds);
        if (rewards.size() == 0) {
            return;
        }

        List<Game> gameList = pageBean.getData();

        List<Map> maps = new ArrayList();

        gameList.stream().forEach(x -> {
            Map<String, Object> map = new HashMap<>();
//            奖励
            Reward reward = rewards.stream()
                    .filter(y -> x.getId().equals(y.getGameId())).findFirst().orElse(null);

            // 时间处理,只取时分 HH:mm
            Date begin = x == null ? null : x.getBeginAt();
            String now = DateExtendUtil.dateToString(begin, DateExtendUtil.TIME_NO_SECOND);
            Timestamp beginAt = Timestamp.valueOf(now); // 转换成时间戳

            map.put("id", x == null ? null : x.getId());//比赛id
            map.put("name", x == null ? null : x.getName());//比赛名字
            map.put("type", x == null ? null : x.getType());//比赛类型 1-和平精英 2-王者荣耀 3-英雄联盟
            map.put("division", x == null ? null : x.getDivision());// 赛区 1-QQ赛区 2-微信赛区
            map.put("model", x == null ? null : x.getModel());// 比赛模式 1-战队模式 2-个人模式
            map.put("beginAt", beginAt);//开赛时间

            // 奖励
            JSONObject json = JSON.parseObject(reward == null ? null : reward.getRemark());
            String firstMoney = json.getString("firstMoney");
            map.put("firstMoney", firstMoney);// 奖金,最高的奖金数
            maps.add(map);
        });

        //使用抽取的集合作为分页数据
        pageBean.setData(null);//先清除
        pageBean.setData(maps);
    }

    /**
     * 比赛详情
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findOne(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Game game = gameRepository.getById(id);
        if (game == null) {
            return map;
        }

        Apply apply = applyRepository.getByGameId(id);
        if (apply == null) {
            return map;
        }

        Reward reward = rewardRepository.getByGameId(id);
        if (reward == null) {
            return map;
        }

        // 时间处理,只取时分 HH:mm
        Date begin = game.getBeginAt();
        String now = DateExtendUtil.dateToString(begin, DateExtendUtil.TIME_NO_SECOND);
        Timestamp beginAt = Timestamp.valueOf(now); // 转换成时间戳 -- 比赛开始时间

        game.setBeginAt(beginAt);
        map.put("game", game);// 比赛相关详情

        Date endAt1 = apply.getEndAt(); // 报名结束时间
        String endAt2 = DateExtendUtil.dateToString(endAt1, DateExtendUtil.TIME_NO_SECOND);
        Timestamp endAt = Timestamp.valueOf(endAt2); // 转换成时间戳
        map.put("apply", endAt);// 报名截止时间

        JSONObject json = JSON.parseObject(reward.getRemark());
        map.put("reward", json); // 奖励

        return map;
    }


}
