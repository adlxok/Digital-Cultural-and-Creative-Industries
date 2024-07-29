package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.pojo.Product;
import org.example.digitalculturalportal.service.MallBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mostimaovo
 * @DateTime: 2024/7/27
 **/
@Controller
@Api(tags = "MallBrowseController")
@Tag(name = "MallBrowseController", description = "用户商城浏览")
@RequestMapping("/brows")
public class MallBrowseController {
    @Autowired
    private MallBrowseService mallBrowseService;
    @ApiOperation("用户查看单个商品")
    @RequestMapping(value = "/seeProduct", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> select(@RequestParam("id") Long id){
        List<Product> list=new ArrayList<>();
        list.add(mallBrowseService.findProudct(id));
        return list;
    }

    @ApiOperation("查看批量商品")
    @RequestMapping(value = "/seeProductList", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> selectList(){
        List<Product> list=mallBrowseService.findProudctList();
        return list;
    }
}
