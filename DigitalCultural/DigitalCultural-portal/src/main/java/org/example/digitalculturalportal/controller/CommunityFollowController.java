package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.rabbitmqevent.CommunityProducer;
import org.example.digitalculturalportal.service.CommunityFollowService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区关注Controller【暂定用户板块->实体类型全为用户】
 * @author jisamin
 * @since 2024/8/3
 */
@Controller
@Slf4j
@Api(tags = "CommunityFollowController")
@Tag(name="CommunityFollowController",description = "社区关注")
@RequestMapping("/follow")
public class CommunityFollowController implements CommunityConstant {
    @Autowired
    private CommunityFollowService communityFollowService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommunityProducer communityProducer;
    @ApiOperation("用户关注")
    @RequestMapping(value = "/followUser", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult followUser(@RequestParam("entityType") Integer entityType, @RequestParam("entityId") Integer entityId){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //关注
        communityFollowService.follow(Math.toIntExact(userId),entityType,entityId);
        //发送消息给消息队列
        CommunityEvent communityEvent=new CommunityEvent();
        communityEvent.setEventType(EVENT_TYPE_FOLLOW);
        communityEvent.setUserId(Math.toIntExact(userId));
        communityEvent.setEntityType(entityType);
        communityEvent.setEntityId(entityId);
       if(entityType==ENTITY_TYPE_USER){
           communityEvent.setEntityUserId(entityId);
       }
        communityProducer.sendEvent(communityEvent);
       return CommonResult.success("关注成功");
    }
    @ApiOperation("关注状态")
    @RequestMapping(value = "/followStatus", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult followStatus(@RequestParam("entityType") Integer entityType, @RequestParam("entityId") Integer entityId){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        boolean isFollow=communityFollowService.isFollowed(Math.toIntExact(userId),entityId,entityType);
        if(isFollow){
            return CommonResult.success("已关注");
        }
        return CommonResult.success("未关注");
    }
    @ApiOperation("取消关注")
    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult unfollow(@RequestParam("entityType") Integer entityType, @RequestParam("entityId") Integer entityId){
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        communityFollowService.unfollow(Math.toIntExact(userId),entityType,entityId);
        return CommonResult.success("取消关注");
    }
    @ApiOperation("用户关注列表")
    @RequestMapping(value = "/followeeList/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult followeeList(@PathVariable("userId") Integer userId ,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        //检验用户
        User user=userService.queryUserByIdInCache(userId);
        if(user==null){
            return CommonResult.fail(ResultCode.USER_ERROR);
        }
        Map<String,Object> map =new HashMap<>();
        //关注列表
        List<Map<String,Object>> list=communityFollowService.queryFolloweeList(userId,offset,limit);
        if(list.isEmpty()){
            return CommonResult.success("该用户还没有关注列表");
        }
        map.put("list",list);
        //关注列表数量
        long followeeCount=communityFollowService.queryFolloweeCount(userId,ENTITY_TYPE_USER);
        map.put("followeeCount",followeeCount);
        return CommonResult.success(map);
    }
    @ApiOperation("用户粉丝列表")
    @RequestMapping(value = "/followerList/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult followerList(@PathVariable ("userId") Integer userId,@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        //检验用户
        User user=userService.queryUserByIdInCache(userId);
        if(user==null){
            return CommonResult.fail(ResultCode.USER_ERROR);
        }
        Map<String,Object> map =new HashMap<>();
        //关注列表
        List<Map<String,Object>> list=communityFollowService.queryFollowerList(userId,offset,limit);
        if(list.isEmpty()){
            return CommonResult.success("该用户还没有粉丝列表");
        }
        map.put("list",list);
        //关注列表数量
        long followerCount=communityFollowService.queryFollowerCount(userId,ENTITY_TYPE_USER);
        map.put("followerCount",followerCount);
        return CommonResult.success(map);
    }
}
