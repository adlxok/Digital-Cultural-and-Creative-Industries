package com.example.server_forexam.service;


import com.example.server_forexam.entity.TopicsCategories;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主题表 服务类
 * </p>
 *
 * @author adlx
 * @since 2024-09-19
 */
public interface TopicsCategoriesService {

    List<TopicsCategories> selectAll();

    void insertone(TopicsCategories topicsCategories);

    void deleteOne(int id);

    void updateTopic( int topicId,  String name,  String description);

    void updateStatus(int topicId);

}
