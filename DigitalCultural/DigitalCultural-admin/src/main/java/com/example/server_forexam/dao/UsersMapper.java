package com.example.server_forexam.dao;



import com.example.server_forexam.entity.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
public interface UsersMapper {

    List<Users> selectAll();

    void deleteUsers(@Param("userId") Integer userId);

    void updateUsers(@Param("userId") Integer usesrId, @Param("blog") String blog, @Param("nickname") String nickname);

    void updateStatus(@Param("userId") Integer userId);

}
