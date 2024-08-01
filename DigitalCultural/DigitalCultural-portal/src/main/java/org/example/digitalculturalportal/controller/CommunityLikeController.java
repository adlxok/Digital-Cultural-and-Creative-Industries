package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 社区点赞Controller
 */
@Controller
@Slf4j
@Api(tags = "CommunityLikeController")
@Tag(name="CommunityLikeController",description = "社区点赞")
@RequestMapping("/like")
public class CommunityLikeController implements CommunityConstant {
    @Autowired
    private CommunityLikeService communityLikeService;
    @Autowired
    private RedisCache redisCache;

    @ApiOperation("用户点赞")
    @RequestMapping(value = "/addLike", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult addLike(@RequestParam("postId") Integer postId, @RequestParam("entityType") Integer entityType,
                                @RequestParam("entityId") Integer entityId, @RequestParam("entityUserId") Integer entityUserId) {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //点赞
        communityLikeService.like(Math.toIntExact(userId), entityType, entityId, entityUserId);
        //点赞数量
        long likeCount = communityLikeService.queryEntityLikeCount(entityType, entityId);
        //点赞状态
        int likeStatus = communityLikeService.isLike(Math.toIntExact(userId), entityType, entityId);
        Map<String, Integer> map = new HashMap<>();
        map.put("likeStatus", likeStatus);
        map.put("likeCount", (int) likeCount);
        //发送消息给消息队列，取消点赞不通知
        if (likeStatus == 1) {
            CommunityEvent communityEvent = new CommunityEvent();
            communityEvent.setEventType(EVENT_TYPE_LIKE);
            communityEvent.setUserId(Math.toIntExact(userId));
            communityEvent.setEntityType(entityType);
            communityEvent.setEntityId(entityId);
            communityEvent.setEntityUserId(entityUserId);
            communityEvent.setData("postId", postId);
        }
        if (entityType == ENTITY_TYPE_POST) {
            //加分
            String key = RedisKeyUtil.getScoreKey();
            redisCache.setCacheObjectInSet(key, postId);
        }

     return CommonResult.success(map);
    }
    @ApiOperation("点赞数量")
    @RequestMapping(value = "/likeCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult likeCount(@RequestParam("entityId") Integer entityId,@RequestParam("entityType") Integer entityType){
       long count=communityLikeService.queryEntityLikeCount(entityId, entityType);
       return CommonResult.success(count);
    }

    @ApiOperation("点赞状态")
    @RequestMapping(value = "/likeStatus", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult likeStatus(@RequestParam("entityId") Integer entityId,@RequestParam("entityType") Integer entityType){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
       int status= communityLikeService.isLike(Math.toIntExact(userId),entityType,entityId);
       return CommonResult.success(status);
    }

}
