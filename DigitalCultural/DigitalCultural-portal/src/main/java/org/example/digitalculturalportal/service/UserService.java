package org.example.digitalculturalportal.service;


import org.example.digitalculturalportal.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
public interface UserService {
    String login(String username, String password);
    void logout();
    User register(User user);
}
