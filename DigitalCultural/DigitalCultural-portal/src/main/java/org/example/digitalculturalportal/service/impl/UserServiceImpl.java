package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.dao.UserDao;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.JwtUtil;
import org.example.digitalculturalportal.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache rediscache;

    @Override
    public String login(String username, String password) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        LOGGER.info("{}", authenticate);
        LOGGER.info("{}", Objects.isNull(authenticate));
        //如果认证没通过，给出提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果通过了，使用userid生成JWT，JWT存入ResponseResult进行返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);

        rediscache.setCacheObject("login:"+userid,loginUser);

        return jwt;
    }
}
