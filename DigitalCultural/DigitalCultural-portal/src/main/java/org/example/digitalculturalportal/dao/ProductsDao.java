package org.example.digitalculturalportal.dao;

import org.apache.ibatis.annotations.Param;
import org.example.digitalculturalportal.pojo.Products;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
public interface ProductsDao {

    List<Products> selectAll();

    Products selectProductById(@Param("productId") Integer productId);
}
