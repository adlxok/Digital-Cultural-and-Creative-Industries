package org.example.digitalculturalportal.utils;

import org.example.digitalculturalportal.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/23 16:14
 **/

@Component("adlx")
public class SGExpressionRoot {
    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissons = loginUser.getPermissions();
        return permissons.contains(authority);
    }
}
