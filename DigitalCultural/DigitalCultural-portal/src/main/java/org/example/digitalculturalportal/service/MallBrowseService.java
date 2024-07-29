package org.example.digitalculturalportal.service;

import org.example.digitalculturalportal.pojo.Product;

import java.util.List;
/**
 * 用户商城浏览Service
 * @author mostimaovo
 * @since 2024/7/27
 * */
public interface MallBrowseService {
    public Product findProudct(Long id);
    public List<Product> findProudctList();

}
