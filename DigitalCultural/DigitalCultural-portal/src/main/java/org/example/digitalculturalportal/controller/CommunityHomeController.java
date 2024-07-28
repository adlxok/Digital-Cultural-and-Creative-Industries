package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonPage;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
public class CommunityHomeController {
    @Autowired
    private CommunityPostService communityPostService;
    @ApiOperation("分页展示帖子列表")
    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult showPostList(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     Integer userId,Integer orderMode){
        List<CommunityPost> postList=communityPostService.queryCommunityPosts(userId,orderMode,pageSize,pageNum);
        log.info("查询帖子成功");
        return CommonResult.success(CommonPage.restPage(postList));
    }

//    @ApiOperation("我的主页详情")
//    @RequestMapping(value = "/myHomepage", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult myDetails(){
//
//    }
}
