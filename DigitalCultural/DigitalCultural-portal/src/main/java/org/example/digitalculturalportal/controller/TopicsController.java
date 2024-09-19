package org.example.digitalculturalportal.controller;

import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.Topics;
import org.example.digitalculturalportal.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/17 16:01
 **/

@RestController
public class TopicsController {

    @Autowired
    private TopicsService topicsService;

    @GetMapping("/getallTopics")
    public CommonResult getallTopics() {
        List<Topics> list = topicsService.selectAll();
        return CommonResult.success(list);
    }

}
