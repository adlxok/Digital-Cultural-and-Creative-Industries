package org.example.digitalculturalportal.service.impl;

import com.github.pagehelper.PageHelper;
import org.example.digitalculturalportal.dao.CommunityCommentDao;
import org.example.digitalculturalportal.dao.CommunityPostDao;
import org.example.digitalculturalportal.pojo.CommunityComment;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityCommentService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

import static org.example.digitalculturalportal.utils.CommunityConstant.ENTITY_TYPE_COMMENT;
import static org.example.digitalculturalportal.utils.CommunityConstant.ENTITY_TYPE_POST;

@Service
public class CommunityCommentServiceImpl implements CommunityCommentService {
    @Autowired
    private CommunityCommentDao communityCommentDao;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private CommunityPostService communityPostService;

    //事务管理
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED )
    @Override
    public int addCommunityComment(CommunityComment communityComment) {
        if(communityComment==null){
         throw new IllegalArgumentException("参数不能为空");
        }
        //转义
        communityComment.setContent(HtmlUtils.htmlEscape(communityComment.getContent()));
        //过滤
        communityComment.setContent(sensitiveFilter.filter(communityComment.getContent()));
        int index=communityCommentDao.insertCommunityComment(communityComment);
        //若实体是帖子则更新帖子的评论数量
        if(communityComment.getEntityType()==ENTITY_TYPE_POST){
            int sum=communityCommentDao.selectCommunityCommentCount(communityComment.getEntityType(),communityComment.getEntityId());
            communityPostService.alterCommentCount(communityComment.getEntityId(),sum);
        }
        return index;
    }

    @Override
    public CommunityComment queryCommentById(Integer id) {
        return communityCommentDao.selectCommunityComment(id);
    }
    @Override
    public List<CommunityComment> queryCommentList(Integer entityType, Integer entityId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return communityCommentDao.communityCommentList(entityType,entityId);
    }

    @Override
    public int alterCommentStatus(Integer id,Integer status) {
        return communityCommentDao.updateCommunityCommentStatus(id,status);
    }

    @Override
    public int queryCommentCount(Integer entityType, Integer entityId) {
        return communityCommentDao.selectCommunityCommentCount(entityType,entityId);
    }

    @Override
    public List<CommunityComment> queryCommentListByUserId(Integer userId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return communityCommentDao.selectCommunityListByUserId(userId);
    }

    @Override
    public int queryCommentCountByUserId(Integer userId) {
        return communityCommentDao.selectCommentCountByUserId(userId);
    }
}
