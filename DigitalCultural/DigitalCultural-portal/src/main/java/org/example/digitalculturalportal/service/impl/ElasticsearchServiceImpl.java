package org.example.digitalculturalportal.service.impl;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.digitalculturalportal.dao.ElasticRepository;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    @Autowired
    private ElasticRepository elasticRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void saveCommunityPost(CommunityPost communityPost) {
        elasticRepository.save(communityPost);
    }

    @Override
    public void deleteCommunityPost(Integer id) {

        elasticRepository.deleteById(id);
    }

    @Override
    public Page<CommunityPost> searchCommunityPost(String keyword, int current, int limit) {
        List<CommunityPost> postList = new ArrayList<>();

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content")) //设置字段查询（在这两个字段中查询关键词）
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))   //排序
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit)) //分页
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")  //设置高光区域
                ).build();
        //查询，使用SearchHits存储搜索结果
        SearchHits<CommunityPost> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, CommunityPost.class);
        long rows = searchHits.getTotalHits();
        //处理查询到的数据
        if (searchHits.getTotalHits() != 0) {
            for (SearchHit<CommunityPost> searchHit : searchHits) {
                CommunityPost communityPost = new CommunityPost();

                int id = searchHit.getContent().getId();
                communityPost.setId(id);

                int userId = searchHit.getContent().getUserId();
                communityPost.setUserId(userId);

                int status = searchHit.getContent().getStatus();
                communityPost.setStatus(status);

                int type = searchHit.getContent().getType();
                communityPost.setType(type);

                double score = searchHit.getContent().getScore();
                communityPost.setScore(score);

                Date createTime = searchHit.getContent().getCreateTime();
                communityPost.setCreateTime(createTime);

                int commentCount = searchHit.getContent().getCommentCount();
                communityPost.setCommentCount(commentCount);

                String title = searchHit.getContent().getTitle();
                communityPost.setTitle(title);

                String content = searchHit.getContent().getContent();
                communityPost.setContent(content);

                //获得刚构建的高亮区域，填到帖子的的内容和标题上
                List<String> contentFiled = searchHit.getHighlightFields().get("content");
                if (contentFiled!=null) {
                    communityPost.setContent(contentFiled.get(0));  //一个标题就显示一个高亮即可
                }
                    List<String> titleFiled = searchHit.getHighlightFields().get("title");
                    if (titleFiled!=null) {
                        communityPost.setTitle(titleFiled.get(0));
                    }

                postList.add(communityPost);
            }

        }
        return new PageImpl<>(postList, PageRequest.of(current, limit), rows);
    }
}
