package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.TopicsDao;
import org.example.digitalculturalportal.pojo.Topics;
import org.example.digitalculturalportal.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/17 16:00
 **/
@Service
public class TopicsServiceImpl implements TopicsService {

    @Autowired
    private TopicsDao topicsDao;

    @Override
    public List<Topics> selectAll() {
        return topicsDao.selectAll();
    }
}
