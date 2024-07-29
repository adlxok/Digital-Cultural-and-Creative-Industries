package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.Product;
import org.example.digitalculturalportal.pojo.UserLoginParam;
import org.example.digitalculturalportal.service.ProductManagementService;
import org.example.digitalculturalportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: mostimaovo
 * @DateTime: 2024/7/22
 **/
@Controller
@Api(tags = "ProductManangementController")
@Tag(name = "ProductManangementController", description = "商品管理")
@RequestMapping("/product")
public class ProductManangementController {
    @Autowired
    private ProductManagementService productManagementService;

    @ApiOperation("卖家上传商品信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestParam("productName") String productName,@RequestParam("productPrice") int productPrice,@RequestParam("productQuantity") int productQuantity ) {
        Product product=new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductQuantity(productQuantity);
        productManagementService.addProduct(product);
        return CommonResult.success();
    }

    @ApiOperation("卖家删除商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("id") Long id){
        productManagementService.deleteProudct(id);
        return CommonResult.success();
    }

    @ApiOperation("卖家修改商品信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestParam("id") Long id,@RequestParam("productName") String productName,@RequestParam("productPrice") int productPrice,@RequestParam("productQuantity") int productQuantity ) {
        Product product=new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductQuantity(productQuantity);
        productManagementService.reviseProudct(product);
        return CommonResult.success();
    }


}
