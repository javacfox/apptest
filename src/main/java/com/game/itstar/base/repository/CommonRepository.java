package com.game.itstar.base.repository;

import com.game.itstar.base.criteria.BaseCriteria;
import com.game.itstar.base.entity.BaseEntity;
import com.game.itstar.base.util.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/6/25  15:04
 * @Desc
 */
public interface CommonRepository {
    <T> T find(Class var1, Serializable var2);

    List find(String var1);

    List find(String var1, BaseCriteria var2);

    List find(String var1, Map<String, Object> var2);

    void findByPage(String var1, Map<String, Object> var2, Integer var3, Integer var4);

    void findByPage(PageBean var1);

    void findByPage(PageBean var1, BaseCriteria var2);

    void findByPage(PageBean var1, Map<String, Object> var2);

    List findByProperty(Class var1, String var2, Object var3);

    List findByPropertyIgnoreCase(Class var1, String var2, String var3);

    List findBySQL(String var1, Class var2);

    List findBySQL(String var1, Class var2, BaseCriteria var3);

    List findBySQL(String var1, Class var2, Map<String, Object> var3);

    List findBySQL(String var1);

    List findBySQL(String var1, BaseCriteria var2);

    List findBySQL(String var1, Map<String, Object> var2);

    <T> T merge(BaseEntity var1);
}
