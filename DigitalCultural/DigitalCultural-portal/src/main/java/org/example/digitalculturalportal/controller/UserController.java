package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.pojo.UserLoginParam;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录,返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();
        User user = userService.login(username,password);
        if (user == null) {
            return CommonResult.error(ResultCode.INPUT_ERROR);
        }
        System.out.println(userLoginParam);
        Map<String, String> tokenMap = new HashMap<>();
//        User user = new User();
//        user.setUsername("gfgfg");user.setPassword("fddf");
//        String token = JWTUtil.createToken(user);
        String token = "dfkfd";
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getInfo(@RequestParam("token") String token) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "adlx");
        map.put("avatar", "https://example.com/avatar.jpg");
        return CommonResult.success(map);
    }

    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {

        return CommonResult.success();
    }

}
