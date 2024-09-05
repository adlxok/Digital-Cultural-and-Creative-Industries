package org.example.digitalculturalportal.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class WebConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(31457280); // 设置最大上传文件大小为30MB
        multipartResolver.setMaxInMemorySize(10240); // 设置文件在内存中存储的最大大小，超过这个大小将写入磁盘
        return multipartResolver;
    }

}