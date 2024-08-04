package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.Carousel;
import org.example.digitalculturalportal.pojo.NavigationItems;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/30 16:12
 **/
public interface GetResourceService {
    List<NavigationItems> reqGetHeadList();
    List<Carousel> reqGetBannerList();
}