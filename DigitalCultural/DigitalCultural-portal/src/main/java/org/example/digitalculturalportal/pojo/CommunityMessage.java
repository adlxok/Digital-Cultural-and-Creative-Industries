package org.example.digitalculturalportal.pojo;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMessage {
    private int id;
    /**
     * 发送私信的用户id
     */
    private int fromId;
    /**
     * 接收私信的用户id
     */
    private int toId;
    /**
     *会话id
     */
    private String  conversationId;
    /**
     * 内容
     */
    private String  content;
    /**
     * 状态 0-未读；1-已读；2-拉黑
     */
    private int status;
    /**
     * 发送时间
     */
    private Date createTime;

}
