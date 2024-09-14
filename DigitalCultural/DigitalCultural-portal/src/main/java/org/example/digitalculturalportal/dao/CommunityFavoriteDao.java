package org.example.digitalculturalportal.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.CommunityFavorite;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 社区收藏Mapper
 */
@Mapper
public interface CommunityFavoriteDao {

    /**
     * 收藏
     */
    int  addFavorite(CommunityFavorite communityFavorite);

    /**
     * 改变状态
     * @return
     */
    int updateStatus(@Param("userId") Integer userId,@Param("status") Integer status,@Param("entityId") Integer entityId);
    /**
     * 查询收藏列表
     * @param userId
     * @return
     */
    List<CommunityFavorite> selectFavoriteListByUserId(Integer userId);

    /**
     * 查看收藏状态
     * @param id
     * @return
     */
    Integer selectFavoriteStatus(@Param("id") Integer id,@Param("entityId") Integer entityId);

    /**
     * 某个帖子收藏数量
     * @param entityId
     * @return
     */
    int selectFavoriteCount(@Param("entityId") Integer entityId);
    CommunityFavorite selectFavorite(@Param("userId") Integer userId,@Param("entityId") Integer entityId);
}
