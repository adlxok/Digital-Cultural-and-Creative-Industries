package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.UserDao;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/18 19:34
 **/

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByusername(username);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }
        //查询对应的权限信息
        List<String> list = userDao.selectPermsByUserId(user.getId());


        //把数据封装成UserDetails返回,LoginUser实现UserDetails
        return new LoginUser(user,list);
    }
}
