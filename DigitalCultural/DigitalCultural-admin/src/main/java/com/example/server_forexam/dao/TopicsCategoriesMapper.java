package com.example.server_forexam.dao;


import com.example.server_forexam.entity.TopicsCategories;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * 主题表 Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-09-19
 */
public interface TopicsCategoriesMapper {

    List<TopicsCategories> selectAll();

    void insertone(TopicsCategories topicsCategories);

    void deleteOne(@Param("topicId") Integer topicId);

    void updateTopic(@Param("topicId") Integer topicId, @Param("name") String name, @Param("description") String description);

    void updateStatus(@Param("topicId") Integer topicId);

}
