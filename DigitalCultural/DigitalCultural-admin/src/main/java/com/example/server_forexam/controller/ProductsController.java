package com.example.server_forexam.controller;


import com.example.server_forexam.common.CommonResult;
import com.example.server_forexam.entity.Products;
import com.example.server_forexam.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author adlx
 * @since 2024-09-22
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/selectAll")
    public CommonResult selectAll() {
        List<Products> list = productsService.selectAll();
        return CommonResult.success(list);
    }

    @PostMapping("/updateProducts")
    public CommonResult updateProducts(@RequestBody Products products) {
        productsService.updateProducts(products);
        return CommonResult.success();
    }

    @PostMapping("/deleteOne")
    public CommonResult deleteOne(@RequestBody Map<String, Object> reqData) {
        int proId = Integer.parseInt(reqData.get("proId").toString());
        productsService.deleteOne(proId);
        return CommonResult.success();
    }

    @PostMapping("/updateStatus")
    public CommonResult updateStatus(@RequestBody Map<String, Object> reqData) {
        int proId = Integer.parseInt(reqData.get("proId").toString());
        productsService.updateStatus(proId);
        return CommonResult.success();
    }

    @PostMapping("/insertOne")
    public CommonResult insertOne(@RequestBody Products products) {
        productsService.insertOne(products);
        return CommonResult.success();
    }

}

