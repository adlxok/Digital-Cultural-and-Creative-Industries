package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.pojo.Carousel;
import org.example.digitalculturalportal.pojo.NavigationItems;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/30 15:46
 **/
public interface GetResourcesDao {
    List<NavigationItems> reqGetHeadList();

    List<Carousel> reqGetBannerList();
}