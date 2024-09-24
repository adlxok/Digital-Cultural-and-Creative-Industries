package com.example.server_forexam.service.impl;

import com.example.server_forexam.dao.TopicsCategoriesMapper;
import com.example.server_forexam.entity.TopicsCategories;

import com.example.server_forexam.service.TopicsCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * <p>
 * 主题表 服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-09-19
 */
@Service
public class TopicsCategoriesServiceImpl implements TopicsCategoriesService {


    @Autowired
    private TopicsCategoriesMapper topicsCategoriesMapper;

    @Override
    public List<TopicsCategories> selectAll() {

        return topicsCategoriesMapper.selectAll();
    }

    @Override
    public void insertone(TopicsCategories topicsCategories) {
        topicsCategories.setCreatedAt(new Date());
        topicsCategories.setUpdatedAt(new Date());
        topicsCategoriesMapper.insertone(topicsCategories);
    }

    @Override
    public void deleteOne(int id) {
        topicsCategoriesMapper.deleteOne(id);
    }

    @Override
    public void updateTopic(int topicId, String name, String description) {
        topicsCategoriesMapper.updateTopic(topicId, name, description);
    }

    @Override
    public void updateStatus(int topicId) {
        topicsCategoriesMapper.updateStatus(topicId);
    }


}
