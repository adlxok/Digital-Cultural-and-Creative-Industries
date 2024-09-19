package com.example.server_forexam.service;


import com.example.server_forexam.entity.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
public interface UsersService  {
    List<Users> selectAll();

    void deleteUsers(int userId);

    void updateUsers(int userId, String blog, String nickname);

    void updateStatus(int userId);
}
