package org.example.digitalculturalportal.service;

import java.util.List;
import java.util.Map;

/**
 * 关注Service
 * @author jisamin
 * @since 2024/7/22
 */
public interface CommunityFollowService {
    /**
     * 关注
     * @param userId
     * @param entityType
     * @param entityId
     */
    void follow(Integer userId,Integer entityType,Integer entityId);

    /**
     * 取消关注
     * @param userId
     * @param entityType
     * @param entityId
     */
    void unfollow(Integer userId,Integer entityType,Integer entityId);

    /**
     * 查询某个用户关注的实体的数量
     * @param userId
     * @param entityType
     * @return
     */
    long queryFolloweeCount(Integer userId,Integer entityType);

    /**
     * 查询某个实体的粉丝数量
     * @param entityId
     * @param entityType
     * @return
     */
    long queryFollowerCount(Integer entityId,Integer entityType);

    /**
     * 判断某个用户是否关注该实体
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    boolean isFollowed(Integer userId,Integer entityId,Integer entityType);

    /**
     * 分页查询某个用户关注的人（可以是其他实体）
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryFolloweeList(Integer userId,Integer offset,Integer limit);

    /**
     * 分页查询某个用户的粉丝（可以是其他实体）
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryFollowerList(Integer userId,Integer offset,Integer limit);
}
