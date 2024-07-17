package org.example.digitalculturalportal.utils;

import com.auth0.jwt.interfaces.JWTPartsParser;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器认证
 * @author jisamin
 * @since 2024/7/17
 */
@Component
@Slf4j//可以直接使用log对象的方法记录日志
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
//        String token = request.getHeader("Authorization");
          String token= "dfkfd";
        try {
            log.info("令牌验证：" + token);
//            token = token.replace("_", "+").replace("-", "+");
            if(JWTUtil.verify(token)) {
                String username = JWTUtil.getUsername(token);
                UserHolder.set(username);
                log.info("用户名已存入副本:" + username);
                //放行
                return true;
            }

        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            log.error("token验证失败");
            return false;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        //清空ThreadLocal中的数据
        UserHolder.remove();
    }

}




