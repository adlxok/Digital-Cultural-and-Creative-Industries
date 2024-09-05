package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.CommunityImageDao;
import org.example.digitalculturalportal.pojo.CommunityImage;
import org.example.digitalculturalportal.service.CommunityImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityImageServiceImpl implements CommunityImageService {
    @Autowired
    private CommunityImageDao communityImageDao;
    @Override
    public int addImageUrl(CommunityImage communityImage) {
        return communityImageDao.addImageUrl(communityImage);
    }

    @Override
    public List<String> queryImageList(Integer postId) {
        return communityImageDao.selectImageList(postId);
    }
}
