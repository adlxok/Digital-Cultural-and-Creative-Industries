package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.CommunityImage;
import org.example.digitalculturalportal.pojo.CommunityMessage;

import java.util.List;

/**
 * 社区图片Service
 * @author jisamin
 * @since 2024/8/26
 */
public interface CommunityImageService {
    int addImageUrl(CommunityImage communityImage);
    List<String> queryImageList(Integer postId);
}
