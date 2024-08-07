package org.example.digitalculturalportal;

import org.example.digitalculturalportal.pojo.CommunityPost;

import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.service.impl.ElasticsearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DigitalCulturalPortalApplication .class})
@SpringBootTest
public class ElasticSearchTests {
@Autowired
private CommunityPostService communityPostService;
@Autowired
private ElasticsearchServiceImpl elasticsearchService;

    /**
     * 测试插入数据
     */
   @Test
    public void testSave() {
    elasticsearchService.saveCommunityPost(communityPostService.queryCommunityPostById(1));
   }
    /**
     * 测试删除数据
     */
    @Test
    public void testDelete(){
        elasticsearchService.deleteCommunityPost(1);
    }
    /**
     * 测试搜索数据
     */
    @Test
    public void testSearch(){
        Page<CommunityPost> list=elasticsearchService.searchCommunityPost("中国",0,10);
        System.out.println(list.toString());
    }
}
