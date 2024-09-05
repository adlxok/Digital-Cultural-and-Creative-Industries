package org.example.digitalculturalportal.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.dao.CommunityPostDao;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.example.digitalculturalportal.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;


import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CommunityPostServiceImpl implements CommunityPostService, CommunityConstant {
    @Autowired
    private CommunityPostDao communityPostDao;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void hostPostInCache() {
        RedisKeyUtil redisKeyUtil=new RedisKeyUtil();
        String key=redisKeyUtil.getHotPostKey();
        if(key==null){
            log.error("key is 0");
            redisKeyUtil.setHotPosTKey(DEFAULT_PAGENUM,DEFAULT_PAGESIZE);
            key=redisKeyUtil.getHotPostKey();
        }
        String[] param=key.split(":");
        int pageNum=Integer.valueOf(param[0]);
        int pageSize=Integer.valueOf(param[1]);
        PageHelper.startPage(pageNum,pageSize);
        List<CommunityPost> postList=communityPostDao.selectCommunityPosts(0,1);
        if(postList.isEmpty()){
            return;
        }
        redisCache.setCacheList(key,postList);
    }

    @Override
    public List<CommunityPost> queryHostPostByCache() {
        RedisKeyUtil redisKeyUtil=new RedisKeyUtil();
        String key=redisKeyUtil.getHotPostKey();
        List<CommunityPost> postList=redisCache.getCacheList(key);
        return postList;
    }

    @Override
    public void deleteCache() {
        RedisKeyUtil redisKeyUtil=new RedisKeyUtil();
        String key=redisKeyUtil.getHotPostKey();
        if(key==null){
            redisKeyUtil.setHotPosTKey(DEFAULT_PAGENUM,DEFAULT_PAGESIZE);
            key=redisKeyUtil.getHotPostKey();
        }
        redisCache.deleteObject(key);
    }


    @Override
    public int addCommunityPost(CommunityPost communityPost) {
        if (communityPost == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义HTML标记，确保用户输入内容在web页面上为普通文本，不是作为HTML执行，防止脚本攻击[使用该方法会使数据库不能正常存储编辑器的HTML]
//        communityPost.setTitle(HtmlUtils.htmlEscape(communityPost.getTitle()));
//        communityPost.setContent(HtmlUtils.htmlEscape(communityPost.getContent()));

        //过滤器
        communityPost.setTitle(sensitiveFilter.filter(communityPost.getTitle()));
        communityPost.setContent(sensitiveFilter.filter(communityPost.getContent()));
        return communityPostDao.addCommunityPost(communityPost);
    }

    @Override
    public CommunityPost queryCommunityPostById(Integer id) {
        return communityPostDao.selectCommunityPostById(id);
    }

    @Override
    public int queryCommunityPostRows(Integer userId) {
        return communityPostDao.selectCommunityPostRows(userId);
    }

    @Override
    public List<CommunityPost> queryCommunityPosts(Integer userId, Integer orderMode,Integer pageNum,Integer pageSize) {
        List<CommunityPost> postList=new ArrayList<>();
        if(userId==0&&orderMode==1) {
            //从缓存获取
            postList = queryHostPostByCache();
            if (postList.size() == 0) {
                //缓存未命中从数据库获取
                PageHelper.startPage(pageNum, pageSize);
                postList = communityPostDao.selectCommunityPosts(userId, orderMode);
                log.info("从数据库");
                //存进缓存
                hostPostInCache();
            }else log.info("从缓存获取");
        }
         else{
            //最新/某个用户贴从数据库获取
            PageHelper.startPage(pageNum,pageSize);
            postList=communityPostDao.selectCommunityPosts(userId,orderMode);
        }
        return postList;
    }

    @Override
    public int alterCommentCount(Integer id, Integer commentCount) {
        return communityPostDao.updateCommentCount(id, commentCount);
    }

    @Override
    public int alterCommentStatus(Integer id, Integer status) {
        return communityPostDao.updateCommentStatus(id, status);
    }

    @Override
    public int alterType(Integer id, Integer type) {
        return communityPostDao.updateType(id, type);
    }

    @Override
    public int alterScore(Integer id, Double score) {
        return communityPostDao.updateScore(id,score);
    }

    @Override
    public int queryCommentCount(Integer id) {
        return communityPostDao.selectCommentCount(id);
    }

    @Override
    public List<CommunityPost> queryHotPost() {
        return communityPostDao.selectHotPost();
    }
}
