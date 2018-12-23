package com.liu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liu
 * @date 2018/12/23 15:45
 */
public interface UserRepository extends JpaRepository<User,String>{

    //模糊查询
    User findByNameContaining(String name);

    //根据字段查询
    User findByEmailAndPassword(String email,String password);

}
