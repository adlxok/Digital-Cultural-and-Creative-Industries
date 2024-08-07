package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.rabbitmqevent.CommunityProducer;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 社区帖子Controller
 * @author  jisamin
 * @since  2024/7/15
 */
@Controller
@Slf4j
@Api(tags = "CommunityPostController")
@Tag(name="CommunityPostController",description = "社区帖子")
@RequestMapping("/post")
public class CommunityPostController implements CommunityConstant {
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityProducer communityProducer;
    @Autowired
    private RedisCache redisCache;


    @ApiOperation("用户发布帖子")
    @RequestMapping(value = "/addPost", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult addCommunityPost(@RequestParam("title") String title,@RequestParam("content") String content) {
        if (title.isEmpty()) {
            return CommonResult.fail(ResultCode.FAIL_TITLE);
        }
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        CommunityPost communityPost=new CommunityPost();
        communityPost.setTitle(title);
        communityPost.setContent(content);
        communityPost.setUserId(Math.toIntExact(userId));
        communityPost.setCreateTime(new Date());
        int post=communityPostService.addCommunityPost(communityPost);

        //帖子存进redis定时计算更新分数
        if(communityPost.getId()==0) {
            log.error("获取id失败：加分失败");
        }else {
            String key=RedisKeyUtil.getScoreKey();
            redisCache.setCacheObjectInSet(key, communityPost.getId());
        }
        //向消息队列发送消息
        Map<String,Object> map=new HashMap<>();
        CommunityEvent communityEvent=new CommunityEvent();
        communityEvent.setEventType(EVENT_TYPE_ADDPOST);
        communityEvent.setEntityId(communityPost.getId());
        communityEvent.setEntityType(ENTITY_TYPE_POST);
        communityEvent.setUserId(communityPost.getUserId());
        communityEvent.setEntityUserId(communityPost.getUserId() );
        map.put("postId",communityPost.getId());
        communityEvent.setData(map);
        communityProducer.sendAddPostEvent(communityEvent);
        return CommonResult.success("发帖成功");
    }
    @ApiOperation("置顶帖子")
    @RequestMapping(value = "/topPost", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult topPost(@RequestParam("id") Integer id){
        communityPostService.alterType(id,POST_TYPE_TOP);
        return CommonResult.success("置顶成功");
    }
    @ApiOperation("加精帖子")
    @RequestMapping(value = "/essentialPost", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult essentialPost(@RequestParam("id") Integer id){
        communityPostService.alterCommentStatus(id,POST_STATUS_WONDERFUL);
        //加分
        String key= RedisKeyUtil.getScoreKey();
        redisCache.setCacheObjectInSet(key,id);
        return CommonResult.success("加精成功");
    }
    @ApiOperation("删除帖子")
    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deletePost(@RequestParam("id") Integer id){
        CommunityPost communityPost=communityPostService.queryCommunityPostById(id);
        communityPostService.alterCommentStatus(id,POST_STATUS_BLACK);
        //更新缓存，重新加载数据
        communityPostService.deleteCache();
        communityPostService.hostPostInCache();

        //向消息队列发送消息
        Map<String,Object> map=new HashMap<>();
        CommunityEvent communityEvent=new CommunityEvent();
        communityEvent.setEventType(EVENT_TYPE_ADDPOST);
        communityEvent.setEntityId(communityPost.getId());
        communityEvent.setEntityType(ENTITY_TYPE_POST);
        communityEvent.setUserId(communityPost.getUserId());
        map.put("postId",communityPost.getId());
        communityEvent.setData(map);
        communityProducer.sendDeletePostEvent(communityEvent);
        return CommonResult.success("删帖成功");
    }

}
