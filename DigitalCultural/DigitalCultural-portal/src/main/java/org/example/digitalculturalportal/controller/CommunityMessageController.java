package org.example.digitalculturalportal.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityMessage;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.CommunityMessageService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


import java.util.*;

/**
 * 社区私信Controller
 * @author jisamin
 * @since 2024/8/3
 */

@Controller
@Slf4j
@Api(tags = "CommunityMessageController")
@Tag(name="CommunityMessageController",description = "社区私信")
@RequestMapping("/letter")
public class CommunityMessageController implements CommunityConstant {
    @Autowired
    private CommunityMessageService communityMessageService;
    @Autowired
    private UserService userService;
    @ApiOperation("新增私信")
    @RequestMapping(value = "/addLetter", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult addMessage(@RequestParam ("toId")Integer toId, @RequestParam("content") String content){
        User user=userService.queryUserByIdInCache(toId);
        if(user==null){
            return CommonResult.fail(ResultCode.USER_ERROR);
        }
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        CommunityMessage communityMessage=new CommunityMessage();
        communityMessage.setContent(content);
        communityMessage.setFromId(Math.toIntExact(userId));
        communityMessage.setToId(toId);
        communityMessage.setCreateTime(new Date());
        //规定统一会话id
        String conversation;
        if(toId>userId){
            conversation=toId+"_"+userId;
        }else{
            conversation=userId+"_"+toId;
        }
        communityMessage.setConversationId(conversation);
        communityMessageService.addMessage(communityMessage);
        return CommonResult.success("新增私信成功");
    }
    @ApiOperation("未读消息数量")
    @RequestMapping(value = "/unreadCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult unreadCount(){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        Map<String,Object> map =new HashMap<>();
        //私信
        int unreadLetterCount=communityMessageService.queryUnreadLetterCount(Math.toIntExact(userId),null);
        map.put("unreadLetterCount",unreadLetterCount);
        //通知
        int unreadNoticeCount=communityMessageService.queryNoticeUnReadCount(Math.toIntExact(userId),null);
        map.put("unreadNoticeCount",unreadNoticeCount);
        return CommonResult.success(map);
    }
    @ApiOperation("私信列表")
    @RequestMapping(value = "/letterList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult allConversation(){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //当前用户的全部会话列表->每个私信会话只返回最新的一条私信
        List<CommunityMessage> list=communityMessageService.queryConversationList(Math.toIntExact(userId));
        List<Map<String,Object>> letterList=new ArrayList<>();
        for (CommunityMessage communityMessage : list) {
            Map <String,Object> map=new HashMap<>();
            //私信
            map.put("letter",communityMessage);
            //私信数量
            int letterCount=communityMessageService.queryLetterCount(communityMessage.getConversationId());
            map.put("letterCount",letterCount);
            //未读私信数量
            int unReadLetterCount=communityMessageService.queryUnreadLetterCount(Math.toIntExact(userId),communityMessage.getConversationId());
            map.put("unReadLetterCount",unReadLetterCount);
            //私信对方
            Integer target=userId==communityMessage.getFromId()? communityMessage.getToId():communityMessage.getFromId();
            map.put("target",target);
            letterList.add(map);
        }
        return CommonResult.success(letterList);

    }
    @ApiOperation("私信详情")
    @RequestMapping(value = "/letterDetail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult letterDetail(@RequestParam("conversationId") String conversationId){
        //私信列表
        List<CommunityMessage> list=communityMessageService.queryLetterList(conversationId);
        List<Map<String,Object>> letterDetailList=new ArrayList<>();
        for (CommunityMessage communityMessage : list) {
            Map<String,Object> map =new HashMap<>();
            map.put("letter",communityMessage);
            map.put("fromUser",userService.queryUserByIdInCache(communityMessage.getFromId()));
            map.put("targetUser",userService.queryUserByIdInCache(communityMessage.getToId()));
            letterDetailList.add(map);
        }
      //更改未读私信状态
        List<Integer> screenList=communityMessageService.screenUnreadLetter(list);
        if(!screenList.isEmpty()) {
            communityMessageService.alterStatus(screenList, 1);
        }
        return CommonResult.success(letterDetailList);
    }
    @ApiOperation("系统通知列表")
    @RequestMapping(value = "/noticeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult noticeList(){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //只返回最新的一条通知
        Map<String,Object> noticeMap=new HashMap<>();

        //点赞板块
        CommunityMessage communityMessage=communityMessageService.queryLatestNotice(Math.toIntExact(userId),EVENT_TYPE_LIKE);
            Map<String, Object> likeMap = new HashMap<>();
            if(communityMessage!=null) {
                //通知
                likeMap.put("notice", communityMessage);
                //通知数量
                int noticeCount = communityMessageService.queryNoticeCount(Math.toIntExact(userId), EVENT_TYPE_LIKE);
                likeMap.put("noticeCount", noticeCount);
                //未读通知数量
                int unReadNoticeCount = communityMessageService.queryNoticeUnReadCount(Math.toIntExact(userId), EVENT_TYPE_LIKE);
                likeMap.put("unReadNoticeCount", unReadNoticeCount);
                //转义
                String content = HtmlUtils.htmlUnescape(communityMessage.getContent());
                //转化为键值对
                Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);
                likeMap.put("user", userService.queryUserByIdInCache((Integer) data.get("userId")));
                likeMap.put("entityType", data.get("entityType"));
                likeMap.put("entityId", data.get("entityId"));
                likeMap.put("postId", data.get("postId"));

            }   noticeMap.put("likeMap",likeMap);
        //评论板块
         CommunityMessage communityMessage1=communityMessageService.queryLatestNotice(Math.toIntExact(userId),EVENT_TYPE_COMMENT);
         Map<String,Object> commentMap=new HashMap<>();
         if(communityMessage1!=null){
             //通知
             commentMap.put("notice",communityMessage1);
             //通知数量
             int noticeCount=communityMessageService.queryNoticeCount(Math.toIntExact(userId),EVENT_TYPE_COMMENT);
             commentMap.put("noticeCount",noticeCount);
             //未读通知数量
             int unReadNoticeCount=communityMessageService.queryNoticeUnReadCount(Math.toIntExact(userId),EVENT_TYPE_COMMENT);
             commentMap.put("unReadNoticeCount",unReadNoticeCount);
             //转义
             String content=HtmlUtils.htmlUnescape(communityMessage1.getContent());
             //转化为键值对
             Map<String,Object> data=JSONObject.parseObject(content, HashMap.class);
             commentMap.put("user", userService.queryUserByIdInCache((Integer) data.get("userId")));
             commentMap.put("entityType", data.get("entityType"));
             commentMap.put("entityId", data.get("entityId"));
             commentMap.put("postId", data.get("postId"));
         }noticeMap.put("commentMap", commentMap);
        //关注板块
        CommunityMessage communityMessage2=communityMessageService.queryLatestNotice(Math.toIntExact(userId),EVENT_TYPE_FOLLOW);
        Map<String,Object> followMap=new HashMap<>();
        if(communityMessage2!=null){
            //通知
            followMap.put("notice",communityMessage2);
            //通知数量
            int noticeCount=communityMessageService.queryNoticeCount(Math.toIntExact(userId),EVENT_TYPE_FOLLOW);
            followMap.put("noticeCount",noticeCount);
            //未读通知数量
            int unreadNoticeCount=communityMessageService.queryNoticeUnReadCount(Math.toIntExact(userId),EVENT_TYPE_FOLLOW);
            followMap.put("unreadNoticeCount",unreadNoticeCount);
            //转义
            String content=HtmlUtils.htmlUnescape(communityMessage2.getContent());
            //转化为键值对
            Map<String,Object> data=JSONObject.parseObject(content, HashMap.class);
            followMap.put("user", userService.queryUserByIdInCache((Integer) data.get("userId")));
            followMap.put("entityType", data.get("entityType"));
            followMap.put("entityId", data.get("entityId"));
            followMap.put("postId", data.get("postId"));

        }noticeMap.put("followMap",followMap);

        return CommonResult.success(noticeMap);

    }

