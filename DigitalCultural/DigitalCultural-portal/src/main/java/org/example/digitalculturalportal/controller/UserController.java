package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.UserLoginParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/11 18:57
 **/
@Controller
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        System.out.println(userLoginParam);
//        System.out.println(CommonResult.success(userLoginParam));
        Map<String, String> tokenMap = new HashMap<>();
        String token = "plokmijn";
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(token);
    }

}
