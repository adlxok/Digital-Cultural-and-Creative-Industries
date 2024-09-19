package com.example.server_forexam.controller;

import com.example.server_forexam.entity.Result;
import com.example.server_forexam.entity.User;
import com.example.server_forexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/info")
    public Result login(@RequestParam String username){
        return userService.info(username);
    }

}


