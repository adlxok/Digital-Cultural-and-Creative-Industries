package org.example.digitalculturalportal.service;


import org.example.digitalculturalportal.pojo.CommunityPost;
import org.springframework.data.domain.Page;

/** 搜索Service
 * @author jisamin
 * @since 2024/8/5
 */
public interface ElasticsearchService {
    /**
     * 将数据插入elasticsearch服务器
     * @param communityPost
     */
    void saveCommunityPost(CommunityPost communityPost);

    /**
     * 将数据从elasticsearch服务器中删除
     * @param id
     */
    void deleteCommunityPost(Integer id);

    /**
     * 分页搜索
     * @param keyword 搜索的关键词
     * @param current 当前页码
     * @param limit 每页显示数据的数量
     * @return
     */
    Page<CommunityPost> searchCommunityPost(String keyword,int current,int limit);

}
