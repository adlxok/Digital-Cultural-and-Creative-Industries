package org.example.digitalculturalportal.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author adlx
 * @since 2024-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Carousel implements Serializable {

    /**
     * 主键，自动递增
     */
    private Integer id;

    /**
     * 图片的 URL 地址
     */
    private String imgUrl;

    /**
     * 图片的替代文本或描述
     */
    private String altText;

    /**
     * 图片的链接地址
     */
    private String linkUrl;

    /**
     * 显示顺序
     */
    private Integer position;

    /**
     * 标记图片是否显示
     */
    private Boolean isActive;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
