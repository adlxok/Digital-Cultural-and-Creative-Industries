package org.example.digitalculturalportal.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主题表
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topics implements Serializable {

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
