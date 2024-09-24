package com.example.server_forexam.controller;


import com.example.server_forexam.common.CommonResult;
import com.example.server_forexam.entity.Orders;
import com.example.server_forexam.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户订单表 前端控制器
 * </p>
 *
 * @author adlx
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/selectAllOrders")
    public CommonResult selectAllOrders() {
        List<Orders> list = ordersService.selectAllOrders();
        return CommonResult.success(list);
    }

    @PostMapping("/deleteOrderById")
    public CommonResult deleteOrderById(@RequestBody Map<String, Object> reqData) {
        int orderId = Integer.parseInt(reqData.get("orderId").toString());
        ordersService.deleteOrderById(orderId);
        return CommonResult.success();
    }

    @PostMapping("/OrderDelivery")
    public CommonResult OrderDelivery(@RequestBody Orders orders) {
        ordersService.OrderDelivery(orders);
        return CommonResult.success();
    }

}

