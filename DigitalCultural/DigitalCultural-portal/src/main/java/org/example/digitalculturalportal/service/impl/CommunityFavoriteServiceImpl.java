package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.CommunityFavoriteDao;
import org.example.digitalculturalportal.pojo.CommunityFavorite;
import org.example.digitalculturalportal.pojo.LoginUser;
import org.example.digitalculturalportal.service.CommunityFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommunityFavoriteServiceImpl implements CommunityFavoriteService {
    @Autowired
    private CommunityFavoriteDao communityFavoriteDao;
    @Override
    public int favorite(CommunityFavorite communityFavorite) {
      return communityFavoriteDao.addFavorite(communityFavorite);
    }

    @Override
    public int isFavorite(Integer userId,Integer status,Integer entityId) {
        return communityFavoriteDao.updateStatus(userId,status,entityId);
    }

    @Override
    public List<CommunityFavorite> favoriteListByUserId(Integer userId) {
        return communityFavoriteDao.selectFavoriteListByUserId(userId);
    }

    @Override
    public Integer queryFavoriteStatus(Integer id,Integer entityId) {
        return communityFavoriteDao.selectFavoriteStatus(id,entityId);
    }

    @Override
    public int queryFavoriteCount(Integer entityId) {
        return communityFavoriteDao.selectFavoriteCount(entityId);
    }

    @Override
    public CommunityFavorite queryFavorite(Integer userId, Integer entityId) {
        return communityFavoriteDao.selectFavorite(userId,entityId);
    }
}
