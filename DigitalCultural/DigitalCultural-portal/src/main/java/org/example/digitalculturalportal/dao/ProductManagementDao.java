package org.example.digitalculturalportal.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.digitalculturalportal.pojo.Product;

import java.util.List;

/**
 * 买卖家商品管理Mapper接口
 * @author mostimaovo
 * @since 2024/7/27
 */
@Mapper
public interface ProductManagementDao {
    /**
     * 添加商品
     * @param product
     */
    public void insertProduct(Product product);
    /**
     * 删除商品
     * @param id
     */
    public void deleteProductById(Long id);
    /**
     * 修改商品信息
     * @param product
     */
    public void updateProduct(Product product);


}
