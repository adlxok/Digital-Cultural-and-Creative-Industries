package org.example.digitalculturalportal.utils;

import org.example.digitalculturalportal.controller.CommunityPostController;
import org.example.digitalculturalportal.pojo.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * 定义变量：使用ThreadLocal实现当前登录信息获取(注：不要滥用此代码！）
 * @author jisamin
 * @since 2024/7/16
 */
@Component
public class UserHolder {

    //提供ThreadLocal对象
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    //存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    //清除ThreadLocal 防止内存泄露
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
