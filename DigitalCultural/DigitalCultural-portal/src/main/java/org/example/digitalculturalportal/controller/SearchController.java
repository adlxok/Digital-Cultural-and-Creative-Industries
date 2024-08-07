package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.service.ElasticsearchService;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索Controller【elasticsearch安全功能未启用】
 * @author jisamin
 * @since 2024/8/6
 */
@Controller
@Slf4j
@Api(tags = "SearchController")
@Tag(name="SearchController",description = "搜索引擎")
@RequestMapping("/search")
public class SearchController implements CommunityConstant {
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private CommunityLikeService communityLikeService;
    @Autowired
    private UserService userService;
    @ApiOperation("搜索帖子")
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult searchPost(@RequestParam("keyword") String keyword,
                                   @RequestParam(value = "pageNum", defaultValue = DEFAULT_PAGENUM) Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize){
        //Page索引从0开始
        Page<CommunityPost> list=elasticsearchService.searchCommunityPost(keyword,pageNum-1,pageSize);
        List<Map<String,Object>> searchList=new ArrayList<>();
        for (CommunityPost communityPost : list) {
            Map<String,Object> map=new HashMap<>();
            map.put("communityPost",communityPost);
            long likeCount= communityLikeService.queryEntityLikeCount(ENTITY_TYPE_POST,communityPost.getId());
            map.put("likeCount",likeCount);
            User user=userService.queryUserByIdInCache(communityPost.getUserId());
            map.put("user",user);
            searchList.add(map);
        }
        //分页信息
        Map<String,Object> pageMap=new HashMap<>();
        pageMap.put("TotalElements",list.getTotalElements());
        pageMap.put("TotalPages",list.getTotalPages());
        pageMap.put("Size",list.getSize());
        searchList.add(pageMap);
        return CommonResult.success(searchList);

    }
}
