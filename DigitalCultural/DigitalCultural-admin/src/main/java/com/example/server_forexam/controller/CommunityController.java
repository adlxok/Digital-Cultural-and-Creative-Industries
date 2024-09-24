package com.example.server_forexam.controller;

import com.example.server_forexam.common.CommonResult;
import com.example.server_forexam.entity.CommunityPost;
import com.example.server_forexam.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class CommunityController {
    @Autowired
    private CommunityPostService communityPostService;

    @GetMapping("/postList")
    public CommonResult getPostList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam("userId") Integer userId, @RequestParam("orderMode") Integer orderMode) {
        List<CommunityPost> list = communityPostService.queryCommunityPosts(userId, orderMode,pageNum, pageSize);
        int postCount=communityPostService.queryCommunityPostRows(userId);
        Map<String,Object> map=new HashMap<>();
        map.put("postCount",postCount);
        map.put("list",list);
        return CommonResult.success(map);

    }

    @GetMapping("/deletePost")
    public CommonResult deletePost(@RequestParam("id") Integer id) {
        communityPostService.alterCommentStatus(id, 2);
        return CommonResult.success("删贴成功");
    }

    @GetMapping("/topPost")
    public CommonResult topPost(@RequestParam("id") Integer id) {
        communityPostService.alterType(id, 1);
        return CommonResult.success("置顶成功");

    }

    @GetMapping("/updateScore")
    public CommonResult updateScore(@RequestParam("id") Integer id, @RequestParam("score") double score) {
        communityPostService.alterScore(id, score);
        return CommonResult.success("修改分数成功");
    }

    @GetMapping( "/essentialPost")
    public CommonResult essentialPost(@RequestParam("id") Integer id){
        communityPostService.alterCommentStatus(id,1);
        return CommonResult.success("加精成功");
    }
}
