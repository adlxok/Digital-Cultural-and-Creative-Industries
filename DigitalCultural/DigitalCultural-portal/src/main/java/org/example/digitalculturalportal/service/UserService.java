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

    /**
     *从缓存获取用户，未命中查数据库
     * @param userId
     * @return
     */
    User queryUserByIdInCache(Integer userId);

    void updatePassword(String newpassword, Integer userId);

    void updateProfile(String nickName, String blog, Integer userId);

    User findUserByuserId(Integer userId);
}
