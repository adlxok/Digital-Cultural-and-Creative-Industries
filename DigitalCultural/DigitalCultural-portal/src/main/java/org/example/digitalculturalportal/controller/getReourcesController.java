package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.Carousel;
import org.example.digitalculturalportal.pojo.NavigationItems;
import org.example.digitalculturalportal.service.GetResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/30 15:42
 **/
@Controller
@Api(tags = "getReourcesController")
@Tag(name="getReourcesController",description = "前端获取资源")
public class getReourcesController {
    @Autowired
    private GetResourceService getResourceService;

    @ApiOperation("获取导航栏资源")
    @RequestMapping(value = "/reqGetHeadList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult reqGetHeadList() {
        List<NavigationItems> list = getResourceService.reqGetHeadList();
        return CommonResult.success(list);
    }

    @ApiOperation("获取首页轮播图资源")
    @RequestMapping(value = "/reqGetBannerList",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult reqGetBannerList() {
        List<Carousel> list = getResourceService.reqGetBannerList();
        return CommonResult.success(list);
    }
}
