package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *商品对应数据库表Product
 * @author mostimaovo
 * @since 2024/7/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     * 商品唯一标识，自增
     */
    private Long id;
    /**
     *商品名称
     */
    private String productName;
    /**
     *商品价格
     */
    private int productPrice;
    /**
     *商品数量
     */
    private int productQuantity;
}
