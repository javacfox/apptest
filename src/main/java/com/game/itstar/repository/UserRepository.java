package com.game.itstar.repository;

import com.game.itstar.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author 朱斌
 * @Date 2019/9/24  17:22
 * @Desc
 */
public interface UserRepository extends CrudRepository<User,Integer> {
    Integer countByPhoneNumber(String phoneNumber);

    Integer countByUserName(String userName);

    User findByUserName(String loginName);

    Integer countByEmail(String email);
}
