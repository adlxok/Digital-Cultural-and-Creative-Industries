package org.example.digitalculturalportal.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.CommunityFollowService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.digitalculturalportal.utils.CommunityConstant.ENTITY_TYPE_USER;

@Service
@Slf4j
public class CommunityFollowServiceImpl implements CommunityFollowService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Override
    public void follow(Integer userId, Integer entityType, Integer entityId) {
        //redis事务管理
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //生成redis的key
                String followeeKey= RedisKeyUtil.getFolloweeKey(userId,entityType);
                String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
                //开启事务管理
                operations.multi();
                //添加数据
                redisTemplate.opsForZSet().add(followeeKey,entityId,System.currentTimeMillis());
                redisTemplate.opsForZSet().add(followerKey,userId,System.currentTimeMillis());
                //提交事务
                return operations.exec();
            }
        });
    }

    @Override
    public void unfollow(Integer userId, Integer entityType, Integer entityId) {
     redisTemplate.execute(new SessionCallback() {
         @Override
         public Object execute(RedisOperations operations) throws DataAccessException {
             //生成redis的key
             String followeeKey= RedisKeyUtil.getFolloweeKey(userId,entityType);
             String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
             //启动管理事务
             operations.multi();

             //删除数据
             redisTemplate.opsForZSet().remove(followeeKey,entityId);
             redisTemplate.opsForZSet().remove(followerKey,userId);
             //提交事务
             return operations.exec();
         }
     });
    }

    @Override
    public long queryFolloweeCount(Integer userId, Integer entityType) {
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }

    @Override
    public long queryFollowerCount(Integer entityId, Integer entityType) {
        String followerKey=RedisKeyUtil.getFollowerKey(entityType,entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }

    @Override
    public boolean isFollowed(Integer userId, Integer entityId, Integer entityType) {
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().score(followeeKey,entityId)!=null;
    }

    /**
     *
     * @param userId
     * @param offset 偏移量
     * @param limit 返回元素限制数量
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFolloweeList(Integer userId,Integer offset,Integer limit) {
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,ENTITY_TYPE_USER);
        Set<Integer> indexIds=redisTemplate.opsForZSet().reverseRange(followeeKey,offset,offset+limit-1);
        if(indexIds==null){
            log.info("该用户的关注列表为空");
            return null;
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for (Integer indexId : indexIds) {
           Map<String,Object> map=new HashMap<>();
           //根据id获取用户信息
//           User user=userService.queryUserByIdInCache(indexId);
//           map.put("user",user);
           //获取排序集合的分数（关注时间）
            Double score=redisTemplate.opsForZSet().score(followeeKey,indexId);
            Date followTime=new Date(score.longValue());
            map.put("followTime",followTime);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryFollowerList(Integer userId,Integer offset,Integer limit) {
        String followerKey=RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER,userId);
        Set<Integer> indexIds=redisTemplate.opsForZSet().reverseRange(followerKey,offset,limit-1);
        if(indexIds==null){
            log.info("该实体(用户)无粉丝");
            return null;
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for (Integer indexId : indexIds) {
            Map<String,Object> map=new HashMap<>();
//            User user=userService.queryUserByIdInCache(indexId);
//            map.put("user",user);
            Double score=redisTemplate.opsForZSet().score(followerKey,indexId);
            Date followTime=new Date(score.longValue());
            map.put("followTime",followTime);
            list.add(map);
        }
        return list;
    }
}
