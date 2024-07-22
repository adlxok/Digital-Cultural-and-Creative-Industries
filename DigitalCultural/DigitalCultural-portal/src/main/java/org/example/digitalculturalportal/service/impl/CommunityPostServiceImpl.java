package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.CommunityPostDao;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class CommunityPostServiceImpl implements CommunityPostService {
    @Autowired
    private CommunityPostDao communityPostDao;
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public int addCommunityPost(CommunityPost communityPost) {
        if (communityPost==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义HTML标记，确保用户输入内容在web页面上为普通文本，不是作为HTML执行，防止脚本攻击
        communityPost.setTitle(HtmlUtils.htmlEscape(communityPost.getTitle()));
        communityPost.setContent(HtmlUtils.htmlEscape(communityPost.getContent()));

        //过滤器
        communityPost.setTitle(sensitiveFilter.filter(communityPost.getTitle()));
        communityPost.setContent(sensitiveFilter.filter(communityPost.getContent()));
        return communityPostDao.addCommunityPost(communityPost);
    }
}
