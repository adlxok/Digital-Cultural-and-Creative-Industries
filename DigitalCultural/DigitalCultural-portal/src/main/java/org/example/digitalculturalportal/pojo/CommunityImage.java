package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityImage {
    private Integer id;
    private Integer postId;
    private String  url;
    private Date uploadTime;
}
