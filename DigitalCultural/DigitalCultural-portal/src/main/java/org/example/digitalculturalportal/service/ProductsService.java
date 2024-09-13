package org.example.digitalculturalportal.service;


import org.example.digitalculturalportal.pojo.Products;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
public interface ProductsService {
    List<Products> selectAll();

    Products selectProductById(Integer productId);
}
