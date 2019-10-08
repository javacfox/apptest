package com.game.itstar.repository;

import com.game.itstar.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/9/26  11:02
 * @Desc
 */
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    @Query("select p.parentId from Menu p where p.id in :ids and p.isMoudle = 1")
    List<Integer> findParentIdByIdIn(@Param("ids") Collection<Integer> ids);

    List<Menu> findAllByIdIn(Collection<Integer> ids);
}
