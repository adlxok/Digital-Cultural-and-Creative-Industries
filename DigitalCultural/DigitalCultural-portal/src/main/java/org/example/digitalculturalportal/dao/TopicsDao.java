package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.pojo.Topics;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/9/17 15:53
 **/
public interface TopicsDao {
    List<Topics> selectAll();
}
