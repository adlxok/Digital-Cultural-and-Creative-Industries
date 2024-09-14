package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.recycler.Recycler;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.dao.CommunityFavoriteDao;
import org.example.digitalculturalportal.pojo.*;
import org.example.digitalculturalportal.rabbitmqevent.CommunityProducer;
import org.example.digitalculturalportal.service.CommunityFavoriteService;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private CommunityFavoriteService communityFavoriteService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityProducer communityProducer;
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
            communityProducer.sendEvent(communityEvent);
        }
        if (entityType == ENTITY_TYPE_POST) {
            //加分
            String key = RedisKeyUtil.getScoreKey();
            redisCache.setCacheObjectInSet(key, postId);
        }

     return CommonResult.success(map);
    }
    @ApiOperation("实体获赞数量")
    @RequestMapping(value = "/entLikeCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult entLikeCount(@RequestParam("entityType") Integer entityType,@RequestParam("entityId") Integer entityId){
       long count=communityLikeService.queryEntityLikeCount(entityType, entityId);
       return CommonResult.success(count);
    }
    @ApiOperation("用户获赞数量")
    @RequestMapping(value = "/useLikeCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult useLikeCount(@RequestParam("userId") Integer userId){
        long count=communityLikeService.queryUserLikeCount(userId);
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
    @ApiOperation("添加收藏")
    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addFavorite(@RequestBody FavoriteParam favoriteParam){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        Integer status=communityFavoriteService.queryFavoriteStatus(Math.toIntExact(userId),favoriteParam.getEntityId());
        if(status==null){
            CommunityFavorite communityFavorite = new CommunityFavorite();
            communityFavorite.setEntityType(favoriteParam.getEntityType());
            communityFavorite.setEntityId(favoriteParam.getEntityId());
            communityFavorite.setUserId(Math.toIntExact(userId));
            communityFavorite.setStatus(1);//默认1收藏
            communityFavorite.setCreateTime(new Date());
            int id = communityFavoriteService.favorite(communityFavorite);
            return CommonResult.success(id);
        }
        else if(status==1){
            CommunityFavorite communityFavorite1=communityFavoriteService.queryFavorite(Math.toIntExact(userId),favoriteParam.getEntityId());
            if(communityFavorite1==null){
                return CommonResult.fail(ResultCode.OBJECT_ERROR);
            }
            communityFavoriteService.isFavorite(communityFavorite1.getUserId(),0,communityFavorite1.getEntityId());
            return CommonResult.success("取消收藏");
        } else  {
            CommunityFavorite communityFavorite1=communityFavoriteService.queryFavorite(Math.toIntExact(userId),favoriteParam.getEntityId());
            if(communityFavorite1==null){
                return CommonResult.fail(ResultCode.OBJECT_ERROR);
            }
            communityFavoriteService.isFavorite(communityFavorite1.getUserId(),1,communityFavorite1.getEntityId());
            return CommonResult.success("收藏");
        }

    }
    @ApiOperation("改变收藏状态")
    @RequestMapping(value = "/isFavorite", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult isFavorite(@RequestParam("userId") Integer userId,@RequestParam("status") Integer status,@RequestParam("entityId") Integer entityId){
        communityFavoriteService.isFavorite(userId,status,entityId);
        return CommonResult.success(status);
    }
    @ApiOperation("收藏列表")
    @RequestMapping(value = "/favoriteList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult favoriteList(@RequestParam("userId") Integer userId){
        List<CommunityFavorite> list=communityFavoriteService.favoriteListByUserId(userId);
        if(list.isEmpty()){
            return CommonResult.success("还没有收藏列表");
        }
        List<Map<String,Object>> favoriteList=new ArrayList<>();
        for (CommunityFavorite communityFavorite : list) {
            Map<String,Object> map=new HashMap<>();
            CommunityPost post=communityPostService.queryCommunityPostById(communityFavorite.getEntityId());
            map.put("post",post);
            User entityUser=userService.queryUserByIdInCache(post.getUserId());
            map.put("entityUser",entityUser);
            favoriteList.add(map);
        }
        return CommonResult.success(favoriteList);
    }

    @ApiOperation("收藏状态")
    @RequestMapping(value = "/favoriteStatus/{entityId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult favoriteStatus(@PathVariable Integer entityId) {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        Integer status = communityFavoriteService.queryFavoriteStatus(Math.toIntExact(userId),entityId);
        if(status==null){
            status=0;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("status ",status );
        int count= communityFavoriteService.queryFavoriteCount(entityId);
        map.put("count",count);
        return CommonResult.success(map);
    }

}
