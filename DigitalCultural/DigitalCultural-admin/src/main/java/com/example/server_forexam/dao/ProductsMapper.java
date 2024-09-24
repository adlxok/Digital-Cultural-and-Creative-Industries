package com.example.server_forexam.dao;


import com.example.server_forexam.entity.Products;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-09-22
 */
public interface ProductsMapper {

    List<Products> selectAll();

    void updateProducts(Products products);

    void deleteOne(@Param("porId") int proId);

    void updateStatus(@Param("proId") int proId);

    void insertOne(Products products);

}