    @ApiOperation("通知详情")
    @RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult noticeDetail(@RequestParam("conversationId") String conversationId){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //某个主题的通知列表
        List<CommunityMessage> list=communityMessageService.queryNoticeList(Math.toIntExact(userId),conversationId);
        List<Map<String,Object>> noticeDetailList=new ArrayList<>();
        for (CommunityMessage communityMessage : list) {
            Map<String,Object> map=new HashMap<>();
            map.put("notice",communityMessage);
            //转义
            String content=HtmlUtils.htmlUnescape(communityMessage.getContent());
            //转化为键值对
            Map<String,Object> data=JSONObject.parseObject(content, HashMap.class);
            map.put("user", userService.queryUserByIdInCache((Integer) data.get("userId")));
            map.put("entityType", data.get("entityType"));
            map.put("entityId", data.get("entityId"));
            map.put("postId", data.get("postId"));
            // 发送系统通知的作者
            map.put("fromUser", userService.queryUserByIdInCache(communityMessage.getFromId()));
            noticeDetailList.add(map);
        }
        //更改通知状态
        List<Integer> screenList=communityMessageService.screenUnreadLetter(list);
        if (!screenList.isEmpty()) {
            communityMessageService.alterStatus(screenList, 1);
        }
        return CommonResult.success(noticeDetailList);
    }
}
