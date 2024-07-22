package org.example.digitalculturalportal.utils;

import com.alibaba.fastjson.JSON;
import org.example.digitalculturalportal.common.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: adlx
 * @Description: 认证失败
 * @DateTime: 2024/7/18 21:49
 **/

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        String json = JSON.toJSONString(CommonResult.error(HttpStatus.UNAUTHORIZED.value(),"用户认证失败请重新登录"));
        //处理异常
        WebUtils.renderString(response,json);
    }
}
