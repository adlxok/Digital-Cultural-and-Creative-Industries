package org.example.digitalculturalportal.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收藏参数
 */
@Data
@EqualsAndHashCode
public class FavoriteParam {
    private Integer entityType;
    private Integer  entityId;
}
