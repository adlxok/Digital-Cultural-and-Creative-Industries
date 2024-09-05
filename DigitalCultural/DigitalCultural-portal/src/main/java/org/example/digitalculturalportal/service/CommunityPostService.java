package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CommunityPost;

import java.util.List;
import java.util.Set;

/**
 * 帖子Service
 * @author jisamin
 * @since 2024/7/15
 * */
public interface CommunityPostService {
    /**
     * 热帖进缓存
     */
     void hostPostInCache();

    /**
     * 分页查询热帖
     * @return
     */
  List<CommunityPost> queryHostPostByCache();

  /**
   * 删除缓存
   */
  void deleteCache();

    /**
     * 添加帖子
     * @param communityPost
     * @return
     */
    int addCommunityPost(CommunityPost communityPost);

    /**
     * 根据id查询帖子
     * @param id
     * @return
     */
    CommunityPost queryCommunityPostById(Integer id);

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

    /**
     * 查询评论数量
     * @param id
     * @return
     */
    int queryCommentCount(Integer id);
    List<CommunityPost> queryHotPost();
}
