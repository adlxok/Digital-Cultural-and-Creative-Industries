package org.example.digitalculturalportal.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.pojo.UserLoginParam;
import org.example.digitalculturalportal.service.UserService;

import org.example.digitalculturalportal.utils.JwtUtil;
import org.example.digitalculturalportal.utils.RedisCache;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;


/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/11 18:57
 **/
@Controller
@Slf4j
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache rediscache;


    @ApiOperation("用户登录,返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();
        String token = userService.login(username,password);
        if (token == null) {
            return CommonResult.error(ResultCode.INPUT_ERROR);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestBody User user) {
        if (userService.register(user) == null) {
            return CommonResult.error(ResultCode.USER_EXITS, user);
        }
        return CommonResult.success(user);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getInfo(@RequestParam("token") String token) throws Exception {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "adlx");
//        map.put("avatar", "https://example.com/avatar.jpg");
        Claims claims = JwtUtil.parseJWT(token);
        String userid = claims.getSubject();
        LoginUser loginUser = (LoginUser) rediscache.getCacheObject("login:"+userid);
        User user = loginUser.getUser();
        return CommonResult.success(user);
    }
    @ApiOperation("获取用户信根据id")
    @RequestMapping(value = "/getUser/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUser(@PathVariable Integer userId){
        User user=userService.queryUserByIdInCache(userId);
        return CommonResult.success(user);
    }


    @ApiOperation("退出登录")
//    @PreAuthorize("@adlx.hasAuthority('dcc:use')")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        userService.logout();
        return CommonResult.success();
    }



}
