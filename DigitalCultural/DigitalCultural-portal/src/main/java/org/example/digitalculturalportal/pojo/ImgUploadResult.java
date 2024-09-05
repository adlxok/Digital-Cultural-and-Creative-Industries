package org.example.digitalculturalportal.pojo;

import lombok.Data;

@Data
public class ImgUploadResult {
    private String url;
    private String alt;
    private String href;

    public ImgUploadResult(String url) {
        this.alt = "";
        this.href = "";
        this.url = url;
    }

}
