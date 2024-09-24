package com.example.server_forexam.service.impl;

import com.example.server_forexam.dao.CommunityPostMapper;
import com.example.server_forexam.entity.CommunityPost;
import com.example.server_forexam.service.CommunityPostService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityPostServiceImpl implements CommunityPostService {
@Autowired
    private CommunityPostMapper communityPostMapper;

    @Override
    public int queryCommunityPostRows(Integer userId) {
        return communityPostMapper.selectCommunityPostRows(userId);
    }

    @Override
    public List<CommunityPost> queryCommunityPosts(Integer userId, Integer orderMode,Integer pageNum,Integer pageSize) {
        List<CommunityPost> postList=new ArrayList<>();
                PageHelper.startPage(pageNum, pageSize);
                postList = communityPostMapper.selectCommunityPosts(userId, orderMode);
                return postList;
    }

    @Override
    public int alterCommentCount(Integer id, Integer commentCount) {
        return communityPostMapper.updateCommentCount(id, commentCount);
    }

    @Override
    public int alterCommentStatus(Integer id, Integer status) {
        return communityPostMapper.updateCommentStatus(id, status);
    }

    @Override
    public int alterType(Integer id, Integer type) {
        return communityPostMapper.updateType(id, type);
    }

    @Override
    public int alterScore(Integer id, Double score) {
        return communityPostMapper.updateScore(id,score);
    }

}
