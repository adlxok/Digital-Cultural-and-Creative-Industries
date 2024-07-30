package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.ProductManagementDao;
import org.example.digitalculturalportal.dao.UserDao;
import org.example.digitalculturalportal.pojo.Product;
import org.example.digitalculturalportal.service.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理Service实现类
 * @Author: mostimaovo
 * @DateTime: 2024/7/16
 **/
@Service
public class ProductManagementServiceimpl implements ProductManagementService {
    @Autowired
    private ProductManagementDao productManagementDao;


    @Override
    public void addProduct(Product product) {
        productManagementDao.insertProduct(product);
    }

    @Override
    public void deleteProudct(Long id) {
        productManagementDao.deleteProductById(id);
    }

    @Override
    public void reviseProudct(Product product) {
        productManagementDao.updateProduct(product);
    }


}
