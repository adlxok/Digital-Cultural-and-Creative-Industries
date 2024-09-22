package com.example.server_forexam.dao;

import com.example.server_forexam.entity.CommunityComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityCommentMapper {
//    发布评论
    int  insertCommunityComment( CommunityComment communityComment);

    /**
     * 查询评论列表
     * @param entityType
     * @param entityId
     * @return
     */
    List<CommunityComment> communityCommentList(@Param("entityType") Integer entityType, @Param("entityId") Integer entityId);

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
}
