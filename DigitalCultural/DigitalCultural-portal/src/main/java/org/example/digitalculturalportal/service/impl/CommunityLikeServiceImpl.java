package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class CommunityLikeServiceImpl implements CommunityLikeService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void like(Integer userId, Integer entityType, Integer entityId,Integer entityUserId) {
        //redis事务管理
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey= RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey=RedisKeyUtil.getUserLikeKey(entityUserId);
                boolean isMember=isLike(userId,entityType,entityId);
                operations.multi();
                if(isMember){
                    //用户已点过赞->取消点赞
                    redisTemplate.opsForSet().remove(entityLikeKey);
                    redisTemplate.opsForValue().decrement(userLikeKey);//若key存在-1
                }else{
                    redisTemplate.opsForSet().add(entityLikeKey,userId);
                    redisTemplate.opsForValue().increment(userLikeKey);//若key不存在+1
                }
                return operations.exec();
            }
        });
    }


    @Override
    public boolean isLike(Integer userId, Integer entityType, Integer entityId) {
        String entityKey=RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().isMember(entityKey,userId);
    }

    @Override
    public long queryEntityLikeCount(Integer entityType, Integer entityId) {
        String entityLikeKey=RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    @Override
    public long queryUserLikeCount(Integer userId) {
        String userLikeKey=RedisKeyUtil.getUserLikeKey(userId);
        return redisTemplate.opsForValue().size(userLikeKey);
    }
}
