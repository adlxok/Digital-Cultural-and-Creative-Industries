package org.example.digitalculturalportal.dao;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.CommunityComment;

import java.util.List;

/**
 * 社区评论Mapper接口
 * @author jisamin
 * @since 2024/7/19
 */
@Mapper
public interface CommunityCommentDao {
    /**
     * 发布评论
     * @param communityComment
     * @return
     */
    int  insertCommunityComment( CommunityComment communityComment);

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    CommunityComment  selectCommunityComment(Integer id);

    /**
     * 查询评论列表
     * @param entityType
     * @param entityId
     * @return
     */
   List<CommunityComment> communityCommentList(@Param("entityType") Integer entityType,@Param("entityId") Integer entityId);

    /**
     * 修改评论状态
     * @param id
     * @return
     */
   int updateCommunityCommentStatus(@Param("id") Integer id,@Param("status") Integer status);

    /**
     * 查询该贴/评论的评论数量
     * @param entityType 0-评论; 1-帖子
     * @param entityId
     * @return
     */
   int selectCommunityCommentCount(@Param("entityType") Integer entityType,@Param("entityId") Integer entityId);

    /**
     * 查询某个用户的评论列表（我的回复列表）
     * @param userId
     * @return
     */
   List<CommunityComment> selectCommunityListByUserId(Integer userId);

    /**
     * 查询某个用户的回复数量
     * @param userId
     * @return
     */
   int selectCommentCountByUserId(Integer userId);

}
