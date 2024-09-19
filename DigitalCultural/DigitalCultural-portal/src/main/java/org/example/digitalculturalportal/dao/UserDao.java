package org.example.digitalculturalportal.dao;


import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.User;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */

public interface UserDao {
    User findUserByusername(@Param("username") String username);
    List<String> selectPermsByUserId(@Param("userId") Long userId);
    int insertList(@Param("list") List<User> list);
    User findUserByuserId(@Param("userId") Integer userId);
    void updatePassword(@Param("newpassworod") String newpassworod, @Param("userId") Integer userId);

    void updateProfile(@Param("nickName") String nickName, @Param("blog") String blog, @Param("userId") Integer userId);

}
