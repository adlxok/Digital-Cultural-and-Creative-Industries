package com.example.server_forexam.service.impl;


import com.example.server_forexam.dao.UsersMapper;
import com.example.server_forexam.entity.Users;
import com.example.server_forexam.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@Service
public class UsersServiceImpl  implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<Users> selectAll() {
        return usersMapper.selectAll();
    }

    @Override
    public void deleteUsers(int userId) {
        usersMapper.deleteUsers(userId);
    }

    @Override
    public void updateUsers(int userId, String blog, String nickname) {
        usersMapper.updateUsers(userId, blog, nickname);
    }

    @Override
    public void updateStatus(int userId) {
        usersMapper.updateStatus(userId);
    }


}
