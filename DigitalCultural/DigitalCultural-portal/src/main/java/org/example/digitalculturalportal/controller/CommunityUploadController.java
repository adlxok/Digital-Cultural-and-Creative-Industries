package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.CommunityImage;
import org.example.digitalculturalportal.pojo.FileUploadResult;
import org.example.digitalculturalportal.pojo.ImageParam;
import org.example.digitalculturalportal.service.CommunityImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 社区首页
 * @author jisamin
 * @since 2024/7/18
 */
@Controller
@Slf4j
@Api(tags = "CommunityUploadController")
@Tag(name="CommunityUploadController",description = "社区上传文件")
@RequestMapping("/communityUpload")
public class CommunityUploadController {
    @Autowired
    private CommunityImageService communityImageService;
    @Value("${file.save-path}")
    private String filePath;

    @ApiOperation("上传图片")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public FileUploadResult uploadImage(MultipartFile file) throws IOException {

        String filePath = this.filePath;
        String suffix = "";
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = System.currentTimeMillis() + suffix;
            File targetFile = new File(filePath, fileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            try {
                file.transferTo(targetFile);
            } finally {

            }
            String url = "http://localhost:8080/static/" + fileName;
            return new FileUploadResult(url);

        } catch (Exception e) {
            return new FileUploadResult();
        }
    }

    @ApiOperation("储存图片")
    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult saveImage(@RequestBody ImageParam imageParam) {
        if (imageParam.getImageUrlList().isEmpty()) {
            return CommonResult.success("没有要存储的url");
        }
        for (String url : imageParam.getImageUrlList()) {
            CommunityImage communityImage = new CommunityImage();
            communityImage.setUploadTime(new Date());
            communityImage.setUrl(url);
            communityImage.setPostId(imageParam.getPostId());
            communityImageService.addImageUrl(communityImage);
        }
        return CommonResult.success("存储成功");
    }
}
