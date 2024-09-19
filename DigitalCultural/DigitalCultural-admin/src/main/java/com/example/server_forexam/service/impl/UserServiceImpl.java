package com.example.server_forexam.service.impl;


import com.example.server_forexam.entity.Result;
import com.example.server_forexam.entity.User;
import com.example.server_forexam.entity.UserInfo;
import com.example.server_forexam.dao.UserMapper;
import com.example.server_forexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     */
    @Override
    public Result login(User user){
        Result result = new Result("用户名或密码错误",20001);
        try {
            String userName = userMapper.login(user);
            if(userName!=null){
                result.setCode(20000);
                result.setMessage("登录成功");
                Map<String, Object> map = new HashMap<>();
                map.put("token", "admin-token");
                result.setData(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 注册
     */
    @Override
    public Result info(String username) {
        Result result = new Result("取得用户信息",20000);
        try {
            UserInfo userInfo = userMapper.getUserInfo(username);
            System.out.println("username:" + username);
            result.setData(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}


