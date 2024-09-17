package org.example.digitalculturalportal.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.dao.UserDao;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;


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
    public CommonResult getInfo() throws Exception {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        User user = loginUser.getUser();
        User userreal = userDao.findUserByuserId(Math.toIntExact(user.getId()));
        return CommonResult.success(userreal);
    }

    @ApiOperation("退出登录")
//    @PreAuthorize("@adlx.hasAuthority('dcc:use')")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        userService.logout();
        return CommonResult.success();
    }


    @ApiOperation("修改密码")
//    @PreAuthorize("@adlx.hasAuthority('dcc:use')")
    @RequestMapping(value = "/updatepassword",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatepassword(@RequestBody Map<String, Object> requestData) {
        int userId = Integer.parseInt(requestData.get("userId").toString());
        String oldpassword = requestData.get("oldpassword").toString();
        String newpassword = requestData.get("newpassword").toString();
        String key = "login:" + userId;
        LoginUser loginUser = rediscache.getCacheObject(key);
        User user= loginUser.getUser();
        if (!passwordEncoder.matches(oldpassword, user.getPassword())) {
            return CommonResult.error(ResultCode.INPUT_ERROR);
        }
        userService.updatePassword(newpassword, userId);
        return CommonResult.success();
    }

    @ApiOperation("保存更改")
//    @PreAuthorize("@adlx.hasAuthority('dcc:use')")
    @RequestMapping(value = "/updateProfile",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateProfile(@RequestBody Map<String, Object> requestData) {
        int userId = Integer.parseInt(requestData.get("userId").toString());
        String blog = requestData.get("blog").toString();
        String nickName = requestData.get("nickName").toString();
        userService.updateProfile(nickName,blog,userId);
        return CommonResult.success();
    }


}
