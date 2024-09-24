package com.example.server_forexam.service.impl;

import com.example.server_forexam.dao.ProductsMapper;
import com.example.server_forexam.entity.Products;
import com.example.server_forexam.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author adlx
 * @since 2024-09-22
 */
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public List<Products> selectAll() {
        return productsMapper.selectAll();
    }

    @Override
    public void updateProducts(Products products) {
        productsMapper.updateProducts(products);
    }

    @Override
    public void deleteOne(int proId) {
        productsMapper.deleteOne(proId);
    }

    @Override
    public void updateStatus(int proId) {
        productsMapper.updateStatus(proId);
    }

    @Override
    public void insertOne(Products products) {
        productsMapper.insertOne(products);
    }
}
