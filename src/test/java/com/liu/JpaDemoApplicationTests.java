package com.liu;

import com.liu.dto.UserDTO;
import com.liu.jpa.QUser;
import com.liu.jpa.Role;
import com.liu.jpa.User;
import com.liu.jpa.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.val;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public  class JpaDemoApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void getTest() {
        //查询全部数据
        log.info("结果为 ->"+userRepository.findAll().toString());

        //根据id查询
        String id = "";
        log.info("结果为 ->"+userRepository.findById(id).toString());

        //根据set值查询
        val user = User.builder()
                .name("新垣结衣")
                .role(Role.User)
                .build();
        Example<User> example = Example.of(user);
        log.info("结果为 ->"+userRepository.findAll(example).toString());

        String name = "结";
        //模糊查询
        log.info("结果为 ->"+userRepository.findByNameContaining(name).toString());
        //根据字段查询
        String email = "user@**.com";
        String password = "123";
        log.info("结果为 ->"+userRepository.findByEmailAndPassword(email,password).toString());

    }

    //根据条件分页
    public Page<User> getUser(UserDTO dto, Pageable pageable){
        val qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(dto.getName())){
            //模糊查询 忽略大小写
            builder.and(qUser.name.containsIgnoreCase(dto.getName()));
        }
        if(!StringUtils.isEmpty(dto.getEmail())){
            builder.and(qUser.email.eq(dto.getEmail()));
        }
        val User = jpaQueryFactory
                .selectFrom(qUser)
                .where(builder);
        val count = User.fetchCount();
        val page = User
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(page, pageable, count);
    }

    @Test
    public void addTest() {
        val user = User.builder()
                .id(uuid())
                .email("user@**.com")
                .password("123")
                .name("新垣结衣")
                .role(Role.User)
                .build();
        userRepository.save(user);
    }

    @Test
    public void deleteTest() {
        String id = "";
        val user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("invalid id :" + id));
        userRepository.delete(user);
    }

    @Test
    public void editTest() {
        String id = "";
        val user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("invalid id :" + id));
        user.setPassword("123456");
        userRepository.save(user);
    }

}

