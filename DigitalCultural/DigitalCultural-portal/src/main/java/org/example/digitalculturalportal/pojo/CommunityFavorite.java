package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityFavorite {
    private Integer id;
    private Integer userId;
    private Integer entityType;
    private Integer  entityId;
    private Integer status;
    private Date createTime;
}
