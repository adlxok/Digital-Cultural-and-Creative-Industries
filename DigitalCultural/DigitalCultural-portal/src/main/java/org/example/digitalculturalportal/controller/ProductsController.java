package org.example.digitalculturalportal.controller;


import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.pojo.Products;
import org.example.digitalculturalportal.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author adlx
 * @since 2024-09-05
 */
@RestController
@RequestMapping("/mall/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/getallproducts")
    public CommonResult getAllProducts() {
        List<Products> list = productsService.selectAll();
        return CommonResult.success(list);
    }

    @GetMapping("/selectProductById/{id}")
    public CommonResult selectProductById(@PathVariable("id") int id) {
        Products products = productsService.selectProductById(id);
        return CommonResult.success(products);
    }

}

