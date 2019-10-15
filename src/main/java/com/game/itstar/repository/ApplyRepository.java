package com.game.itstar.repository;

import com.game.itstar.entity.Apply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/12  15:40
 * @Desc
 */
public interface ApplyRepository extends CrudRepository<Apply, Integer> {
    @Query("select  a.gameId from Apply a where a.status =:status")
    List<Integer> findByStatus(Integer status);
}
