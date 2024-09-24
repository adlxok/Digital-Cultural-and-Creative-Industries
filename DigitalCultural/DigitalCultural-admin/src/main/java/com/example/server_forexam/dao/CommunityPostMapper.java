package com.example.server_forexam.dao;

import com.example.server_forexam.entity.CommunityPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityPostMapper {
    /**
     * 查询帖子个数
     * userId=0计算所有用户的帖子总数
     * userId！=0计算该指定用户的帖子总数
     * @param userId
     * @return
     */
    int selectCommunityPostRows(@Param("userId") int userId);
    List<CommunityPost> selectCommunityPosts(@Param("userId") int userId, @Param("orderMode") int orderMode);

    /**
     * 修改评论数量
     * @param id
     * @param commentCount
     * @return
     */
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    /**
     * 修改评论状态：0-正常;1-精华;2-拉黑;
     * @param id
     * @param status
     * @return
     */
    int updateCommentStatus(@Param("id") int id,@Param("status") int status);

    /**
     * 修改帖子类型：0-普通;1-置顶;
     * @param id
     * @param type
     * @return
     */
    int updateType(@Param("id") int id,@Param("type") int type);

    /**
     * 修改帖子分数
     * @param id
     * @param score
     * @return
     */
    int updateScore(@Param("id") int id,@Param("score") double score);

}
