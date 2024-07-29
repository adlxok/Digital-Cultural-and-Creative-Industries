package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.Product;

import java.util.List;

/**
 * 商品管理Service
 * @author mostimaovo
 * @since 2024/7/16
 * */
public interface ProductManagementService {
    public void addProduct(Product product);
    public void deleteProudct(Long id);
    public void reviseProudct(Product product);


}
