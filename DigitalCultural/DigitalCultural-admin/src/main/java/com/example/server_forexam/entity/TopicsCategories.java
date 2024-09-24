package com.example.server_forexam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主题表
 * </p>
 *
 * @author adlx
 * @since 2024-09-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicsCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题ID，主键
     */
    private Long id;

    /**
     * 主题名称
     */
    private String name;

    /**
     * 主题描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 逻辑删除标志：0-未删除，1-已删除
     */
    private Boolean isDeleted;
}
