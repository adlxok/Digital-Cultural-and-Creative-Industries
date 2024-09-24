package com.example.server_forexam.controller;


import com.example.server_forexam.common.CommonResult;

import com.example.server_forexam.entity.TopicsCategories;
import com.example.server_forexam.service.TopicsCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 主题表 前端控制器
 * </p>
 *
 * @author adlx
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/topicscategories")
public class TopicsCategoriesController {

    @Autowired
    private TopicsCategoriesService topicsCategoriesService;

    @GetMapping("/selectAll")
    public CommonResult seleceAll() {
        List<TopicsCategories> list = topicsCategoriesService.selectAll();
        return CommonResult.success(list);
    }

    @PostMapping("/insertone")
    public CommonResult insertone(@RequestBody TopicsCategories topicsCategories) {
        topicsCategoriesService.insertone(topicsCategories);
        return CommonResult.success();
    }

    @PostMapping("/deleteOne")
    public CommonResult deleteOne(@RequestBody Map<String, Object> reqData) {
        int topicId = Integer.parseInt(reqData.get("topid").toString());
        topicsCategoriesService.deleteOne(topicId);
        return CommonResult.success();
    }

    @PostMapping("/updateTopic")
    public CommonResult updateTopic(@RequestBody Map<String, Object> reqData) {
        int topicId = Integer.parseInt(reqData.get("topicId").toString());
        String name = reqData.get("name").toString();
        String description = reqData.get("description").toString();

        topicsCategoriesService.updateTopic(topicId, name, description);
        return CommonResult.success();
    }

    @PostMapping("/updateStatus")
    public CommonResult updateStatus(@RequestBody Map<String, Object> reqData) {
        int topicId = Integer.parseInt(reqData.get("topicId").toString());
        topicsCategoriesService.updateStatus(topicId);
        return CommonResult.success();
    }

}

