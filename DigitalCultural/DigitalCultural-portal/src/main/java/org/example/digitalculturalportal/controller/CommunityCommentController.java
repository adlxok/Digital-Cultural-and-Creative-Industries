package org.example.digitalculturalportal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonPage;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.*;
import org.example.digitalculturalportal.rabbitmqevent.CommunityProducer;
import org.example.digitalculturalportal.service.CommunityCommentService;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 社区评论/回复Controller
 * @author jisamin
 * @since 2024/7/20
 */
@Controller
@Slf4j
@Api(tags = "CommunityCommentController")
@Tag(name="CommunityCommentController",description = "社区评论/回复")
@RequestMapping("/comment")
public class CommunityCommentController implements CommunityConstant {
    @Autowired
    private CommunityCommentService communityCommentService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityLikeService communityLikeService;
    @Autowired
    private CommunityProducer communityProducer;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    @ApiOperation("用户发布评论")
    @RequestMapping(value = "/addComment/{postId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addComment(@PathVariable("postId") Integer postId, @RequestBody CommunityComment communityComment)  {
        if (communityComment==null){
          return CommonResult.fail(ResultCode.FAIL_TITLE);
        }
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        communityComment.setUserId(Math.toIntExact(userId));
        communityComment.setCreateTime(new Date());
        communityCommentService.addCommunityComment(communityComment);
        //向消息队列发送消息
        CommunityEvent communityEvent=new CommunityEvent();
        communityEvent.setEventType(EVENT_TYPE_COMMENT);
        communityEvent.setEntityId(communityComment.getEntityId());
        communityEvent.setUserId(Math.toIntExact(userId));
        communityEvent.setEntityType(communityComment.getEntityType());
        communityEvent.setData("postId",postId);

        if(communityComment.getEntityType()==ENTITY_TYPE_COMMENT){
            CommunityComment comment=communityCommentService.queryCommentById(communityComment.getEntityId());
            Integer entityUserId=comment.getUserId();
            communityEvent.setEntityUserId(entityUserId);
        }
        else if(communityComment.getEntityType()==ENTITY_TYPE_POST){
            CommunityPost post=communityPostService.queryCommunityPostById(communityComment.getEntityId());
            Integer entityUserId=post.getUserId();
            communityEvent.setEntityUserId(entityUserId);
        }
        communityProducer.sendEvent(communityEvent);
        if(communityComment.getEntityType()==ENTITY_TYPE_POST) {
            //加分
            String key= RedisKeyUtil.getScoreKey();
            redisCache.setCacheObjectInSet(key,postId);
        }
        return CommonResult.success("发布评论成功");
    }
    @ApiOperation("帖子评论列表")
    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult commentList(@RequestParam(value = "pageNum", defaultValue = DEFAULT_PAGENUM) Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize,
                                    @RequestParam("postId") Integer postId){
        //帖子顶级评论列表
      List<CommunityComment> list=communityCommentService.queryCommentList(ENTITY_TYPE_POST,postId,pageNum,pageSize);
      if(list.isEmpty()){
          return CommonResult.success("该帖子还没有评论");
      }
        List<Map<String,Object>> allPostInfo=new ArrayList<>();
        for (CommunityComment communityComment : list) {
            //封装帖子评论相关信息
            Map<String, Object> postComInfo=new HashMap<>();
            //评论
            postComInfo.put("communityComment",communityComment);
            //点赞数量
            long likeCount=communityLikeService.queryEntityLikeCount(ENTITY_TYPE_COMMENT,communityComment.getId());
            postComInfo.put("likeCount",likeCount);
            //获取SecurityContextHolder中的用户id
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long userId = loginUser.getUser().getId();
            //当前用户点赞状态
            int  likeStatus=communityLikeService.isLike(Math.toIntExact(userId),ENTITY_TYPE_COMMENT,communityComment.getId());
            postComInfo.put("likeStatus",likeStatus);


           //该评论的回复列表->不分页
            List<CommunityComment> replyList=communityCommentService.queryCommentList(ENTITY_TYPE_COMMENT,communityComment.getId(),1,Integer.MAX_VALUE);
            List<Map<String,Object>> allReply=new ArrayList<>();
            for (CommunityComment replyComment : replyList) {
                //封装回复评论相关消息
                Map<String, Object> replyComInfo = new HashMap<>();
                //回复
                replyComInfo.put("replyComment", replyComment);
                //该条回复的点赞数量
                likeCount = communityLikeService.queryEntityLikeCount(ENTITY_TYPE_COMMENT, replyComment.getId());
                replyComInfo.put("likeCount", likeCount);
                //该条回复的点赞状态
                likeStatus = communityLikeService.isLike(Math.toIntExact(userId), ENTITY_TYPE_COMMENT, replyComment.getId());
                replyComInfo.put("likeStatus", likeStatus);
                //该条回复的作者
                replyComInfo.put("user", userService.queryUserByIdInCache(replyComment.getUserId()));
                //该条回复的目标
                User target = userService.queryUserByIdInCache(replyComment.getTargetId());
                replyComInfo.put("target", target);
                allReply.add(replyComInfo);
            }
            postComInfo.put("allreply", allReply);
            //该评论的回复总数量
            long replyCount=communityCommentService.queryCommentCount(ENTITY_TYPE_COMMENT,communityComment.getId());
            postComInfo.put("replyCount",replyCount);
            allPostInfo.add(postComInfo);
        }
      return CommonResult.success(CommonPage.restPage(allPostInfo)) ;
    }
//    @ApiOperation("回复评论列表")
//    @RequestMapping(value = "/replyCommentList", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult replyCommentList()

}
