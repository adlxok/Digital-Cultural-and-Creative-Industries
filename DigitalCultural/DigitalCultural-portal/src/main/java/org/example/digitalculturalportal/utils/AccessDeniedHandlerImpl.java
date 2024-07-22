package org.example.digitalculturalportal.utils;

import com.alibaba.fastjson.JSON;
import org.example.digitalculturalportal.common.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: adlx
 * @Description: 授权失败
 * @DateTime: 2024/7/18 21:50
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException){
        String json = JSON.toJSONString(CommonResult.error(HttpStatus.FORBIDDEN.value(),"您的权限不足"));
        //处理异常
        WebUtils.renderString(response,json);
    }
}
