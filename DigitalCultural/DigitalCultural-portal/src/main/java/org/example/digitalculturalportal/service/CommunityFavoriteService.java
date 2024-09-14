package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CommunityFavorite;

import java.util.List;

public interface CommunityFavoriteService {
    /**
     * 收藏
     */
    int favorite(CommunityFavorite communityFavorite);

    /**
     * 取消收藏
     * @return
     */
    int isFavorite(Integer userId,Integer status,Integer entityId);

    /**
     * 查询某人的收藏列表
     * @param userId
     * @return
     */
    List<CommunityFavorite> favoriteListByUserId(Integer userId);
    Integer queryFavoriteStatus(Integer id,Integer entityId);
    int queryFavoriteCount(Integer entityId);
    CommunityFavorite queryFavorite(Integer userId,Integer entityId);
}
