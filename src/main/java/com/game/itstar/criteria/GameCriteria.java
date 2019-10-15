package com.game.itstar.criteria;

import com.game.itstar.base.criteria.BaseCriteria;
import com.game.itstar.utile.DateExtendUtil;
import com.game.itstar.utile.Helpers;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/14  15:26
 * @Desc 普通用户查看比赛信息
 */
@Setter
@Getter
public class GameCriteria extends BaseCriteria {
    private Integer status;
    private List<Integer> ids;//比赛id集合
    private String today;//查询的时间

    @Override
    public String getCondition() {
        values.clear();//清除条件数据
        StringBuilder condition = new StringBuilder();

        if (ids != null && ids.size() > 0) {
            condition.append("and id in :ids");
            values.put("ids", this.ids);
        }

        if (Helpers.isNotNullAndEmpty(this.today)) {
            Date today = DateExtendUtil.stringToDate(this.today, DateExtendUtil.YEAR);

            //查询时间在开始时间之后,不考虑开始时间为00:00的点
            condition.append(" and beginAt <= :today ");
            //恢复时间在查询时间之后,不考虑恢复时间为00:00的点
            condition.append(" and (endAt >= :today)");
            values.put("today", today);
        }
        return condition.toString();
    }
}
