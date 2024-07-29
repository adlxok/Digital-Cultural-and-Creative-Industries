package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CommunityComment;
import org.example.digitalculturalportal.pojo.CommunityPost;

import java.util.List;

/**
 * 评论Service
 * @author jisamin
 * @since 2024/7/19
 */
public interface CommunityCommentService {
    /**
     * 发布评论
     * @param communityComment
     * @return
     */
    int addCommunityComment(CommunityComment communityComment);

    /**
     * 根据id查询帖子
     * @param id
     * @return
     */
    CommunityComment queryCommentById(Integer id);

    /**
     * 分页查询评论列表
     * @param entityType 0-评论; 1-帖子
     * @param entityId
     * @return
     */
    List<CommunityComment> queryCommentList(Integer entityType,Integer entityId,Integer pageNum,Integer pageSize);

    /**
     * 修改评论状态
     * @param id
     * @return
     */
    int alterCommentStatus(Integer id,Integer status);

    /**
     * 查询当前评论数量
     * @param entityType 0-评论；1-帖子
     * @param entityId
     * @return
     */
    int queryCommentCount(Integer entityType,Integer entityId);

    /**
     * 分页查询查询某个用户的评论列表
     * @param userId
     * @return
     */
    List<CommunityComment> queryCommentListByUserId(Integer userId,Integer pageNum,Integer pageSize);

    /**
     * 查询某个用户的回复数量
     * @param userId
     * @return
     */
    int queryCommentCountByUserId(Integer userId);

}
