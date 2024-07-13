package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.UserDao;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        return userDao.login(username,password);
    }
}
