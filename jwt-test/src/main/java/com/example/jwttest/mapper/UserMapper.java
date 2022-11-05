package com.example.jwttest.mapper;

import com.example.jwttest.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findUser(@Param("id") String id);

    /* 아이디 검증 */
    /* 아이디가 존재 할때 아이디와 일치하는 모든 데이터를 가져온다. */
    User validId(@Param("id") String id);


    /* 회원가입*/
    void register(User user);
}
