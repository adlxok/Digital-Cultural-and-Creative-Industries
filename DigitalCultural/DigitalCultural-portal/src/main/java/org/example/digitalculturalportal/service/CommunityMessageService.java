package org.example.digitalculturalportal.service;

import org.apache.logging.log4j.message.Message;
import org.example.digitalculturalportal.pojo.CommunityMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 消息Service
 * @author jisamin
 * @since 2024/7/20
 */
public interface CommunityMessageService {
    /**
     * 发送一条私信
     * @param communityMessage
     * @return
     */
    int addMessage(CommunityMessage communityMessage);

    /**
     * 查询当前用户的会话列表，每个会话返回最新的一条私信
     * @param userId
     * @return
     */
    List<CommunityMessage> queryConversationList(Integer userId);

    /**
     *查询当前用户会话数量
     * @param userId
     * @return
     */
    int queryConversationCount(int userId);
    /**
     * 查询某个会话的私信列表
     * @param conversationId
     * @return
     */
    List<CommunityMessage> queryLetterList(String conversationId);
    /**
     * 查询某个会话私信的数量
     * @param conversationId
     * @return
     */
    int queryLetterCount(String conversationId);
    /**
     * 查询会话的未读私信数量
     * @param conversationId null-全部会话; !null-指定会话
     * @return
     */
    int queryUnreadLetterCount(String conversationId);
    /**
     * 读取私信（未读->已读）
     * @param indexId
     * @param status
     * @return
     */
    int alterStatus(List<Integer> indexId, Integer status);
    /**
     * 查询某个主题下的最新通知
     * @param userId
     * @param topic
     * @return
     */
    int queryLatestNotice(Integer userId,String topic);
    /**
     * 查询某个主题下包含的系统通知数量
     * @param userId
     * @param topic
     * @return
     */
    int queryNoticeCount(Integer userId,String topic);
    /**
     * 查询未读的系统通知数量
     * @param userId
     * @param topic
     * @return
     */
    int queryNoticeUnReadCount(int userId, String topic);
    /**
     * 查询某个主题所包含的通知列表
     * @param userId
     * @param topic
     * @return
     */
    List<Message> queryNoticeList(int userId, String topic);


}
