package org.example.digitalculturalportal.utils;

public interface CommunityConstant {
    //实体类型：评论
    int ENTITY_TYPE_COMMENT=0;
    //实体类型：帖子
    int ENTITY_TYPE_POST=1;
    //实体：人
    int ENTITY_TYPE_USER=2;
    //帖子状态：普通
    int POST_STATUS_COMMON=0;
    //帖子状态：精华
    int POST_STATUS_WONDERFUL=1;
    //帖子状态：拉黑
    int POST_STATUS_BLACK=2;
    //帖子类型：普通
    int POST_TYPE_COMMON=0;
    //帖子类型
    int POST_TYPE_TOP=1;
    //默认页码
    String DEFAULT_PAGENUM="1";
    //默认一页数据数量
    String DEFAULT_PAGESIZE="5";
    //Kafka 主题：评论
    String TOPIC_COMMENT="comment";
    //Kafka 主题：点赞
    String TOPIC_LIKE="like";
    //Kafka 主题：关注
    String TOPIC_FOLLOW="follow";
    // Kafka 主题：发帖
    String TOPIC_PUBLISH = "publish";

    // Kafka 主题：删帖
    String TOPIC_DELETE = "delete";
}
