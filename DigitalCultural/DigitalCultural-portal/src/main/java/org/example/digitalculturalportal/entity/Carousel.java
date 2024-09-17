package org.example.digitalculturalportal.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Carousel)实体类
 *
 * @author adlx
 * @since 2024-09-05 15:15:11
 */
public class Carousel implements Serializable {
    private static final long serialVersionUID = -29551404852651629L;
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
    private Integer isActive;
/**
     * 创建时间
     */
    private Date createdAt;
/**
     * 更新时间
     */
    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

