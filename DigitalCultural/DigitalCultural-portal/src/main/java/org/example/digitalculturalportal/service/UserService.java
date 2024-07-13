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
    User login(String username, String password);
}
