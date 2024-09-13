package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID，主键
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 分类ID，关联category表
     */
    private Long categoryId;

    /**
     * 主题ID，关联topic表
     */
    private Long topicId;

    /**
     * 品牌ID，关联brand表
     */
    private Long brandId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 折扣价格
     */
    private BigDecimal discountPrice;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品状态：1-上架，0-下架
     */
    private Integer status;

    /**
     * 商品SKU编号
     */
    private String sku;

    /**
     * 商品重量，单位：kg
     */
    private BigDecimal weight;

    /**
     * 商品主图URL
     */
    private String imageUrl;

    /**
     * 销售量
     */
    private Integer salesVolume;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 逻辑删除标志：0-未删除，1-已删除
     */
    private Boolean isDeleted;


}
