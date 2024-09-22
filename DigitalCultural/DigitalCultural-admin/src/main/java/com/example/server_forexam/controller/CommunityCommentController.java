package com.example.server_forexam.controller;

import com.example.server_forexam.common.CommonResult;
import com.example.server_forexam.entity.CommunityComment;
import com.example.server_forexam.service.CommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/comment")
public class CommunityCommentController {
@Autowired
    private CommunityCommentService communityCommentService;

@PostMapping("/add")
    public CommonResult add(@RequestBody CommunityComment communityComment){
    communityComment.setStatus(0);
    communityComment.setCreateTime(new Date());
    communityCommentService.addCommunityComment(communityComment);
    return CommonResult.success("添加评论成功");
}
@GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @RequestParam("entityType") Integer entityType,@RequestParam("entityId") Integer entityId){
    List<CommunityComment> list=communityCommentService.queryCommentList(entityType,entityId,pageNum,pageSize);
    if(list.isEmpty()){
        return CommonResult.success("评论列表为空");
    }
    int count=communityCommentService.commentCount(entityType,entityId);
    Map<String, Object> map=new HashMap<>();
    map.put("list",list);
    map.put("count",count);
    return CommonResult.success(map);
}
@GetMapping("/delete")
    public CommonResult delete(@RequestParam("id") Integer id){
    communityCommentService.alterCommentStatus(id,2);
    return CommonResult.success("删评成功");
}
}
