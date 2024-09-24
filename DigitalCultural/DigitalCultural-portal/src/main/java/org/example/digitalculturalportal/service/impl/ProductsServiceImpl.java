package org.example.digitalculturalportal.service.impl;

import org.example.digitalculturalportal.dao.ProductsDao;
import org.example.digitalculturalportal.pojo.Product;
import org.example.digitalculturalportal.pojo.Products;
import org.example.digitalculturalportal.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsDao productsDao;

    @Override
    public List<Products> selectAll() {
        return productsDao.selectAll();
    }

    @Override
    public Products selectProductById(Integer productId) {
        return productsDao.selectProductById(productId);
    }


}
