package org.example.digitalculturalportal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${file.save-path}")
    private String fileSavePath;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        //映射本地文件夹
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("file:"+fileSavePath);
    }

}