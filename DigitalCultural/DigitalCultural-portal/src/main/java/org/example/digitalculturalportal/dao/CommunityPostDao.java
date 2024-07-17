package org.example.digitalculturalportal.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.CommunityPost;

import java.util.List;

/**
 * 社区帖子Mapper接口
 * @author jisamin
 * @since 2024/7/15
 */
@Mapper
public interface CommunityPostDao {
    /**
     * 添加帖子
     * @param communityPost
     */
 int addCommunityPost (CommunityPost communityPost);

    /**
     * 根据id查询帖子
     * @param id
     * @return
     */
    CommunityPost selectCommunityPostById(int id);

    /**
     * 查询帖子个数
     * userId=0计算所有用户的帖子总数
     * userId！=0计算该指定用户的帖子总数
     * @param userId
     * @return
     */
    int selectCommunityPostRows(@Param("userId") int userId);

    /**
     * 分页查询帖子信息
     * @param userId  userId=0计算所有用户的帖子总数
     *                userId！=0计算该指定用户的帖子总数
     * @param offset 每页的起始索引
     * @param limit  每页显示多少条帖子
     * @param orderMode 排行模式（0：按时间排序 1：按热度排序）
     * @return
     */
    List<CommunityPost>selectCommunityPosts(int userId,int offset,int limit,int orderMode);

    /**
     * 修改评论数量
     * @param id
     * @param commentCount
     * @return
     */
    int updateCommentCount(int id,int commentCount);

    /**
     * 修改评论状态：0-正常;1-精华;2-拉黑;
     * @param id
     * @param status
     * @return
     */
    int updateCommentStatus(int id,int status);

    /**
     * 修改帖子类型：0-普通;1-置顶;
     * @param id
     * @param type
     * @return
     */
    int updateType(int id,int type);

   /**
    * 修改帖子分数
    * @param id
    * @param score
    * @return
    */
    int updateScore(int id,double score);
}

