package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 社区帖子管理Controller
 * @author  jisamin
 * @since  2024/7/15
 */
@Controller
@Api(tags = "CommunityPostController")
@Tag(name="CommunityPostController",description = "社区帖子管理")
@RequestMapping("/post")
public class CommunityPostController {
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private UserHolder userHolder;

    @ApiOperation("用户发布帖子")
    @RequestMapping(value = "/addPost", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult addCommunityPost(@RequestParam("title") String title,@RequestParam("content") String content) {
        String username=userHolder.get();
        if(username==null){
            return CommonResult.fail(ResultCode.FAIL,"您还未登录");
        }
        if (title.isEmpty()) {
            return CommonResult.fail(ResultCode.FAIL,"标题不能为空");
        }
        CommunityPost communityPost=new CommunityPost();
        communityPost.setTitle(title);
        communityPost.setContent(content);
        communityPost.setUserId(2);
        communityPost.setCreateTime(new Date());
        communityPostService.addCommunityPost(communityPost);
        return CommonResult.success();
    }
}
