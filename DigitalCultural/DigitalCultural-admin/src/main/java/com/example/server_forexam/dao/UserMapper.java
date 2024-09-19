package com.example.server_forexam.dao;

import com.example.server_forexam.entity.User;
import com.example.server_forexam.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper{

    /**
     * 登录
     * @param user
     * @return
     */
    String login(User user);

    /**
     * 用户信息
     * @param username
     * return
     */
    UserInfo getUserInfo(@Param("username") String username);

}


