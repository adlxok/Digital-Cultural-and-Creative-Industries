package org.example.digitalculturalportal.dao;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.logging.log4j.message.Message;
import org.example.digitalculturalportal.pojo.CommunityMessage;

import java.util.List;

/**
 * 私信/系统通知Mapper接口
 * @author jisamin
 * @since 2024/7/20
 */
@Mapper
public interface CommunityMessageDao {
    /**
     * 添加私信
     * @param message
     * @return
     */
    int insertMessage(CommunityMessage message);

    /**
     * 查询当前用户的会话列表 每个会话只返回最新的一条私信
     * @param userId
     * @return
     */
    List<CommunityMessage> selectConversationList(Integer userId);

    /**
     * 查询当前用户会话数量
     * @param userId
     * @return
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话的私信列表
     * @param conversationId
     * @return
     */
    List<CommunityMessage> selectLetterList(String conversationId);

    /**
     * 查询某个会话私信的数量
     * @param conversationId
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询会话的未读私信数量
     * @param conversationId null-全部会话; !null-指定会话
     * @return
     */
    int selectUnreadLetterCount(String conversationId);

    /**
     * 修改消息状态
     * @param indexId
     * @param status
     * @return
     */
    int updateStatus(List<Integer> indexId, Integer status);

    /**
     * 查询某个主题下的最新通知
     * @param userId
     * @param topic
     * @return
     */
    int selectLatestNotice(Integer userId,String topic);

    /**
     * 查询某个主题下包含的系统通知数量
     * @param userId
     * @param topic
     * @return
     */
    int selectNoticeCount(Integer userId,String topic);

    /**
     * 查询未读的系统通知数量
     * @param userId
     * @param topic
     * @return
     */
    int selectNoticeUnReadCount(int userId, String topic);

    /**
     * 查询某个主题所包含的通知列表
     * @param userId
     * @param topic
     * @return
     */
    List<Message> selectNoticeList(int userId, String topic);


}
