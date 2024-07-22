package org.example.digitalculturalportal.dao;


import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.User;

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
}
