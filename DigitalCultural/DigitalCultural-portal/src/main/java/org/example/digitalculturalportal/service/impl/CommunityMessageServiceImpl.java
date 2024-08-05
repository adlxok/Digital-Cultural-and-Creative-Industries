package org.example.digitalculturalportal.service.impl;

import org.apache.logging.log4j.message.Message;
import org.example.digitalculturalportal.dao.CommunityMessageDao;
import org.example.digitalculturalportal.pojo.CommunityMessage;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.service.CommunityMessageService;
import org.example.digitalculturalportal.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class CommunityMessageServiceImpl implements CommunityMessageService {
    @Autowired
    private CommunityMessageDao communityMessageDao;
    @Autowired
    private SensitiveFilter  sensitiveFilter;
    @Override
    public int addMessage(CommunityMessage communityMessage) {
        if(communityMessage==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义
        communityMessage.setContent(HtmlUtils.htmlEscape(communityMessage.getContent()));
        //过滤
        communityMessage.setContent(sensitiveFilter.filter(communityMessage.getContent()));

        int index=communityMessageDao.insertMessage(communityMessage);
        return index;
    }

    @Override
    public List<CommunityMessage> queryConversationList(Integer userId) {
        return communityMessageDao.selectConversationList(userId);
    }

    @Override
    public int queryConversationCount(Integer userId) {
        return communityMessageDao.selectConversationCount(userId);
    }

    @Override
    public List<CommunityMessage> queryLetterList(String conversationId) {
        return communityMessageDao.selectLetterList(conversationId);
    }

    @Override
    public int queryLetterCount(String conversationId) {
        return communityMessageDao.selectLetterCount(conversationId);
    }

    @Override
    public int queryUnreadLetterCount(Integer userId,String conversationId) {
        return communityMessageDao.selectUnreadLetterCount(userId,conversationId);
    }

    @Override
    public int alterStatus(List<Integer> indexId, Integer status) {
        return communityMessageDao.updateStatus(indexId,status);
    }

    @Override
    public List<Integer> screenUnreadLetter(List<CommunityMessage> list) {
        List<Integer> screenList=new ArrayList<>();
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        for (CommunityMessage communityMessage : list) {
            if(communityMessage.getFromId()!=Math.toIntExact(userId)&&communityMessage.getStatus()==0){
                screenList.add(communityMessage.getId());
            }
        }
        return screenList;
    }

    @Override
    public CommunityMessage queryLatestNotice(Integer userId, String topic) {
        return communityMessageDao.selectLatestNotice(userId,topic);
    }

    @Override
    public int queryNoticeCount(Integer userId, String topic) {
        return communityMessageDao.selectNoticeCount(userId,topic);
    }

    @Override
    public int queryNoticeUnReadCount(Integer userId, String topic) {
        return communityMessageDao.selectNoticeUnReadCount(userId,topic);
    }

    @Override
    public List<CommunityMessage> queryNoticeList(Integer userId, String topic) {
        return communityMessageDao.selectNoticeList(userId, topic);
    }
}
