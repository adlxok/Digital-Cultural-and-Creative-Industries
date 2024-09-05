package org.example.digitalculturalportal.pojo;

import lombok.Data;

import java.util.List;
/**
 * 存储图片参数
 */
@Data
public class ImageParam {
        private List<String> imageUrlList;
        private Integer postId;
}
