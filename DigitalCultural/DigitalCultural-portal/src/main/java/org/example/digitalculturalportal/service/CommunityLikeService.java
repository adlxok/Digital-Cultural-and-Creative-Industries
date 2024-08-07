package org.example.digitalculturalportal.service;

import java.util.List;
import java.util.Map;

/**
 * 点赞Service
 * @author jisamin
 * @since 2024/7/22
 */
public interface CommunityLikeService {

    /**
     * 点赞
     * @param userId 点赞用户
     * @param entityType
     * @param entityId
     * @param entityUserId 被点赞的用户
     */
    void like(Integer userId,Integer entityType,Integer entityId,Integer entityUserId);

    /**
     * 判断是否点赞
     * @param userId
     * @return
     */
    int isLike(Integer userId,Integer entityType,Integer entityId);


    /**
     * 查询某个实体的点赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    long  queryEntityLikeCount(Integer entityType,Integer entityId);

    /**
     * 查询某个用户获得的点赞数量
     * @param userId
     * @return
     */
    long queryUserLikeCount(Integer userId);

}
