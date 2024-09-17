package org.example.digitalculturalportal.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.dao.UserDao;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.service.UserService;
import org.example.digitalculturalportal.utils.JwtUtil;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache rediscache;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String username, String password) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
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

    @Override
    public void logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        log.info("userid: {}", userid);
        //删除Redis中的值
        boolean flag = rediscache.deleteObject("login:"+userid);
        log.info("rediscache.deleteObject: {}", flag);
    }

    @Override
    public User register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User one = userDao.findUserByusername(username);
        if (one == null) {
            List<User> list = new ArrayList<>();
            user.setPassword(passwordEncoder.encode(password));
            user.setCreateTime(new Date());
            list.add(user);
            userDao.insertList(list);
        } else {
            return null;
        }
        return user;
    }

    @Override
    public User queryUserByIdInCache(Integer userId) {
        String key= RedisKeyUtil.getUserKey(userId);
        User user=rediscache.getCacheObject(key);
        if(user==null){
            user=userDao.findUserByuserId(userId);
            //存缓存
            rediscache.setCacheObject(key,user);
        }
        return user;
    }

    @Override
    public void updatePassword(String newpassword, Integer userId) {
        newpassword = passwordEncoder.encode(newpassword);
        userDao.updatePassword(newpassword, userId);
    }

    @Override
    public void updateProfile(String nickName, String blog, Integer userId) {
        userDao.updateProfile(nickName, blog, userId);
    }

    @Override
    public User findUserByuserId(Integer userId) {
        return userDao.findUserByuserId(userId);
    }


}
