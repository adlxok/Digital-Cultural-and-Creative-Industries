package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonPage;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
public class CommunityHomeController implements CommunityConstant{
    @Autowired
    private CommunityPostService communityPostService;
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
        return CommonResult.success(CommonPage.restPage(postList));
    }
}
