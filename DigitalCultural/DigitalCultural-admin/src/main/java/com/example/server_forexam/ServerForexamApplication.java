package com.example.server_forexam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.server_forexam.dao") // 指定 Mapper 接口所在的包
public class ServerForexamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerForexamApplication.class, args);
    }

}
