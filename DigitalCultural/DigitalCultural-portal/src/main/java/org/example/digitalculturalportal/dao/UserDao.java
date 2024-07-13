package org.example.digitalculturalportal.dao;


import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */

public interface UserDao {
    User login(@Param("username") String username, @Param("password") String password);
}
