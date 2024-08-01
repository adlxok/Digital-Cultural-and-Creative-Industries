package org.example.digitalculturalportal.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 帖子对应数据库表community_post
 * @author jisamin
 * @since 2024/7/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "communitypost", shards = 6, replicas = 3)
public class CommunityPost {
    @Id
    private int id;

    /**
     * 用户id
     */
    @Field(type = FieldType.Integer)
    private int userId;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 帖子类型 0-普通;1-置顶;
     */
    @Field(type = FieldType.Integer)
    private int type;

    /**
     * 评论状态 0-正常;1-精华;2-拉黑;
     */
    @Field(type = FieldType.Integer)
    private int status;

    /**
     * 发布时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 评论数量
     */
    @Field(type = FieldType.Integer)
    private int commentCount;

    /**
     * 帖子分数
     */
    @Field(type = FieldType.Double)
    private double score;
}
