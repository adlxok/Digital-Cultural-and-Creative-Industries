package org.example.digitalculturalportal.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonPage;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区首页
 * @author jisamin
 * @since 2024/7/18
 */
@Controller
@Slf4j
@Api(tags = "CommunityHomeController")
@Tag(name="CommunityHomeController",description = "社区首页")
@RequestMapping("/communityHome")
public class CommunityHomeController implements CommunityConstant{
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityLikeService communityLikeService;
    @Autowired
    private UserService userService;
    @ApiOperation("分页展示帖子列表")
    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult showPostList(@RequestParam(value = "pageNum", defaultValue = DEFAULT_PAGENUM) Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize,
                                     @RequestParam("userId") Integer userId,@RequestParam("orderMode") Integer orderMode){
        //保存当前页码页数作键
        RedisKeyUtil redisKeyUtil=new RedisKeyUtil();
        redisKeyUtil.setHotPosTKey(pageNum.toString(),pageSize.toString());
        List<CommunityPost> postList=communityPostService.queryCommunityPosts(userId,orderMode,pageNum,pageSize);
        List<Map<String,Object>> list=new ArrayList<>();
        for (CommunityPost communityPost : postList) {
            Map<String,Object> map=new HashMap<>();
            map.put("post",communityPost);
            User user=userService.queryUserByIdInCache(communityPost.getUserId());
            map.put("user",user);
            long likeCount=communityLikeService.queryEntityLikeCount(ENTITY_TYPE_POST,communityPost.getId());
            map.put("likeCount",likeCount);
           list.add(map);
        }
        //单独封装页数信息
        PageInfo<CommunityPost> pageInfo = new PageInfo<CommunityPost>(postList);
        Map <String,Object> pageMap=new HashMap<>();
        pageMap.put("PageNum",pageInfo.getPageNum());
        pageMap.put("PageSize",pageInfo.getPageSize());
        pageMap.put("Pages",pageInfo.getPages());
        pageMap.put("Total",pageInfo.getTotal());

        Map<String,Object> map =new HashMap<>();
        map.put("list",list);
        map.put("page",pageMap);
        return CommonResult.success(map);
    }
}
