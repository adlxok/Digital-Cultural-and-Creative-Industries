package com.example.server_forexam.entity;

import lombok.Data;

import java.util.Date;


@Data
public class CommunityPost {

    private int id;


    private int userId;


    private String title;


    private String content;


    private int type;


    private int status;


    private Date createTime;


    private int commentCount;


    private double score;

    private String contentHtml;
}
