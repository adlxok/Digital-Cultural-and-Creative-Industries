package org.example.digitalculturalportal.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发布帖子参数
 */
@Data
@EqualsAndHashCode
public class PostParam {
    private String title;
    private String Content;
    private String ContentHtml;

}
