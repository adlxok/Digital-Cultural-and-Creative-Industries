package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityComment;
import org.example.digitalculturalportal.service.CommunityCommentService;
import org.example.digitalculturalportal.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 社区评论管理Controller
 * @author jisamin
 * @since 2024/7/20
 */
@Controller
@Slf4j
@Api(tags = "CommunityCommentController")
@Tag(name="CommunityCommentController",description = "社区评论管理")
@RequestMapping("/comment")
public class CommunityCommentController {
    @Autowired
    private CommunityCommentService communityCommentService;

    @ApiOperation("用户发布评论")
    @RequestMapping(value = "/addComment/{postId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult addComment(@RequestParam("content") String content, @PathVariable  Integer postId){
        if (content==null){
          return CommonResult.fail(ResultCode.FAIL_CONTENT);
        }
//         int userId=UserHolder.get();//获取当前用户id
        CommunityComment communityComment=new CommunityComment();
        communityComment.setContent(content);
        communityComment.setEntityId(postId);
        communityComment.setUserId(3);//3为测试
        communityCommentService.addCommunityComment(communityComment);
        return CommonResult.success();
    }

}
