package com.example.server_forexam.service.impl;

import com.example.server_forexam.dao.CommunityCommentMapper;
import com.example.server_forexam.entity.CommunityComment;
import com.example.server_forexam.service.CommunityCommentService;
import com.example.server_forexam.service.CommunityPostService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CommunityCommentServiceImpl implements CommunityCommentService {
    @Autowired
    private CommunityCommentMapper communityCommentMapper;
    @Autowired
    private CommunityPostService communityPostService;
    //事务管理
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED )
    @Override
    public int addCommunityComment(CommunityComment communityComment) {
        if(communityComment==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        int index=communityCommentMapper.insertCommunityComment(communityComment);
        //若实体是帖子则更新帖子的评论数量
        if(communityComment.getEntityType()==1){
            int sum=communityCommentMapper.selectCommunityCommentCount(communityComment.getEntityType(),communityComment.getEntityId());
            communityPostService.alterCommentCount(communityComment.getEntityId(),sum);
        }
        return index;
    }


    @Override
    public List<CommunityComment> queryCommentList(Integer entityType, Integer entityId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return communityCommentMapper.communityCommentList(entityType,entityId);
    }

    @Override
    public int alterCommentStatus(Integer id,Integer status) {
        return communityCommentMapper.updateCommunityCommentStatus(id,status);
    }

    @Override
    public int commentCount(Integer entityType, Integer entityId) {
        return communityCommentMapper.selectCommunityCommentCount(entityType,entityId);
    }

}
