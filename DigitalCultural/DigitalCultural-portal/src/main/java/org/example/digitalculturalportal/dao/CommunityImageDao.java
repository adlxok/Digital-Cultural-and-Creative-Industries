package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.pojo.CommunityImage;

import java.util.List;

/**
 * 社区图片Mapper接口
 * @author jisamin
 * @since 2024/8/26
 */
public interface CommunityImageDao {
    /**
     * 记录图片url
     * @param communityImage
     * @return
     */
    int addImageUrl(CommunityImage communityImage);

    /**
     * 获取图片url列表
     * @param postId
     * @return
     */
    List<String> selectImageList(Integer postId);
}
