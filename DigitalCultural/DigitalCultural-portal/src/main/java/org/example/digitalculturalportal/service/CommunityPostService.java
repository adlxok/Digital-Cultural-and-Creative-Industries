package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CommunityPost;

/**
 * 帖子Service
 * @author jisamin
 * @since 2024/7/15
 * */
public interface CommunityPostService {
    /**
     * 添加帖子
     * @param communityPost
     * @return
     */
    int addCommunityPost(CommunityPost communityPost);
}
