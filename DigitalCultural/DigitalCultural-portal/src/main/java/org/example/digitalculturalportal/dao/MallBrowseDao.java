package org.example.digitalculturalportal.dao;

import org.example.digitalculturalportal.pojo.Product;

import java.util.List;

/**
 * 用户商品浏览Mapper接口
 * @author mostimaovo
 * @since 2024/7/27
 */
public interface MallBrowseDao {
    /**
     * 查看单个商品信息
     * @param id
     */
    public Product selectProductById(Long id);
    /**
     * 查看批量商品信息
     */
    public List<Product> selectProductList();
}
