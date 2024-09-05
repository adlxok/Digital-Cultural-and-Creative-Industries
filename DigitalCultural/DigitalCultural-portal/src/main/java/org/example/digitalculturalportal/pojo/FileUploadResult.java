package org.example.digitalculturalportal.pojo;

import lombok.Data;

@Data
public class FileUploadResult {
    private int errno;
    private String message;
    private ImgUploadResult data;

    public FileUploadResult() {
        this.errno = 1;
        this.message = "上传失败";
    }

    public FileUploadResult(String url) {
        this.errno = 0;
        this.message = "上传成功";
        this.data = new ImgUploadResult(url);
    }

    public static FileUploadResult success(String url) {
        return new FileUploadResult(url);
    }

    public static FileUploadResult fail() {
        return new FileUploadResult();
    }


}



