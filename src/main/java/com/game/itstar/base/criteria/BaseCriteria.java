package com.game.itstar.base.criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 朱斌
 * @Date 2019/6/25  15:00
 * @Desc
 */
public abstract class BaseCriteria {
    protected Map<String, Object> values = new HashMap();

    public BaseCriteria() {
    }

    public abstract String getCondition();

    public Map<String, Object> getValues() {
        return this.values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
