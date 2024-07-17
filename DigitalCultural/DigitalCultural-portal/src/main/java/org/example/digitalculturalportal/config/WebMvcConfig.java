package org.example.digitalculturalportal.config;

import org.example.digitalculturalportal.utils.JwtTokenUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author jisamin
 * @since 2024/7/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //登录不拦截
        registry.addInterceptor(jwtTokenUserInterceptor).excludePathPatterns("/user/login");
    }

}
