package com.example.server_forexam.service;


import com.example.server_forexam.entity.Result;
import com.example.server_forexam.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public Result login(User user);

    public Result info(String uid);



}
