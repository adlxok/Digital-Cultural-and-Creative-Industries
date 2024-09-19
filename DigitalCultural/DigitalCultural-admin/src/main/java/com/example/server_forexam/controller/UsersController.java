package com.example.server_forexam.controller;


import com.example.server_forexam.common.CommonResult;
import com.example.server_forexam.entity.Users;
import com.example.server_forexam.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/selectAll")
    public CommonResult selectAll() {
        List<Users> list = usersService.selectAll();
        return CommonResult.success(list);
    }

    @PostMapping("/deleteUsers")
    public CommonResult deleteUsers(@RequestBody Map<String, Object> reqData) {
        int userId = Integer.parseInt(reqData.get("userId").toString());
        usersService.deleteUsers(userId);
        return CommonResult.success();
    }

    @PostMapping("/updateUsers")
    public CommonResult updateUsers(@RequestBody Map<String, Object> reqData) {
        int userId = Integer.parseInt(reqData.get("userId").toString());
        String blog = reqData.get("blog").toString();
        String nickname = reqData.get("nickname").toString();
        usersService.updateUsers(userId, blog, nickname);
        return CommonResult.success();
    }

    @PostMapping("/updateStatus")
    public CommonResult updateStatus(@RequestBody Map<String, Object> reqData) {
        int userId = Integer.parseInt(reqData.get("userId").toString());
        usersService.updateStatus(userId);
        return CommonResult.success();
    }

}

