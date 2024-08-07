package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.pojo.CommunityPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<CommunityPost,Integer> {
}
