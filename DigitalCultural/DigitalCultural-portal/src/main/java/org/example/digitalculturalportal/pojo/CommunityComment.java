package org.example.digitalculturalportal.pojo;

import lombok.*;

import java.util.Date;

/**
 * 评论对应数据库community_comment
 * @author jisamin
 * @since 2024/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
