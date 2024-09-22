package com.example.server_forexam.entity;

import lombok.Data;


import java.util.Date;

@Data
public class CommunityComment {
    private int id;
    /**
     *发布评论作者
     */
    private int userId;
    /**
     * 评论目标类型（0-评论;1-帖子）
     */
    private int entityType;
    /**
     * 评论目标id
     */
    private int  entityId;
    /**
     * 评论目标作者（向谁评论）
     */
    private int targetId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论状态 0-正常；2-拉黑
     */
    private int status;
    /**
     * 发布时间
     */
    private Date createTime;

}