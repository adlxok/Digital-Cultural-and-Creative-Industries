package org.example.digitalculturalportal.quartz;

import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityLikeService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.example.digitalculturalportal.utils.RedisCache;
import org.example.digitalculturalportal.utils.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * 更新帖子分数的任务Job
 * @author jisamin
 * @since 2024/7/26
 */
@Repository
@Slf4j
public class postScoreRefreshJob implements Job, CommunityConstant {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityLikeService communityLikeService;
    //社区纪元
    private static final Date epoch;
    static {
        try{
            epoch=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-04-01 00:00:00");
        }catch (ParseException e){
            throw new RuntimeException("初始化纪元失败！"+e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String key= RedisKeyUtil.getScoreKey();
        Set<Integer> set=redisCache.getCacheSet(key);

        if(set.size()==0){
            log.info("任务取消，没有要刷新的帖子");
            return;
        }
        log.info("任务开始：刷新帖子的分数:"+set.size());
            for (Integer i : set) {
                this.refresh(i);
            }
            log.info("任务完成..刷新帖子成功");
        //更新缓存,重新加载数据
        communityPostService.deleteCache();
        communityPostService.hostPostInCache();
    }


    private void refresh(int postId){
        CommunityPost post=communityPostService.queryCommunityPostById(postId);
        if(post==null){
            log.info("该帖子不存在id="+postId);

            return;
        }
        //计算帖子分数
        //是否加精
        boolean isWonderful=post.getStatus()==POST_STATUS_WONDERFUL;
        //评论数量
        int commentCount=post.getCommentCount();
        //点赞数量
        int likeCount= (int) communityLikeService.queryEntityLikeCount(ENTITY_TYPE_POST,postId);
        // 计算权重
        double w = (isWonderful? 75 : 0) + commentCount * 10 + likeCount * 2;
        // 计算分数=log(w) + 距离起始值天数
        // 新增帖子没有权重，默认为1
        // 时间以天为单位
        double score = Math.log10(Math.max(w, 1))
                + (post.getCreateTime().getTime() - epoch.getTime()) / (1000 * 3600 * 24);
        //更新数据库的帖子分数
        communityPostService.alterScore(postId,score);

    }
}
