package org.example.digitalculturalportal.utils;

import io.swagger.models.auth.In;

public class RedisKeyUtil {
    private static final String SPLIT=":";
    private static final String PREFIX_POST = "post";
    private static final String PREFIX_FOLLOWEE="followee";//关注的对象
    private static final String PREFIX_FOLLOWER="follower";//被关注（粉丝）
    private static final String PREFIX_USER="user";//用户信息
    private static final String PREFIX_USER_LIKE="user:like";//用户获赞
    private static final String PREFIX_ENTITY_LIKE="entity:like";//实体获赞
    private static String hotPostKey;//热帖

    /**
     * 获得热帖key
     * @return
     */
    public String getHotPostKey() {
        return hotPostKey;
    }

    /**
     * 赋值热帖key
     * @param pageNum
     * @param pageSize
     */
    public void setHotPosTKey(String pageNum,String pageSize) {
        this.hotPostKey = pageNum+SPLIT+pageSize;
    }


    /**
     * 用户关注实体key
     * key->zset(entityId,now)
     * @param userId
     * @param entityType
     * @return
     */
    public static String getFolloweeKey(Integer userId, Integer entityType){
        return PREFIX_FOLLOWEE+SPLIT+userId+SPLIT+entityType;
    }

    /**
     * 实体的粉丝key
     * key->zset(userId,now)
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(Integer entityType,Integer entityId){
        return PREFIX_FOLLOWER+SPLIT+entityType+SPLIT+entityId;
    }

    /**
     * 用户key
     * key->user
     * @param userId
     * @return
     */
    public static String getUserKey(Integer userId){
        return PREFIX_USER+SPLIT+userId;
    }

    /**
     * 用户获得点赞数量key
     * key->int
     * @param userId
     * @return
     */
    public static String getUserLikeKey(Integer userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }

    /**
     * 实体获得点赞key
     * key->set(userId)
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityLikeKey(Integer entityType,Integer entityId){
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    /**
     * 分数key
     * @return
     */
    public static String getScoreKey(){
        return PREFIX_POST+SPLIT+"score";
    }

}
