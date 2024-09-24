package com.example.server_forexam.service;

import com.example.server_forexam.entity.CommunityComment;

import java.util.List;

public interface CommunityCommentService {
    /**
     * 发布评论
     * @param communityComment
     * @return
     */
    int addCommunityComment(CommunityComment communityComment);
    /**
     * 分页查询评论列表
     * @param entityType 0-评论; 1-帖子
     * @param entityId
     * @return
     */
    List<CommunityComment> queryCommentList(Integer entityType, Integer entityId, Integer pageNum, Integer pageSize);

    /**
     * 修改评论状态
     * @param id
     * @return
     */
    int alterCommentStatus(Integer id,Integer status);
    int commentCount(Integer entityType,Integer entityId);

}
