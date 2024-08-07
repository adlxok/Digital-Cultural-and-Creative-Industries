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
    //系统用户
    int SYSTEM_USER_ID=1;

    //事件类型：评论
    String EVENT_TYPE_COMMENT="comment";
    //事件类型：点赞
    String EVENT_TYPE_LIKE="like";
    //事件类型：关注
    String EVENT_TYPE_FOLLOW="follow";
    //事件类型：添加帖子
    String EVENT_TYPE_ADDPOST="addPost";
    //事件类型:删除帖子
    String EVENT_TYPE_DELETEPOST="deletePost";

}
