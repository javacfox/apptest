package com.game.itstar.criteria;

import com.game.itstar.base.criteria.BaseCriteria;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AdmissionCriteria extends BaseCriteria implements Serializable {
    private Integer teamId;
    private Integer status;
    private Integer userId;
    private Integer flag;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();

        if (this.teamId != null) {
            condition.append(" and teamId = :teamId");
            values.put("teamId", this.teamId);
        }
        if (this.status != null) {
            condition.append(" and status = :status");
            values.put("status", this.status);
        }
        if (this.userId != null) {
            condition.append(" and userId = :userId");
            values.put("userId", this.userId);
        }

        return condition.toString();
    }
}
