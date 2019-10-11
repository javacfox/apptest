package com.game.itstar.repository;

import com.game.itstar.entity.Admission;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author 朱斌
 * @Date 2019/10/11  14:16
 * @Desc
 */
public interface AdmissionRepository extends CrudRepository<Admission, Integer> {
    boolean existsByTeamIdAndUserId(Integer teamId, Integer id);

    Admission getById(Integer id);
}
