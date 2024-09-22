package com.example.server_forexam.service;

import com.example.server_forexam.entity.CommunityPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityPostService {
    /**
     * 查询帖子数量
     * @param userId 0->所有用户的帖子 ！0->指定用户的帖子
     * @return
     */
    int queryCommunityPostRows(Integer userId);
    /**
     * 分页查询帖子信息
     * @param userId 0->所有用户 ！0->指定用户
     * @param orderMode 0->最新 1->最热
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<CommunityPost> queryCommunityPosts(Integer userId, Integer orderMode,Integer pageNum,Integer pageSize);

    /**
     * 修改评论数量
     * @param id
     * @param commentCount
     * @return
     */
    int alterCommentCount(Integer id, Integer commentCount);

    /**
     * 修改帖子状态：0-正常;1-精华;2-拉黑;
     * @param id
     * @param status
     * @return
     */
    int alterCommentStatus(Integer id, Integer status);

    /**
     * 修改帖子类型：0-普通;1-置顶;
     * @param id
     * @param type
     * @return
     */
    int alterType(Integer id, Integer type);

    /**
     * 修改帖子分数
     * @param id
     * @param score
     * @return
     */
    int alterScore(Integer id, Double score);



}
