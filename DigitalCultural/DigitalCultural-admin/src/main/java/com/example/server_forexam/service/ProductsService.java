package com.example.server_forexam.service;

import com.example.server_forexam.entity.Products;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author adlx
 * @since 2024-09-22
 */
public interface ProductsService  {

    List<Products> selectAll();

    void updateProducts(Products products);

    void deleteOne(int proId);

    void updateStatus(int proId);

    void insertOne(Products products);

}
