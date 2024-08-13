package org.example.digitalculturalportal.service.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.dao.GetResourcesDao;
import org.example.digitalculturalportal.pojo.Carousel;
import org.example.digitalculturalportal.pojo.NavigationItems;
import org.example.digitalculturalportal.service.GetResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/30 16:14
 **/
@Service
@Slf4j
public class GetResourceServiceImpl implements GetResourceService {

    @Autowired
    private GetResourcesDao getResourcesDao;

    @Override
    public List<NavigationItems> reqGetHeadList() {
        return getResourcesDao.reqGetHeadList();
    }

    @Override
    public List<Carousel> reqGetBannerList() {
        return getResourcesDao.reqGetBannerList();
    }
}
