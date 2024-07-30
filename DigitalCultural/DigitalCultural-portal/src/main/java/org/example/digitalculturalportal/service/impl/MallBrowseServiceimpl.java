package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.MallBrowseDao;
import org.example.digitalculturalportal.pojo.Product;
import org.example.digitalculturalportal.service.MallBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户商城浏览Service实现类
 * @Author: mostimaovo
 * @DateTime: 2024/7/27
 **/
@Service
public class MallBrowseServiceimpl implements MallBrowseService {
    @Autowired
    private MallBrowseDao mallBrowseDao;
    @Override
    public Product findProudct(Long id) {
        return mallBrowseDao.selectProductById(id);
    }

    @Override
    public List<Product> findProudctList() {
        return mallBrowseDao.selectProductList();
    }
}
